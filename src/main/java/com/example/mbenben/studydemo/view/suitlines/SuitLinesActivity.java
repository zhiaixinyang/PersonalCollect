package com.example.mbenben.studydemo.view.suitlines;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.chart.ChartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MDove on 2018/5/30.
 */

public class SuitLinesActivity extends BaseActivity {
    private SuitLines mLines;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, SuitLinesActivity.class);
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
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_chart_line);

        mLines = (SuitLines) findViewById(R.id.suitlines);

        final List<Unit> lines = new ArrayList<>();
        lines.add(new Unit(0, "时间"));
        for (int i = 0; i < 5; i++) {
            lines.add(new Unit(new Random().nextInt(100), (i + 1) + "");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLines.setXySize(12);
                mLines.setDefaultOneLineColor(Color.BLACK);
                mLines.feedWithAnim(lines);
            }
        }, 250);
    }
}
