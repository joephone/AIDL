package com.transcendence.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.transcendence.aidl.service.MyService;

/**
 * @Author Joephone C
 * @CreateTime 2025年2月16日
 * @Desc
 */
public class MyServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private MyService.MyServiceBinder myBinder;


    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //在Activity与Service解除关联的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //实例化Service的内部类myBinder
            //通过向下转型得到了MyBinder的实例
            myBinder = (MyService.MyServiceBinder) service;
            //在Activity调用Service类的方法
            myBinder.service_connect_activity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);


        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        bindService = (Button) findViewById(R.id.bindService);
        unbindService = (Button) findViewById(R.id.unbindService);

        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startService) {
            //点击启动Service
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent);
        } else if (v.getId() == R.id.stopService) {
            //点击停止Service
            Intent stopIntent = new Intent(this, MyService.class);
            stopService(stopIntent);
        } else if (v.getId() == R.id.bindService) {
            //点击绑定Service
            Intent bindIntent = new Intent(this, MyService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
            //这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
            //这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行
        } else if (v.getId() == R.id.unbindService) {
            //点击解绑Service
            unbindService(connection);
        }

    }


}
