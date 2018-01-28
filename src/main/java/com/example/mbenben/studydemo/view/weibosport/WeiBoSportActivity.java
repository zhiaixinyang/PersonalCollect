package com.example.mbenben.studydemo.view.weibosport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/1/3.
 */

public class WeiBoSportActivity extends AppCompatActivity{
    private WeiBoSportView weiboSport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibosport);

        weiboSport= (WeiBoSportView) findViewById(R.id.weiboSport);
        weiboSport.setmTargetPercent(60);
        weiboSport.setNumber(40);
    }

}
