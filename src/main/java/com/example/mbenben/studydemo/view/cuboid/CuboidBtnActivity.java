package com.example.mbenben.studydemo.view.cuboid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.util.DensityUtil;
import com.example.mbenben.studydemo.view.bigimage.GlideLoaderActivity;

/**
 * Created by MDove on 2018/1/22.
 */

public class CuboidBtnActivity extends BaseActivity {
    private WaveView waveView;
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, CuboidBtnActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_cuboid_btn);

        waveView = (WaveView) findViewById(R.id.wave);
        waveView.setInitialRadius(DensityUtil.dip2px(this, 88f))
                .setMaxRadius(DensityUtil.getScreenWidth(this) / 2)
                .setDuration(2000)
                .setSpeed(500)
                .setStyle(Paint.Style.FILL)
                .setColor(Color.WHITE)
                .setInitialAlpha(102)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waveView.stopImmediately();
    }
}
