package com.example.mbenben.studydemo.view.xiaomi.select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.utils.ToastHelper;
import com.example.mbenben.studydemo.view.xiaomi.select.time.HourAndMinutePicker;

/**
 * Created by MDove on 2018/3/18.
 */

public class SelecterActvity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";
    private String mTitle;
    private HourAndMinutePicker mPicker;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, SelecterActvity.class);
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
        setContentView(R.layout.activity_selecter);

        mPicker = (HourAndMinutePicker) findViewById(R.id.whell_picker);
        mPicker.setOnTimeSelectedListener(new HourAndMinutePicker.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(int hour, int minute) {
                ToastHelper.shortToast("选择的时间：" + hour + ":" + minute);
            }
        });
    }
}
