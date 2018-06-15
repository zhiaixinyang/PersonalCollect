package com.example.mbenben.studydemo.view.bezier.magiccircle;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.databinding.ActivityMagicCircleBinding;

/**
 * Created by MDove on 2018/6/15.
 */

public class MagicCircleActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";

    private ActivityMagicCircleBinding mBinding;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, MagicCircleActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_magic_circle);
        mBinding.toolbar.setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setSupportActionBar(mBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.circle.startAnimation();
            }
        });
    }
}
