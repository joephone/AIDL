package com.transcendence.aidlserver

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivityKt : AppCompatActivity(), OnGetClientDataCallback{
    private var iv_pic: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_pic = findViewById(R.id.iv_pic);
        ServerApp.application.setOnGetClientDataCallback(this)
//        btn_send_to_client.setOnClickListener {
//            try {
//                val inputStream = assets.open("server1.jpg")
//                val byteArray=inputStream.readBytes()
//                val memoryFile= MemoryFile("server_image", byteArray.size)
//                memoryFile.writeBytes(byteArray, 0, 0, byteArray.size)
//                val fd= MemoryFileUtils.getFileDescriptor(memoryFile)
//                val pfd= ParcelFileDescriptor.dup(fd)
//                val message= Message().apply {
//                    what=2
//                    obj=pfd
//                }
//                MyApplication.application.handler.sendMessage(message)
//            }catch (e: RemoteException){
//                e.printStackTrace()
//            }
//        }
    }
    override fun onGetClientData(bytes: ByteArray?) {
        if (bytes != null && bytes.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            if (bitmap != null) {
                iv_pic?.setImageBitmap(bitmap)
            }
        }
    }
}