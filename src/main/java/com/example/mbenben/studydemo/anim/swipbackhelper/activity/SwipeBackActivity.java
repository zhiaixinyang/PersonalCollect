package com.example.mbenben.studydemo.anim.swipbackhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.anim.swipbackhelper.view.SwipeBackHelper;

/**
 * Created by MDove on 2017/9/18.
 */

public class SwipeBackActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);

        setContentView(R.layout.activity_swipeback);

        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Intent to1 = new Intent(SwipeBackActivity.this, Swipe1Activity.class);
                startActivity(to1);
                break;
            case R.id.btn_2:
                Intent to2 = new Intent(SwipeBackActivity.this, SwipeRLVActivity.class);
                startActivity(to2);
                break;
            case R.id.btn_3:
                Intent to3 = new Intent(SwipeBackActivity.this, SwipeVPActivity.class);
                startActivity(to3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
