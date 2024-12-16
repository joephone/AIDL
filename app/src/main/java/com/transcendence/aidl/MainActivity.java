package com.transcendence.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.transcendence.aidl.log.LogUtils;
import com.transcendence.aidl.server.BinderStub;
import com.transcendence.aidl.server.IPersonManager;
import com.transcendence.aidl.service.ServerService;

public class MainActivity extends AppCompatActivity {

    IPersonManager iPersonManager;
    private boolean isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBinderService();
    }

    private void initBinderService() {
        Intent intent = new Intent(this, ServerService.class);
        bindService(intent,mConn,BIND_AUTO_CREATE);
    }

    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d("onServiceConnected");
            iPersonManager = BinderStub.asInterface(service);
            isConnect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("onServiceDisconnected");
            isConnect = false;
        }
    };
}