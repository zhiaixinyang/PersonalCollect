package com.example.mbenben.studydemo.layout.recyclerview.others.adapter;

import android.view.View;

/**
 * description:
 * <p/>
 * Created by 曾辉 on 2016/11/18.
 * QQ：240336124
 * Email: 240336124@qq.com
 * Version：1.0
 */
public abstract class BannerAdapter {
    /**
     * 1.获取根据位置获取ViewPager里面的子View
     */
    public abstract View getView(int position,View convertView);

    /**
     * 5.获取轮播的数量
     */
    public abstract int getCount();

    /**
     * 6.根据位置获取广告位描述
     */
    public String getBannerDesc(int position){
        return "";
    }
}
