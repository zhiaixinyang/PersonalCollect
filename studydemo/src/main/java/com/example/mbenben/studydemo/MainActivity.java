package com.example.mbenben.studydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.fragment.AndroidBaseFragment;
import com.example.mbenben.studydemo.fragment.AnimsFragment;
import com.example.mbenben.studydemo.fragment.LayoutFragment;
import com.example.mbenben.studydemo.fragment.NetFragment;
import com.example.mbenben.studydemo.fragment.ViewsFragment;
import com.example.mbenben.studydemo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/17.
 */

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private LayoutFragment layoutFragment;
    private NetFragment netFragment;
    private ViewsFragment viewsFragment;
    private AnimsFragment animsFragment;
    private AndroidBaseFragment androidBaseFragment;

    private String[] titles={"Layout","Net","Anim","Views","Base"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        layoutFragment=LayoutFragment.newInstance("和布局相关的Demo");
        netFragment=NetFragment.newInstance("网络请求想过的Demo");
        animsFragment=AnimsFragment.newInstance("动画相关的Demo");
        viewsFragment=ViewsFragment.newInstance("自定义View相关的Demo");
        androidBaseFragment=new AndroidBaseFragment().newInstance("Android的基本应用");

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return layoutFragment;
                }else if(position==1){
                    return netFragment;
                }else if(position==2){
                    return animsFragment;
                }else if (position==3){
                    return viewsFragment;
                }
                return androidBaseFragment;
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
