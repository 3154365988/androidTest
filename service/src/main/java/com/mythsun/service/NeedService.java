package com.mythsun.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class NeedService extends Service {
    private static String TAG = "NeedService";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return aidl;
    }

    IServiceAdapter.Stub aidl = new IServiceAdapter.Stub() {
        @Override
        public void work() throws RemoteException {
            Log.d(TAG, "work");
        }
    };
}
