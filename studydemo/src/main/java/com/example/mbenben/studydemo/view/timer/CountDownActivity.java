package com.example.mbenben.studydemo.view.timer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.view.timer.view.CountDownView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/10/8.
 */

public class CountDownActivity extends AppCompatActivity {
    @BindView(R.id.fragment_count_down)
    CountDownView countDownView;
    @BindView(R.id.fragment_count_down_start_btn)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);
        countDownView.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
            @Override
            public void onStartCount() {
                ToastUtils.showShort("开始计时");
            }

            @Override
            public void onFinishCount() {
                ToastUtils.showShort("计时结束");
            }
        });
        countDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("被点击");

            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                countDownView.start();
            }
        });
    }
}
