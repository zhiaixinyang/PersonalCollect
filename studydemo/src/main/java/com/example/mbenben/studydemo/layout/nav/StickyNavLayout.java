package com.example.mbenben.studydemo.layout.nav;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created by MBENBEN on 2016/12/26.
 *
 * 原作者项目GitHub：https://github.com/hongyangAndroid/Android-StickyNavLayout
 */

public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = "aaa";

    /**
     * 该方法决定了当前控件是否能接收到其内部View滑动时的参数；
     * 假设你只涉及到纵向滑动，这里可以根据nestedScrollAxes这个参数，进行纵向判断。
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll");
        return true;
    }


    //手指正常滑动时不断调用

    /**
     * 来自谷歌官方翻译：
     * 在目标视图消耗一部分滚动之前，对正在进行的嵌套滚动进行响应。当使用嵌套滚动时，
     * 父视图通常需要一个机会来在嵌套滚动子对象之前使用滚动。 一个例子是一个包含可滚动列表的抽屉。
     * 用户将希望能够在列表本身开始滚动之前将列表完全滚动到视图中。
     * 当嵌套滚动子调用dispatchNestedPreScroll（int，int，int []，int []）时，
     * 调用onNestedPreScroll。
     * 实现应该报告由dx，dy报告的滚动的任何像素如何在消费数组中消耗。
     * 索引0对应于dx，索引1对应于dy。 此参数永远不为null。 消耗的[0]和消耗的[1]的初始值将始终为0。
     *
     * @param target
     * @param dx 内部View移动的dx
     * @param dy 内部View移动的dy
     * @param consumed 如果你需要消耗一定的dx,dy，
     *                 就通过最后一个参数consumed进行指定，
     *                 例如消耗一半的dy:consumed[1]=dy/2
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //上滑动：dy为正；下滑动：dy为负。
        //getScrollY()就是View中Y轴的滑动距离。
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    //惯性滑动时调用一次
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling");
        return false;
    }
    //惯性滑动时调用一次，先于onNestedFling()被调用，如果返回false，此方法不会回调
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling："+velocityY+"getScrollY:"+getScrollY());
        //down - //up+
        if (getScrollY() >= mTopViewHeight){
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    private View mTop;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;

    private OverScroller mScroller;

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = getChildAt(0);
        mNav = getChildAt(1);
        View view = getChildAt(2);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mNav.getMeasuredHeight() + mViewPager.getMeasuredHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }


    public void fling(int velocityY) {
        Log.d(TAG,"fling");
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        Log.d(TAG,"scrollTo");
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG,"computeScroll");
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}