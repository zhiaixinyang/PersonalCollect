package com.example.mbenben.studydemo.layout.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/12/13.
 */

public class IndicatorActivity extends AppCompatActivity {
    @BindView(R.id.indicator) MyViewPagerIndicator myViewPagerIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private List<String> titles=new ArrayList<>();

    private List<ColorTrackView> tabs = new ArrayList<>();
    private List<MyTestFragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        initViewAndDatas();
        initEvents();

        myViewPagerIndicator.setTitles(titles);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.size();
            }

        });
        myViewPagerIndicator.setViewPager(viewPager);
    }

    private void initEvents() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                if (positionOffset > 0) {
                    ColorTrackView left = tabs.get(position);
                    ColorTrackView right = tabs.get(position + 1);

                    left.setDirection(1);
                    right.setDirection(0);

                    left.setProgress( 1-positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewAndDatas() {
        ButterKnife.bind(this);

        for (int i = 0; i < 10; i++) {
            titles.add("标题-"+i);
        }

        for (String title:titles){
            fragments.add(MyTestFragment.newInstance(title));
        }

        tabs.add((ColorTrackView) findViewById(R.id.id_tab_01));
        tabs.add((ColorTrackView) findViewById(R.id.id_tab_02));
        tabs.add((ColorTrackView) findViewById(R.id.id_tab_03));
    }

}
