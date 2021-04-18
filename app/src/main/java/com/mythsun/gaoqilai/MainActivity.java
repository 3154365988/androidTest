package com.mythsun.gaoqilai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mythsun.gaoqilai.widget.RecordButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecordButton btnRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnRecord = findViewById(R.id.btn_record);
        btnRecord.setOnVoiceButtonCallBack(new RecordButton.OnVoiceButtonCallBack() {
            @Override
            public void onStartRecord() {

            }

            @Override
            public void onStopRecord() {

            }

            @Override
            public void onWillCancelRecord() {

            }

            @Override
            public void onContinueRecord() {

            }
        });
    }
}
