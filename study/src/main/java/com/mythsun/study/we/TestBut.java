package com.mythsun.study.we;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mythsun.study.R;

public class TestBut extends LinearLayout {
    /**
     * 之所以用这个实现，是因为我的空间要在xml中用，那么就需要LayoutInflater来加载一个xml，xml是和属性在一起的，自然需要两个
     * 如果自定义控件没有xml或者说不经过xml声明，就可以用第一个
     *
     * @param context
     * @param attrs
     */
    public TestBut(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.dasd, this);
        Button button = findViewById(R.id.button3);
        final TextView textView = findViewById(R.id.textView3);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("22222222");
            }
        });
    }

    public TestBut(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.dasd, this);
        Button button = findViewById(R.id.button3);
        final TextView textView = findViewById(R.id.textView3);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("11111");
            }
        });
    }
}
