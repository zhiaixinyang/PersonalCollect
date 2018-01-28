package com.example.mbenben.studydemo.layout.viewpager.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class AdViewPagerAdapter  extends PagerAdapter {
    private List<ImageView> datas;

    public AdViewPagerAdapter(List<ImageView> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //是否复用当前view对象
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //初始化每个条目要显示的内容
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //拿着position位置 % 集合.size
        int newposition = position % datas.size();
        //获取到条目要显示的内容imageview
        ImageView iv = datas.get(newposition);
        //要把 iv加入到 container 中
        container.addView(iv);
        return iv;
    }

    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //移除条目
        container.removeView((View) object);
    }
}