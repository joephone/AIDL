//package com.transcendence.aidlserver;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.ParcelFileDescriptor;
//import android.os.RemoteException;
//import android.util.Log;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class MyService extends Service {
//    private static final String TAG = "wan";
//    public MyService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        return new MyBinder();
//    }
//
//    class MyBinder extends IMyAidlInterface.Stub {
//        @Override
//        public String getStringFromServer() throws RemoteException {
//            Log.d(TAG, "service 端的getStringFromServer方法被client调用了");
//            return "this info from Server";
//        }
//
//        @Override
//        public void sendImage(byte[] data) throws RemoteException {
//            Log.d(TAG, "service 端的sendImage方法被client调用了");
//            if(ServerApp.mGetClientDataCallback!=null) {
//                ServerApp.mGetClientDataCallback.onGetClientData(data);
//            }
//        }
//
//        @Override
//        public void client2server(ParcelFileDescriptor pfd) throws RemoteException {
//            Log.d(TAG, "service 端的client2server方法被client调用了");
//            FileDescriptor fileDescriptor = pfd.getFileDescriptor();
//
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(fileDescriptor);
//
//                // 从InputStream中读取字节数组
//                byte[] data = readBytesFromInputStream(fis);
//                Log.d(TAG, "bytes size:" + data.length);
////                if(ServerApp.mGetClientDataCallback!=null) {
////                    ServerApp.mGetClientDataCallback.onGetClientData(data);
////                }
//                // 创建并发送消息
//                Message message = Message.obtain();
//                message.what = 1;
//                message.obj = data;
//                ServerApp.application.getHandler().sendMessage(message);
//            } catch (IOException e) {
//                Log.e(TAG, "Error reading from InputStream", e);
//            } finally {
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        Log.e(TAG, "Error closing InputStream", e);
//                    }
//                }
//            }
//        }
//
//        private byte[] readBytesFromInputStream(FileInputStream fis) throws IOException {
//            byte[] buffer = new byte[1024];
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int bytesRead;
//
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//
//            return baos.toByteArray();
//        }
//    }
//
//
//}