package com.example.mbenben.studydemo.layout.recyclerview.refresh2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.refresh2.view.PullToRefreshBase;


/**
 * create by luoxiaoke 2017/3/30 11:02
 * eamil:wtimexiaoke@gmail.com
 * github:https://github.com/103style
 * csdn:http://blog.csdn.net/lxk_1993
 * 简书：http://www.jianshu.com/u/109656c2d96f
 */
public class NoBottomTabActivityXmlView extends PullToRefreshBase<FrameLayout> {
    /**
     * 判断是否可以刷新 or 是否拦截点击事件
     */
    private boolean canRefresh;

    public NoBottomTabActivityXmlView(Context context) {
        this(context, null);
    }

    public NoBottomTabActivityXmlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoBottomTabActivityXmlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始为可刷新
        canRefresh = true;
    }

    /**
     * 判断是否可以刷新 or 是否拦截点击事件
     *
     * @param canRefresh true 拦截   false 不拦截
     */
    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    /**
     * 设置布局内容
     */
    @Override
    protected FrameLayout createRefreshableView(Context context, AttributeSet attrs) {
        FrameLayout frameLayout = new FrameLayout(context);
        View v = LayoutInflater.from(context).inflate(R.layout.actvity_refresh_2, null);
        v.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.AT_MOST);
        frameLayout.addView(v);
        return frameLayout;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return canRefresh;
    }

    /**
     * 没有做加载
     * 要实现可以参考： http://blog.csdn.net/leehong2005/article/details/12567757
     */
    @Override
    protected boolean isReadyForPullUp() {
        return false;
    }
}
