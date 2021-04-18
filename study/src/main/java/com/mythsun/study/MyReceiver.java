package com.mythsun.study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "自定义广播", Toast.LENGTH_SHORT).show();
        Log.i("第三次", "自定义广播 exe");
    }
}
