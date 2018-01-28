package com.example.mbenben.studydemo.view.timer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.view.timer.view.TimerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/10/8.
 */

public class TimerActivity extends AppCompatActivity {
    @BindView(R.id.timer_view)
    TimerView timerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);

        timerView.setTimerSize(18, false);
        timerView.setCountDownTime(0, 0, 10, 10);
        timerView.start();

        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerView.isRunning()) {
                    timerView.stop();
                } else {
                    timerView.start();
                }
            }
        });
        timerView.setOnFinishedListener(new TimerView.OnFinishedListener() {
            @Override
            public void onFinished() {
                ToastUtils.showShort("结束");
            }
        });
    }
}
