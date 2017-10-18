package com.example.mbenben.studydemo.basenote.lockscreen;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2017/10/17.
 */

public class LockScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        //添加<uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>  权限
        //去掉系统锁屏页
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        //使Activity在锁屏时仍然能够显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    private int mStartX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        final int action = event.getAction();
//        final float nx = event.getX();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mStartX = nx;
//                onAnimationEnd();
//            case MotionEvent.ACTION_MOVE:
//                handleMoveView(nx);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                doTriggerEvent(nx);
//                break;
//        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK: {
                return true;
            }
            case KeyEvent.KEYCODE_MENU:{
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
