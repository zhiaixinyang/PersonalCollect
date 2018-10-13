package com.example.mbenben.studydemo.view.rainyview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.qqhealth.QQHealthActivity;

/**
 * Created by MDove on 2018/10/13.
 */

public class RainyViewActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";
    private RainyView rainyView;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RainyViewActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_rainy_view);
        rainyView = (RainyView) findViewById(R.id.rv);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()){
            rainyView.release();
        }
    }

    public void onStop(View view) {
        rainyView.stop();
    }

    public void onStart(View view) {
        rainyView.start();
    }
}
