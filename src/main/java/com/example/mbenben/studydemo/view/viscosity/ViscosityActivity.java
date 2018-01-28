package com.example.mbenben.studydemo.view.viscosity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原项目作者GitHub：https://github.com/smartbetter/AndroidGooView
 */
public class ViscosityActivity extends AppCompatActivity{
    @BindView(R.id.viscosity) ViscosityView viscosityView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viscosity);
        ButterKnife.bind(this);
        viscosityView.setOnReleaseListener(new ViscosityView.OnReleaseListener() {
            @Override
            public void onDisappear() {
                ToastUtils.showShort( "消失了..");
            }

            @Override
            public void onReset(boolean isOutOfRange) {
                ToastUtils.showShort( "返回原地.." + isOutOfRange);
            }
        });
    }
}
