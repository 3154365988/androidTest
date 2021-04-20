package com.mythsun.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * This class is used for SDK self-testing
 */
public class ServiceAdapter {
    private static String TAG = "ServiceAdapter";
    private static String NEED_SERVICE = "com.mythsun.service.action1";
    private static String NEED_SERVICE_PACKAGE = "com.mythsun.service";
    private static IServiceAdapter aidlAdapter;

    private static NeedServiceConnection mConnection;

    public static void bindAidlService(Context context) {
        // Here, you can do a whitelist check on the caller.
        Log.d(TAG, "package name " + context.getPackageName());

        Intent intent = new Intent();
        intent.setAction(NEED_SERVICE);
        intent.setPackage(NEED_SERVICE_PACKAGE);
        mConnection = new NeedServiceConnection();

        // BIND may fail because of an error in the package name.
        try {
            context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } catch (RuntimeException e) {
            Log.e(TAG, "bindService " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void work(){
        Log.d(TAG, "work");
        try {
            aidlAdapter.work();
        } catch (RemoteException e) {
            Log.e(TAG, "work " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class NeedServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            aidlAdapter = IServiceAdapter.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    }
}
