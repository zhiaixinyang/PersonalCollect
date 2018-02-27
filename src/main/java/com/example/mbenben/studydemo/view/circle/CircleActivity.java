package com.example.mbenben.studydemo.view.circle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bigimage.GlideLoaderActivity;

/**
 * Created by MDove on 2017/1/25.
 */

public class CircleActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, CircleActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_circle);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
