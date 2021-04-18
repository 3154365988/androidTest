package com.mythsun.fileos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mythsun.fileos.database.DatabaseHelper;
import com.mythsun.fileos.permission.PermissionUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    Button btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    Context context;
    private String TAG = "文件os";
    int REQUEST_CODE_ASK_PERMISSIONS = 123;
    int REQUEST_CODE_ASK_PERMISSIONS1 = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplication();
        initView();
        Log.d(TAG, "onCreate: 初始化按钮成功");
        setBtn31();
        setBtn41();
        setBtn5();
        setBtn6();
        setBtn7();
        setBtn8();
        setBtn9();
        setBtn10();
        setBtn11();
        setBtn12();
    }

    /**
     * 权限申请，拒绝后回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111:
                Log.d(TAG, "收到111");
                // 要么此时有权限，要么没有
                break;
            default:
                Log.d(TAG, "收到别的");
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void setBtn41() {
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn4");
                Cursor cursor = null;
                // 需要通讯录权限
                if (PermissionUtil.permissionApply(MainActivity.this)) {
                    cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                    while (cursor.moveToNext()) {
                        // 姓名
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        // 手机号
                        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d(TAG, "姓名：" + name + " 手机号：" + phone);
                    }
                    cursor.close();
                    Toast.makeText(context, "读取成功通讯录成功", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * 权限申请方法
             * @return
             */
//            private boolean permissionApply() {
//                Log.d(TAG, "申请权限");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                        Log.d(TAG, "无权限，准备申请");
//
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
//                            Log.d(TAG, "弹框申请");
//                            // 第二次以及以后用到
//                            showDialog("权限不存在，需要同意授权", MainActivity.this, Manifest.permission.READ_CONTACTS);
//                        } else {
//                            // 先进这里，尝试向用户申请一次，用户拒绝了，以后会进入上面的逻辑；若是永久拒绝，则会直接进入111回调
//                            Log.d(TAG, "其余情况");
//                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 111);
//                        }
//                        return false;
//                    } else {
//                        Log.d(TAG, "权限已存在");
//                        return true;
//                    }
//                } else {
//                    Log.d(TAG, "低版本直接权限申请通过");
//                    return true;
//                }
//            }
        });
    }

    private void setBtn31() {
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn3");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 开启事务
                db.beginTransaction();
                try {
                    // 要做的事就是删除这个表，但是下面抛出异常，根据事务的处理，没有setTransactionSuccessful的事务在事务endTransaction的时候，会回滚到beginTransaction的状态
                    db.delete("book", null, null);
                    // 手动抛出异常，让事务失败，把这个异常去掉的话，后面就能继续执行了
                    // 用这个异常来验证事物的整体性
                    if (true) {
                        throw new NullPointerException();
                    }
                    // 事物执行成功需要这句话
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 结束事务【区别于事务执行成功】
                    db.endTransaction();
                }


                Toast.makeText(context, "数据修改成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn12() {
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn12");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 插入数据，execSQL单参数的也行，就是直接sql语句
                db.execSQL("insert into book (name,author,pages,price) values (?,?,?,?)",
                        new String[]{"王五", "王五的作者", "555", "1445.25"});
                select(db);
                db.execSQL("update book set price = ? where name = ?",
                        new String[]{"666.6", "王五"});
                select(db);
                db.execSQL("delete from book where pages < ?",
                        new String[]{"200"});
                select(db);
                Toast.makeText(context, "数据操作成功", Toast.LENGTH_SHORT).show();
            }

            private void select(SQLiteDatabase db) {
                Cursor cursor = db.rawQuery("select * from book", null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        // 来不及显示完会显示最后一个
//                        Toast.makeText(context, "数据为 " + name + " " + author + " " + pages + " " + price, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "数据为 " + name + " " + author + " " + pages + " " + price);
                    } while (cursor.moveToNext());
                }
                Log.d(TAG, "分割线----------------------------------");
                cursor.close();
            }
        });
    }

    private void setBtn11() {
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn11");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 其实Cursor就相当于游标，数据的行指针的意思
                Cursor cursor = db.query("book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        // 来不及显示完会显示最后一个
                        Toast.makeText(context, "数据为 " + name + " " + author + " " + pages + " " + price, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "数据为 " + name + " " + author + " " + pages + " " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    private void setBtn10() {
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn10");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("book", "pages<?", new String[]{"200"});
                Toast.makeText(context, "数据删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn9() {
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn9");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 第一条数据
                ContentValues values = new ContentValues();
                values.put("price", 1001.2);
                db.update("book", values, "name=?", new String[]{"张三"});
                Toast.makeText(context, "数据修改成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn8() {
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn8");
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 第一条数据
                ContentValues values = new ContentValues();
                values.put("name", "张三");
                values.put("author", "作者");
                values.put("pages", 456);
                values.put("price", 16.96);
                db.insert("book", null, values);
                // 第二条数据
                values.clear();
                values.put("name", "李四");
                values.put("author", "作者1");
                values.put("pages", 112);
                values.put("price", 15566.2);
                db.insert("book", null, values);
                Toast.makeText(context, "数据加入成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn7() {
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn7");
                // 此时version已经是2，代表升级了，版本号是用来调用onUpgrade的
                DatabaseHelper helper = new DatabaseHelper(context, "saveBase.db", null, 2);
                helper.getWritableDatabase();
                Toast.makeText(context, "数据库创建结束", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn6() {
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn6");
                SharedPreferences sp = getSharedPreferences("sp测试", MODE_PRIVATE);
                int a = sp.getInt("int", 1);
                String b = sp.getString("string", "s");
                Toast.makeText(context, "sp取数据" + a + b, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn5() {
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: setBtn5");
                SharedPreferences.Editor editor = getSharedPreferences("sp测试", MODE_PRIVATE).edit();
                editor.putInt("int", 1);
                editor.putString("string", "s");
                editor.commit();
                Toast.makeText(context, "sp保存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn4() {
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn4");
                String data = "";
                FileInputStream in = null;
                BufferedWriter writer = null;
                try {
                    in = openFileInput("saveTxt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    data = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "读取成功" + data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBtn3() {
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Btn3 click");
                String data = "测试保存的文本，不转码。";
                FileOutputStream fos = null;
                BufferedWriter writer = null;
                try {
                    fos = openFileOutput("saveTxt", Context.MODE_APPEND);
                    writer = new BufferedWriter(new OutputStreamWriter(fos));
                    writer.write(data);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(context, "写入成功", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Btn3 click over");
            }
        });
    }

    private void initView() {
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btn10 = findViewById(R.id.button10);
        btn11 = findViewById(R.id.button11);
        btn12 = findViewById(R.id.button12);
    }
}
