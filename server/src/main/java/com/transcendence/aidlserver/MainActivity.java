package com.transcendence.aidlserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;


public class MainActivity extends AppCompatActivity implements OnGetClientDataCallback {
    private static final String TAG = "wan";
    private ImageView iv;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerApp.application.setOnGetClientDataCallback(this);
        iv = findViewById(R.id.iv_pic);
    }

    @Override
    public void onGetClientData(@Nullable byte[] bytes) {
        Log.d(TAG,"收到client传来的图片数据");
        if (bytes == null || bytes.length == 0) {
            Log.e(TAG, "收到的数据为空或无效");
            return;
        }
        Log.d(TAG, "bytes size:"+bytes.length);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                if (bitmap == null) {
                    Log.e(TAG, "无法解码图像数据");
                } else {
                    Log.d(TAG, "图像解码成功");
                }
                iv.setImageBitmap(bitmap);
//                Glide.with(iv.getContext())
//                        .asBitmap()
//                        .load(bytes)
//                        .into(iv);
//            }
//        });
    }
}