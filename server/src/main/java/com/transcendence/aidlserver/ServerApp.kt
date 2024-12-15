package com.transcendence.aidlserver

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.ParcelFileDescriptor

class ServerApp :Application() {



//    /**
//     * 从客户端接收到数据回调
//     */
//    var mGetClientDataCallback: OnGetClientDataCallback? = null

    fun setOnGetClientDataCallback(callback: OnGetClientDataCallback) {
        mGetClientDataCallback = callback
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: ServerApp
        /**
         * 从客户端接收到数据回调
         */
        lateinit var mGetClientDataCallback: OnGetClientDataCallback
    }

    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what){

                1->{
                    val bytes = msg.obj as ByteArray
                    mGetClientDataCallback?.onGetClientData(bytes)
                }
//                2->{
//                    val pfd=msg.obj as ParcelFileDescriptor
//                    sendDataCallback?.onSendClientData(pfd)
//                }
            }
        }
    }
}