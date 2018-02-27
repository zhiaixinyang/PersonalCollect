package com.example.mbenben.studydemo.view.radar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.timelytext.TimelyTextActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/4/9.
 */

public class RadarViewActivity extends BaseActivity {
    @BindView(R.id.radarview)
    RadarView radarView;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RadarViewActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_radar_view);
        ButterKnife.bind(this);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    public void btnOpen(View view) {
        radarView.startScan();
    }

    public void btnClose(View view) {
        radarView.stopScan();
    }
}
