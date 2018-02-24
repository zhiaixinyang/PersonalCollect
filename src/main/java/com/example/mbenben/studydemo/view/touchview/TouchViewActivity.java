package com.example.mbenben.studydemo.view.touchview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.viscosity.ViscosityActivity;

/**
 * Created by MDove on 18/2/24.
 */

public class TouchViewActivity extends BaseActivity {

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, TouchViewActivity.class);
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
        setContentView(R.layout.activity_touch_view);
    }

    public void onClick(View v) {

        TouchView touchView = new TouchView(this);
        touchView.setBackgroundResource(R.mipmap.p13);
        touchView.setLayoutParams(new ViewGroup.LayoutParams(500, 500));
        ViewGroup vg = (ViewGroup) v.getParent();
        vg.addView(touchView);
    }

}
