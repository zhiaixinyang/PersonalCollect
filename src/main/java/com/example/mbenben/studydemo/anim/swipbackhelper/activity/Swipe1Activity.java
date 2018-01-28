package com.example.mbenben.studydemo.anim.swipbackhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.anim.swipbackhelper.view.SwipeBackHelper;

/**
 * Created by MDove on 2017/9/18.
 */

public class Swipe1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        setContentView(R.layout.activity_empty_text);

        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }
}
