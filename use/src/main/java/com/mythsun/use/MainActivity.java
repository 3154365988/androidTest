package com.mythsun.use;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mythsun.service.ServiceAdapter;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "UseMainActivity";
    private Button btn1;
    private Button btn2;

    private ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btn1 onclick");
                adapter = new ServiceAdapter();
                adapter.bindAidlService(getApplication());
            }
        });
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btn2 onclick");
                adapter.work();
            }
        });
    }
}
