package com.transcendence.aidl.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.transcendence.aidl.bean.Person;
import com.transcendence.aidl.log.LogUtils;
import com.transcendence.aidl.proxy.CustomProxy;

import java.util.List;

/**
 * @Author Joephone C
 * @CreateTime 2024年12月15日 21:56:15
 * @Desc  AMS 实现的service
 */
public abstract class BinderStub extends Binder implements IPersonManager {
    //binder 唯一标识  限定名确定唯一性
    public static final String DESCRIPTOR = "com.transcendence.aidl.server";

    public static final int GET_PERSON = IBinder.FIRST_CALL_TRANSACTION;

    public static final int ADD_PERSON = IBinder.FIRST_CALL_TRANSACTION + 1;

    public BinderStub() {
        this.attachInterface(this,DESCRIPTOR);
    }


    /**
     *  通过queryLocalInterface 方法，查找本地Binder对象
     *  如果返回的就是PersonManager,说明 client 和 server 处于同一个进程，直接返回
     *  如果不是，则返回一个代理对象
     *
     * @param iBinder  Binder驱动传来的一个代理对象
     * @return
     */
    public static IPersonManager asInterface(IBinder iBinder){
        if(iBinder == null){
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if(iInterface instanceof IPersonManager){
            LogUtils.d("client 和 server 处于同一个进程，直接返回");
            return (IPersonManager) iInterface;
        }
        LogUtils.d("client 和 server处于不同进程，返回代理对象");
        return new CustomProxy(iBinder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }


    /**
     *  数据交互
     *
     *
     * @param code The action to perform. This should be a number between
     * {@link #FIRST_CALL_TRANSACTION} and {@link #LAST_CALL_TRANSACTION}.
     * @param data Marshalled data being received from the caller.
     * @param reply If the caller is expecting a result back, it should be marshalled
     * in to here.
     * @param flags Additional operation flags. Either 0 for a normal
     * RPC, or {@link #FLAG_ONEWAY} for a one-way RPC.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        // 收到binder 驱动通知后，server进程通过回调binder 对象 onTransact() 进行数据解包 & 调用目标方法
        // code 即在transact() 中约定的目标方法的标识符
        switch (code) {
            case INTERFACE_TRANSACTION:
                return true;
            case GET_PERSON:
                data.enforceInterface(DESCRIPTOR);
                List<Person> personList = this.getPersonList();
                if(reply != null){
                    reply.writeNoException();
                    reply.writeTypedList(personList);
                }
                return true;
            case ADD_PERSON:
                //解包 Parcel中的数据
                data.enforceInterface(DESCRIPTOR);
                Person person = null;
                if(data.readInt() != 0) {
//                    person =
                }
                this.addPerson(person);
                //将结果写入到reply
                if(reply!=null) {
                    reply.writeNoException();
                }
                return true;
        }
        //返回计算结果到binder
        return super.onTransact(code, data, reply, flags);
    }
}
