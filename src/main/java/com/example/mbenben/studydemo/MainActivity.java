package com.example.mbenben.studydemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mbenben.studydemo.basenote.lockscreen.LockScreenService;
import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.db.SearchDBManager;
import com.example.mbenben.studydemo.fragment.AndroidBaseFragment;
import com.example.mbenben.studydemo.fragment.AnimsFragment;
import com.example.mbenben.studydemo.fragment.LayoutFragment;
import com.example.mbenben.studydemo.fragment.NetFragment;
import com.example.mbenben.studydemo.fragment.ViewsFragment;
import com.example.mbenben.studydemo.utils.ToastHelper;
import com.example.mbenben.studydemo.utils.permission.PermissionGrantCallback;
import com.example.mbenben.studydemo.utils.permission.PermissionUtils;
import com.example.mbenben.studydemo.view.AboutMeDialog;
import com.example.mbenben.studydemo.view.adline.AdHeadline;
import com.example.mbenben.studydemo.view.adline.HeadlineBean;
import com.example.mbenben.studydemo.view.searchbox.SearchFragment;
import com.example.mbenben.studydemo.view.searchbox.custom.IOnSearchClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/17.
 */

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, IOnSearchClickListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ad_headline)
    AdHeadline adHeadline;
    private LayoutFragment layoutFragment;
    private NetFragment netFragment;
    private ViewsFragment viewsFragment;
    private AnimsFragment animsFragment;
    private AndroidBaseFragment androidBaseFragment;

    private List<HeadlineBean> adHeadLineData = new ArrayList<>();

    private SearchFragment searchFragment;
    private SearchDBManager manager;

    private String[] titles = {"Layout", "Net", "Anim", "Views", "Base"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
    }

    private void initView() {
        ButterKnife.bind(this);

        //启动锁屏服务
        Intent toLockScreen = new Intent(this, LockScreenService.class);
        startService(toLockScreen);

        manager = new SearchDBManager();

        setSupportActionBar(toolbar);

        adHeadLineData.add(new HeadlineBean("作者", "个人制作，简单粗糙，见谅见谅"));
        adHeadLineData.add(new HeadlineBean("内容", "如果项目原作者感到侵权，立马删除..."));
        adHeadLineData.add(new HeadlineBean("拜谢", "您的包容与鼓励是作者莫大的荣幸"));
        adHeadline.setData(adHeadLineData);

        searchFragment = SearchFragment.newInstance();
        layoutFragment = LayoutFragment.newInstance("和布局相关的Demo");
        netFragment = NetFragment.newInstance("网络请求想过的Demo");
        animsFragment = AnimsFragment.newInstance("动画相关的Demo");
        viewsFragment = ViewsFragment.newInstance("自定义View相关的Demo");
        androidBaseFragment = AndroidBaseFragment.newInstance("Android的基本应用");

        toolbar.setOnMenuItemClickListener(this);
        searchFragment.setOnSearchClickListener(this);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return layoutFragment;
                } else if (position == 1) {
                    return netFragment;
                } else if (position == 2) {
                    return animsFragment;
                } else if (position == 3) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search://点击搜索
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                break;
            case R.id.btn_about_me:
                AboutMeDialog aboutMeDialog = new AboutMeDialog(this);
                aboutMeDialog.show();
                break;
        }
        return true;
    }

    @Override
    public void OnSearchClick(String keyword) {
//        List<SearchBean> searchList = manager.query(keyword);
//        Intent toRlv = new Intent(GitHubLayoutManagerActivity.this, SearchRlvActivity.class);
//        toRlv.putExtra("search_rlv", (Serializable) searchList);
//        startActivity(toRlv);
        ToastHelper.shortToast("搜索功能暂时关闭，需要优化");
    }

    private void checkPermission() {
        String[] storagePermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!PermissionUtils.hasPermissions(this, storagePermissions)) {
            ToastHelper.showToastCenter("部分效果需要一些权限");
            PermissionUtils.requestPermission(this, 0, storagePermissions, new PermissionGrantCallback() {

                @Override
                public void permissionGranted(int requestCode) {

                }

                @Override
                public void permissionRefused(int requestCode) {

                }
            });
        }
    }
}
