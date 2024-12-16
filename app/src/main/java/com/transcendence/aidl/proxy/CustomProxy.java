package com.transcendence.aidl.proxy;

import android.os.IBinder;
import android.os.Parcel;

import com.transcendence.aidl.bean.Person;
import com.transcendence.aidl.server.BinderStub;
import com.transcendence.aidl.server.IPersonManager;

import java.util.List;

/**
 * @Author Joephone C
 * @CreateTime 2024年12月15日 21:56:09
 * @Desc   远端服务器代理
 */
public class CustomProxy implements IPersonManager {

    private IBinder mIBinder;

    public CustomProxy(IBinder iBinder){
        this.mIBinder = iBinder;
    }

    @Override
    public void addPerson(Person person) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(BinderStub.DESCRIPTOR);
            if(person!=null) {
                data.writeInt(1);
                person.writeToParcel(data,0);
            } else {
                data.writeInt(0);
            }
            /**
             *  通过 transact() 将上述数据发送到binder 驱动 （远端）
             *   参数1 目标方法标识符，client 与server 进程自身约定
             *   参数2 上述的Parcel 对象
             *   参数3 结果回复
             */
            mIBinder.transact(BinderStub.ADD_PERSON,data,reply,0);
            //在发送数据后，client的进程该线程会暂时被挂起
            reply.readException();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public List<Person> getPersonList() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        List<Person> result = null;

        try {
            data.writeInterfaceToken(BinderStub.DESCRIPTOR);
            /**
             *  通过 transact() 将上述数据发送到binder 驱动 （远端）
             *   参数1 目标方法标识符，client 与server 进程自身约定
             *   参数2 上述的Parcel 对象
             *   参数3 结果回复
             */
            mIBinder.transact(BinderStub.GET_PERSON,data,reply,0);
            //在发送数据后，client的进程该线程会暂时被挂起
            reply.readException();
//            result = reply.createTypedArrayList(Person.)
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
