package com.mythsun.study.we;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythsun.study.R;

public class Lred extends Fragment {
    String Tas = "第二次";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.left_f,container,false);
        Log.i(Tas, "onCreateView");
        return v;
    }

    @Override
    public void onStart() {
        Log.i(Tas, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(Tas, "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.i(Tas, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(Tas, "onPause");
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(Tas, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(Tas, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(Tas, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.i(Tas, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(Tas, "onDetach");
        super.onDetach();
    }
}
