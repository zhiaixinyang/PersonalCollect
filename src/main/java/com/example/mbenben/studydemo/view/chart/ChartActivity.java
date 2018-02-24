package com.example.mbenben.studydemo.view.chart;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bezier.GiftBezierActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MDove on 2017/1/3.
 */

public class ChartActivity extends BaseActivity {
    private ChartView myChartView;
    private List<Integer> list = new ArrayList<>();
    private RelativeLayout relativeLayout;
    private TextView showText;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_chart);
        init();
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    private void init() {
        showText = new TextView(getApplicationContext());

        myChartView = (ChartView) findViewById(R.id.my_chart_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.linearLayout);
        Random random = new Random();
        while (list.size() < 24) {
            int randomInt = random.nextInt(100);
            list.add(randomInt);
        }
        myChartView.setList(list);
        myChartView.setListener(new ChartView.getNumberListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void getNumber(int number, int x, int y) {
                relativeLayout.removeView(showText);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = x - 100;
                if (x - 100 < 0) {
                    params.leftMargin = 0;
                } else if (x - 100 > relativeLayout.getWidth() - showText.getWidth()) {
                    params.leftMargin = relativeLayout.getWidth() - showText.getWidth();
                }
                params.topMargin = 100;
                showText.setLayoutParams(params);
                showText.setTextColor(getResources().getColor(R.color.white));
                showText.setTextSize(10);
                showText.setText("选择的数字为:" + list.get(number * 2) + "," + list.get(number * 2 + 1));
                relativeLayout.addView(showText);

            }
        });

    }
}
