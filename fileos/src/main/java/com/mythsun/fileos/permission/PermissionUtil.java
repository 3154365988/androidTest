package com.mythsun.fileos.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mythsun.fileos.MainActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

public class PermissionUtil {
    private static final String TAG = "PermissionUtil";




    public static boolean permissionApply(Activity activity) {
//        AndPermission.with(this)
//                .permission(Permission.CAMERA)
//                .callback(permissionListener)
//                .rationale(new RationaleListener() {
//                    @Override
//                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
//                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
//                        AndPermission.rationaleDialog(MainActivity.this, rationale).show();
//                    }
//                })
//                .start();


        Log.d(TAG, "申请权限");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "无权限，准备申请");
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                    Log.d(TAG, "弹框申请");
                    // 第二次以及以后用到
                    showDialog("通讯录", activity, Manifest.permission.READ_CONTACTS);
                } else {
                    // 先进这里，尝试向用户申请一次，用户拒绝了，以后会进入上面的逻辑；若是永久拒绝，则会直接进入111回调
                    Log.d(TAG, "其余情况");
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, 111);

                }
                return false;
            } else {
                Log.d(TAG, "权限已存在");
                return true;
            }
        } else {
            Log.d(TAG, "低版本直接权限申请通过");
            return true;
        }
    }

    /**
     * 权限申请--后续自提示
     *
     * @param msg        权限名称
     * @param context
     * @param permission
     */
    public static void showDialog(String msg, final Context context,
                                  final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("权限申请");
        alertBuilder.setMessage(msg + " 是必要的权限");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    // 点击确定后再获取一次
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 111);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
