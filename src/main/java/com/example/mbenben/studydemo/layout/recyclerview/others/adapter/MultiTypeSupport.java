package com.example.mbenben.studydemo.layout.recyclerview.others.adapter;

/**
 * Created by Darren on 2016/12/28.
 * Email: 240336124@qq.com
 * Description:  多布局支持接口
 *
 * 原作者项目GitHub：https://github.com/Shenmowen/RecyclerAnalysis
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    public int getLayoutId(T item, int position);
}
