package com.mythsun.study.we;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythsun.study.R;

import java.util.List;

public class AsadAdapter extends ArrayAdapter<Adasd> {
    private int di;

    public AsadAdapter(@NonNull Context context, int resource, @NonNull List<Adasd> objects) {
        super(context, resource, objects);
        di = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Adasd adasd = getItem(position);
        View view;
        Hoddd hoddd;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(di, null);
            hoddd = new Hoddd();
            hoddd.textView = view.findViewById(R.id.textView4);
            hoddd.view = view.findViewById(R.id.imageView);
            view.setTag(hoddd);
        } else {
            view = convertView;
            hoddd = (Hoddd) view.getTag();
        }
        hoddd.view.setImageResource(adasd.getId());
        hoddd.textView.setText(adasd.getName());
        return view;
    }

    class Hoddd {
        ImageView view;
        TextView textView;
    }
}
