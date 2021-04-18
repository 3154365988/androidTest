package com.mythsun.study;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mythsun.study.we.Lred;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String Tas = "第一次";
    Button btn5;
    Button btn6;
    Context context;
    LocalBroadcastManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        context = getApplication();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, intentFilter);

        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.mythsun.study.aaa");
        localReceiver = new LocalReceiver();
        // 本地广播需要使用LocalBroadcastManager。顾名思义本地广播管理者
        manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(localReceiver, intentFilter1);
    }

    private IntentFilter intentFilter;
    private BroadcastReceiver networkReceiver;

    private IntentFilter intentFilter1;
    private BroadcastReceiver localReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
        manager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Tas, "LocalReceiver exe");
            Toast.makeText(context, "LocalReceiver", Toast.LENGTH_SHORT).show();
        }
    }

    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Tas, "NetworkReceiver exe");
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Log.i(Tas, "NetworkReceiver isAvailable");
            } else {
                Log.i(Tas, "NetworkReceiver is not Available");
            }
        }
    }

    private void init() {
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
//                intent1.putExtra("send", "这是传递来的信息");
////                startActivity(intent1);
//                startActivityForResult(intent1, 1);
                Main2Activity.startThis(getApplication(), "222222", "d");

            }
        });
        tv = findViewById(R.id.textView2);
        Button bt4 = findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lred fra = new Lred();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.sadd, fra);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btn5 = findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ComponentName componentName = new ComponentName("com.mythsun.study", "com.mythsun.study.MyReceiver");
//                Intent intent = new Intent("com.mythsun.study.dddd");
//                intent.setComponent(componentName);
//                sendBroadcast(intent);
                sendHXBroadCast();
//                Intent intent = new Intent("com.mythsun.study.dddd");
//                sendBroadcast(intent);
            }
        });

        btn6 = findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Tas, "btn6 onClick");
                Intent intent = new Intent("com.mythsun.study.aaa");
                manager.sendBroadcast(intent);
            }
        });

    }

    private TextView tv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            tv.setText(data.getStringExtra("result"));
        }

    }

    /**
     * 静态广播让所有注册接受方都接收到，不用这个只能挨个绑定应用的包名，如下
     * ComponentName componentName = new ComponentName("com.mythsun.study", "com.mythsun.study.MyReceiver");
     * Intent intent = new Intent("com.mythsun.study.dddd");
     * intent.setComponent(componentName);
     * sendBroadcast(intent);
     * 而下面用了PackageManager的queryBroadcastReceivers，可以检索出指定intent的receiver
     */
    private void sendHXBroadCast() {
        Intent logIntent = new Intent();
        logIntent.setAction("com.mythsun.study.dddd");
        PackageManager pm = this.getPackageManager();

        List<ResolveInfo> matches = pm.queryBroadcastReceivers(logIntent, 0);
        if (matches != null && matches.size() >= 1) {
            for (ResolveInfo resolveInfo : matches) {
                Intent intent = new Intent(logIntent);
                ComponentName cn = new ComponentName(
                        resolveInfo.activityInfo.applicationInfo.packageName,
                        resolveInfo.activityInfo.name);
                intent.setComponent(cn);
                //this.sendBroadcast(intent);
                // 发送有序【使用queryBroadcastReceivers，就无法发送有序的了，它会定向发送给每一个应用，自然截断无效】
                this.sendOrderedBroadcast(intent, null);
            }
        }
    }
}
