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
import android.widget.Toast;

import com.transcendence.aidlserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "wan";
    private IMyAidlInterface mAidl = null;
    private Button btnConnect,btnGetInfo;
    private Boolean isConnect = false;
    private ServiceConnection mConn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.tv_connect);
        btnGetInfo = findViewById(R.id.tv_get_info);
        btnConnect.setOnClickListener(v -> {
            if(isConnect){
                disconnectService();
            } else {
                connectionServer();
            }
        });


        btnGetInfo.setOnClickListener(v -> {
            if(isConnect){
                getInfoFromServer();
            } else {

            }
        });

    }

    private void getInfoFromServer() {
        try {
            String info = mAidl.getStringFromServer();
            btnGetInfo.setText(info);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectionServer() {
        Log.d(TAG,"connectionServer");
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"onServiceConnected");
                Toast.makeText(getApplicationContext(),"onServiceConnected",Toast.LENGTH_SHORT).show();
                mAidl = IMyAidlInterface.Stub.asInterface(service);
                isConnect = true;
                btnConnect.setText(R.string.connecting);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"onServiceDisconnected");
                Toast.makeText(getApplicationContext(),"onServiceDisconnected",Toast.LENGTH_SHORT).show();
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
            btnConnect.setText(R.string.connect);
        }
    }

}