package com.example.mbenben.studydemo.view.select.select;

/**
 * Created by yany on 2017/10/16.
 *
 * 原作者项目地址：https://insight.io/github.com/totond/BooheeRuler/tree/master
 */

public interface RulerCallback {
    //选取刻度变化的时候回调
    void onScaleChanging(float scale);
    //选取刻度变化完成的时候回调
//    void afterScaleChanged(float scale);
}
