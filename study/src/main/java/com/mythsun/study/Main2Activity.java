package com.mythsun.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mythsun.study.we.Adasd;
import com.mythsun.study.we.AsadAdapter;
import com.mythsun.study.we.TestBut;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    String Tas = "第一次";
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i(Tas, "onCreate");
        Intent intent = getIntent();
        String s = intent.getStringExtra("send");
        TextView yt = findViewById(R.id.textView);
        yt.setText(s);
        Button b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("result", "这是返回的");
                setResult(2, intent1);
                finish();
            }
        });
//        LinearLayout layout = new LinearLayout(Main2Activity.this);
//        LinearLayout.LayoutParams lLayoutlayoutParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout.setLayoutParams(lLayoutlayoutParams);
//        TestBut dsa = new TestBut(getApplication());
//        layout.addView(dsa);
//        setContentView(layout);

        listView = findViewById(R.id.listaaa);
        Log.i(Tas, "1");
        String[] adds = new String[]{"23", "53453", "4354353", "2", "34", "656"};
        Log.i(Tas, "2");
        List<Adasd> lis = new ArrayList<>();
        lis.add(new Adasd("34324",R.drawable.ic_launcher_background));
        lis.add(new Adasd("2",R.drawable.ic_launcher_background));
        lis.add(new Adasd("4",R.drawable.ic_launcher_background));
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, adds);\
        AsadAdapter  adt = new AsadAdapter(Main2Activity.this,R.layout.item,lis);
        Log.i(Tas, "3");
        listView.setAdapter(adt);
        Log.i(Tas, "4");
    }

    public static void startThis(Context context, String s, String a) {
        Intent intent = new Intent(context, Main2Activity.class);
        intent.putExtra("send", s);
        intent.putExtra("A", a);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tas, "onStart");
    }

    @Override
    protected void onResume() {
        Log.i(Tas, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(Tas, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tas, "onStop");
    }

    @Override
    protected void onDestroy() {
        Log.i(Tas, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i(Tas, "onRestart");
        super.onRestart();
    }
}
