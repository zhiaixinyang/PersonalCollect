package com.example.mbenben.studydemo.view.horizontalselectedview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.radar.RadarViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/6/22.
 */

public class HorizontalselectedActivity extends BaseActivity {
    @BindView(R.id.horizontal_select)
    HorizontalselectedView horizontalselectedView;
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, HorizontalselectedActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_horizontalselected);

        ButterKnife.bind(this);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add((i + 1) * 1000 + "");
        }
        horizontalselectedView.setData(data);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
