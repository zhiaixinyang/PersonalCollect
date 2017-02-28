package com.example.mbenben.studydemo.layout.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.viewpager.fragment.FragmentFour;
import com.example.mbenben.studydemo.layout.viewpager.fragment.FragmentOne;
import com.example.mbenben.studydemo.layout.viewpager.fragment.FragmentThree;
import com.example.mbenben.studydemo.layout.viewpager.fragment.FragmentTwo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/2.
 */

public class ViewPagerActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private FragmentFour fragmentFour;

    private String[] titles={"转场效果1","转场效果2",
            "转场效果3","轮播广告条"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        initView();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return fragmentOne;
                }else if(position==1){
                    return fragmentTwo;
                }else if (position==2){
                    return fragmentThree;
                }
                return fragmentFour;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView() {
        ButterKnife.bind(this);
        fragmentOne=FragmentOne.newInstance("倒影式ViewPager效果");
        fragmentTwo=FragmentTwo.newInstance("ZoomTransformation效果");
        fragmentThree=FragmentThree.newInstance("DepthTransformationx效果");
        fragmentFour=FragmentFour.newInstance("ViewPager轮播广告条");
    }
}
