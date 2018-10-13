package com.example.mbenben.studydemo.view.bottomsectormenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

/**
 * Created by zhaojing on 2018/10/9.
 */

public class BottomSectorMenuActivity extends BaseActivity {
    FloatingActionButton mFb;

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sector_menu);

        mFb = (FloatingActionButton) findViewById(R.id.fb);

        new BottomSectorMenuView.Converter(mFb)
                .setToggleDuration(500, 800)
                .setAnchorRotationAngle(135f)
                .addMenuItem(R.mipmap.add_bg, "拍照", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .addMenuItem(R.mipmap.add_bg, "图片", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .addMenuItem(R.mipmap.add_bg, "文字", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .addMenuItem(R.mipmap.add_bg, "视频", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .addMenuItem(R.mipmap.add_bg, "摄像", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .apply();

    }
}
