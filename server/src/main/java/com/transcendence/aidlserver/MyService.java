package com.transcendence.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "wan";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    class MyBinder extends IMyAidlInterface.Stub{
        @Override
        public String getStringFromServer() throws RemoteException {
            Log.d(TAG, "service 端的方法被client调用了");
            return "this info from Server";
        }
    }
}