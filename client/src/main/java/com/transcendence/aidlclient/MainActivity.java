package com.transcendence.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.transcendence.aidlserver.IMyAidlInterface;

import java.io.FileDescriptor;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "wan";
    private IMyAidlInterface mAidl = null;
    private Button btnConnect,btnGetInfo,btnSendImage,btnSendBigImage;
    private Boolean isConnect = false;
    private ServiceConnection mConn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.tv_connect);
        btnGetInfo = findViewById(R.id.tv_get_info);
        btnSendImage = findViewById(R.id.tv_send_image);
        btnSendBigImage = findViewById(R.id.tv_big_image);
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
                Toast.makeText(getApplicationContext(),R.string.press_connect_first,Toast.LENGTH_SHORT).show();
            }
        });

        btnSendImage.setOnClickListener(v -> {
            if(isConnect){
                sendImage();
            } else {
                Toast.makeText(getApplicationContext(),R.string.press_connect_first,Toast.LENGTH_SHORT).show();
            }
        });

        btnSendBigImage.setOnClickListener(v -> {
            if(isConnect){
                sendBigImage();
            } else {
                Toast.makeText(getApplicationContext(),R.string.press_connect_first,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendBigImage() {
        Log.d(TAG,"sendBigImage");
        Toast.makeText(getApplicationContext(),R.string.send_image,Toast.LENGTH_SHORT).show();
        AssetManager assetManager = getAssets();
        try {
            InputStream is =assetManager.open("large.jpg");
            byte[] buffer = new byte[is.available()];
            MemoryFile memoryFile = new MemoryFile("client_image",buffer.length);
            memoryFile.writeBytes(buffer,0,0,buffer.length);
            FileDescriptor fd = MemoryFileUtils.INSTANCE.getFileDescriptor(memoryFile);
            ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(fd);
            mAidl.client2server(pfd);
            is.close();
        } catch (Exception e){

        }
    }

    private void sendImage() {
        Log.d(TAG,"sendImage");
        Toast.makeText(getApplicationContext(),R.string.send_image,Toast.LENGTH_SHORT).show();
        AssetManager assetManager = getAssets();
        try {
            InputStream is =assetManager.open("small.jpg");
            byte[] buffer = new byte[is.available()];
//            is.read(buffer);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);
            mAidl.sendImage(buffer);
            is.close();
        } catch (Exception e){

        }

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
            btnGetInfo.setText(R.string.press_to_get_info);
        }
    }

}