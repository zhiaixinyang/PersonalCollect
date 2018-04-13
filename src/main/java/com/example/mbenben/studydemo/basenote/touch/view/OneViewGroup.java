package com.example.mbenben.studydemo.basenote.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.mbenben.studydemo.utils.log.LogUtils;

/**
 * Created by MDove on 2018/4/13.
 */

public class OneViewGroup extends LinearLayout {
    private static final String TAG="OneViewGroup";

    public OneViewGroup(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG,"OneViewGroup -> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG,"OneViewGroup -> onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d(TAG,"OneViewGroup -> onTouchEvent");
        return super.onTouchEvent(event);
    }

    public OneViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
