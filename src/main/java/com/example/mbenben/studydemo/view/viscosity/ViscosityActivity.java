package com.example.mbenben.studydemo.view.viscosity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.view.chart.ChartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/15.
 * <p>
 * 原项目作者GitHub：https://github.com/smartbetter/AndroidGooView
 */
public class ViscosityActivity extends BaseActivity {
    @BindView(R.id.viscosity)
    ViscosityView viscosityView;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, ViscosityActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_viscosity);
        ButterKnife.bind(this);
        viscosityView.setOnReleaseListener(new ViscosityView.OnReleaseListener() {
            @Override
            public void onDisappear() {
                ToastUtils.showShort("消失了..");
            }

            @Override
            public void onReset(boolean isOutOfRange) {
                ToastUtils.showShort("返回原地.." + isOutOfRange);
            }
        });
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
