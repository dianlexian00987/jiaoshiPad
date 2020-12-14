package com.hjq.xtoast.demo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.xtoast.OnClickListener;
import com.hjq.xtoast.OnToastListener;
import com.hjq.xtoast.OnTouchListener;
import com.hjq.xtoast.XToast;
import com.hjq.xtoast.draggable.MovingDraggable;
import com.hjq.xtoast.draggable.SpringDraggable;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XToast
 *    time   : 2019/01/04
 *    desc   : XToast 使用案例
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 这里需要先初始化 ToastUtils，实际开发中这句代码应当放在 Application.onCreate 方法中
        ToastUtils.init(getApplication());
    }



    public void show6(View v) {
        XXPermissions.with(this)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> granted, boolean all) {
                        // 传入 Application 表示这个是一个全局的 Toast
                        new XToast(getApplication())
                                .setView(R.layout.toast_phone1)
                                .setGravity(Gravity.LEFT | Gravity.TOP)
                                .setYOffset(100)
                                // 设置指定的拖拽规则
                                .setDraggable(new SpringDraggable())
                                .setOnClickListener(R.id.iv_guan_kong_head, new OnClickListener<ImageView>() {

                                    @Override
                                    public void onClick(XToast toast, ImageView view) {
                                        ToastUtils.show("我被点击了");
                                        // 点击后跳转到拨打电话界面
                                        // Intent intent = new Intent(Intent.ACTION_DIAL);
                                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        // toast.startActivity(intent);
                                        // 安卓 10 在后台跳转 Activity 需要额外适配
                                        // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onDenied(List<String> denied, boolean never) {
                        new XToast(MainActivity.this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                                .setText(android.R.id.message, "请先授予悬浮窗权限")
                                .show();
                    }
                });
    }


}