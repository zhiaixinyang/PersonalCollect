package com.example.mbenben.studydemo.layout.nestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.indicator.view.MyViewPagerIndicator;
import com.example.mbenben.studydemo.layout.nav.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2016/12/27.
 */

public class NestedScrollActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private MyViewPagerIndicator indicator;
    private List<String> datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscroll);
        initView();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new TabFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        indicator.setTitles(datas);
        indicator.setViewPager(viewPager);
    }

    private void initView() {
        indicator= (MyViewPagerIndicator) findViewById(R.id.indicator);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        datas=new ArrayList<>();
        datas.add("页面1");
        datas.add("页面2");
        datas.add("页面3");
    }


}
