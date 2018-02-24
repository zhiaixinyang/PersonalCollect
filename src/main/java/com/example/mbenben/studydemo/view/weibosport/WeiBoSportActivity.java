package com.example.mbenben.studydemo.view.weibosport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bezier.GiftBezierActivity;

/**
 * Created by MDove on 2017/1/3.
 */

public class WeiBoSportActivity extends BaseActivity{
    private WeiBoSportView weiboSport;
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, WeiBoSportActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getStringExtra(ACTION_EXTRA));

        setContentView(R.layout.activity_weibosport);

        weiboSport= (WeiBoSportView) findViewById(R.id.weiboSport);
        weiboSport.setmTargetPercent(60);
        weiboSport.setNumber(40);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

}
