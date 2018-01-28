package com.example.mbenben.studydemo.layout.nestedscroll;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by MBENBEN on 2016/12/26.
 */

public class MyNestedScrollLayout extends LinearLayout implements NestedScrollingParent{
    private NestedScrollingParentHelper nestedScrollingParentHelper;
    public MyNestedScrollLayout(Context context) {
        super(context);
        nestedScrollingParentHelper=new NestedScrollingParentHelper(this);
    }

    public MyNestedScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        nestedScrollingParentHelper=new NestedScrollingParentHelper(this);
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child,target,nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < topHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0&& !ViewCompat.canScrollVertically(target, -1);;
        if (hiddenTop||showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private View top,indicator,viewPager;
    private int topHeight;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        top=getChildAt(0);
        indicator=getChildAt(1);
        viewPager =getChildAt(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topHeight=top.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight()-indicator.getMeasuredHeight();
    }
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > topHeight) {
            y = topHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }
}
