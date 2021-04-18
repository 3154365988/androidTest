package com.mythsun.fileos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    String createBook = "create table book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text);";
    String createCategory = "create table category (" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer);";
    Context context;
    private String TAG = "文件os";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 如果存在数据库，那么这个方法不会再被执行
        Log.d(TAG, "onCreate: exe");
        db.execSQL(createBook);
        db.execSQL(createCategory);
        Toast.makeText(context, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: exe");
        // 升级方法的作用，再次调用
        // 让调用时的版本比1大，就会执行该方法
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);
        /**
         * 这里有个最佳实践，用于解决，连续版本中数据库的升级
         * 比如1版本我就一个book表，2版本多了个adb表，3版本多了个ddd表，4版本又去掉了adb表
         * 那这样用户使用肯定不能动态的去每次修改相应的逻辑
         * 这时候switch的case不要break就出来作用了
         */
        // 用oldVersion能够知道原先的版本，假设原先处于2版本，此时已经又book以及adb表，现在用户用了3版本
        switch (oldVersion) {
            case 1:
                //创建book
            case 2:
                //创建adb表
            case 3:
                //创建ddd表
            default:
                //
        }
        // 这样就会因为case没有break，就会按照顺序，让2版本应该有的按须初始化好，保证升级成3之前的状态一致，免得表结构不一致

        //当然了，我理解用newVersion更好，直接根据这个，依次创建到newVersion表，免得表结构不一致

        /**
         * 情景分析【主要看这里，比较清晰】：
         * 比如1版本我就一个book表，2版本多了个adb表，3版本多了个ddd表，4版本又去掉了adb表
         * 用户直接装的4版本，4版本的onCreate自然就是所需的数据库，因为是第一次装，不会进入onUpgrade
         * 但如果之前装的是3版本呢，那就要考虑3升4的差异，也就是要提前有book表，adb表，ddd表
         * 那么就要有一个oldVersion的switch，当是3的时候，把book表，adb表，ddd表准备好
         *
         * 依次类推2版本
         * 准备好book表，adb表
         *
         * 所以干脆用这个，不要break，直接按序走就行
         *
         */


    }
}
