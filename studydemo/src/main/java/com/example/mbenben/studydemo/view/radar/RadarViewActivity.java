package com.example.mbenben.studydemo.view.radar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/4/9.
 */

public class RadarViewActivity extends AppCompatActivity {
    @BindView(R.id.radarview) RadarView radarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);
        ButterKnife.bind(this);
    }

    public void btnOpen(View view){
        radarView.startScan();
    }

    public void btnClose(View view){
        radarView.stopScan();
    }
}
