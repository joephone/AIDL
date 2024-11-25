package com.transcendence.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import com.transcendence.aidlserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "wan";
    private IMyAidlInterface mAidl = null;
    private Button btn = null;
    private Boolean isConnect = false;
    private ServiceConnection mConn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.tv);
        btn.setOnClickListener(v -> {
            if(isConnect){
                disconnectService();
            } else {
                connectionServer(btn);
            }
        });

    }

    private void connectionServer(Button tv) {
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"onServiceConnected");
                mAidl = IMyAidlInterface.Stub.asInterface(service);
                try {
                    String info = mAidl.getStringFromServer();
                    tv.setText(info+", press to unConnect");
                    isConnect = true;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"onServiceDisconnected");
            }
        };

        Intent intent = new Intent();
        intent.setAction("com.transcendence.aidlserver.action");
        intent.setPackage("com.transcendence.aidlserver");
        bindService(intent,mConn,BIND_AUTO_CREATE);

    }


    private void disconnectService() {
        if (isConnect) {
            unbindService(mConn);
            isConnect = false;
            btn.setText("This is client,press to get info from Server");
        }
    }

}