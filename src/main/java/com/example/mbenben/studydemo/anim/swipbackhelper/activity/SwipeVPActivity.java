package com.example.mbenben.studydemo.anim.swipbackhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.anim.swipbackhelper.view.SwipeBackHelper;

/**
 * Created by MDove on 2017/9/18.
 */

public class SwipeVPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        ViewPager viewPager = new ViewPager(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        viewPager.setLayoutParams(lp);

        setContentView(viewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return SwipeFragment.newInstance("来卖萌的Fragment：" + position);
                } else if (position == 1) {
                    return SwipeFragment.newInstance("来卖萌的Fragment：" + position);
                } else if (position == 2) {
                    return SwipeFragment.newInstance("来卖萌的Fragment：" + position);
                }
                return SwipeFragment.newInstance("来卖萌的Fragment：" + position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }
}
