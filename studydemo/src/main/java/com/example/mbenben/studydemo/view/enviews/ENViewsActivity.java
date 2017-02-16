package com.example.mbenben.studydemo.view.enviews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allfragments.DownloadFragment;
import com.example.mbenben.studydemo.view.enviews.allfragments.LoadingFragment;
import com.example.mbenben.studydemo.view.enviews.allfragments.PlayFragment;
import com.example.mbenben.studydemo.view.enviews.allfragments.RefreshFragment;
import com.example.mbenben.studydemo.view.enviews.allfragments.ScrollFragment;
import com.example.mbenben.studydemo.view.enviews.allfragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class ENViewsActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private DownloadFragment downloadFragment;
    private LoadingFragment loadingFragment;
    private PlayFragment playFragment;
    private RefreshFragment refreshFragment;
    private ScrollFragment scrollFragment;
    private SearchFragment searchFragment;
    private String[] titles={"Download","Loading","play","Refresh","Scroll","Search"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviews);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        downloadFragment=DownloadFragment.newInstance();
        loadingFragment=LoadingFragment.newInstance();
        playFragment=PlayFragment.newInstance();
        refreshFragment=RefreshFragment.newInstance();
        scrollFragment=ScrollFragment.newInstance();
        searchFragment=SearchFragment.newInstance();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return downloadFragment;
                }else if(position==1){
                    return loadingFragment;
                }else if(position==2){
                    return playFragment;
                }else if(position==3){
                    return refreshFragment;
                }else if(position==4){
                    return scrollFragment;
                }
                return searchFragment;
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
