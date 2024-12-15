package com.transcendence.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileInputStream

class MyServiceKt :Service() {

    companion object {
        private const val TAG = "wan"
    }
    override fun onBind(intent: Intent?): IBinder? {
        return MyBinder()
    }

    class MyBinder : IMyAidlInterface.Stub() {
        override fun getStringFromServer(): String {
            Log.d(TAG, "service 端的getStringFromServer方法被client调用了")
            return "this info from Server"
        }

        override fun sendImage(data: ByteArray?) {

        }

        override fun client2server(pfd: ParcelFileDescriptor) {
            Log.d(TAG, "thread:" + Thread.currentThread().name + " sendData")
            /**
             * 从ParcelFileDescriptor中获取FileDescriptor
             */
            val fileDescriptor = pfd.fileDescriptor

            /**
             * 根据FileDescriptor构建InputStream对象
             */
            val fis = FileInputStream(fileDescriptor)

            /**
             * 从InputStream中读取字节数组
             */
            val data = fis.readBytes()
            Log.d(TAG, "bytes size:${data.size}")
            val message = Message().apply {
                what = 1
                obj = data
            }
            ServerApp.application.handler.sendMessage(message)
        }

    }
}