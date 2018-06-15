package com.example.mbenben.studydemo.view.bezier.water;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bezier.ClearBezierActivity;

/**
 * Created by MDove on 2018/6/15.
 */

public class WaterActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, WaterActivity.class);
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
        setContentView(R.layout.activity_bezier_water);
    }
}