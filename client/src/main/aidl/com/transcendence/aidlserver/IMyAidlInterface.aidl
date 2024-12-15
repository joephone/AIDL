// IMyAidlInterface.aidl
package com.transcendence.aidlserver;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getStringFromServer();

    /**
     * default in
     */
    void sendImage(in byte[] data);

    void client2server(in ParcelFileDescriptor pfd);
}