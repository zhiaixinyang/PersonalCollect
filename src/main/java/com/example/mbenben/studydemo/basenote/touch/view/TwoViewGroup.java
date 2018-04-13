package com.example.mbenben.studydemo.basenote.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.mbenben.studydemo.utils.log.LogUtils;

/**
 * Created by MDove on 2018/4/13.
 */

public class TwoViewGroup extends FrameLayout {
    private static final String TAG = "TouchActivity";

    public TwoViewGroup(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG, "TwoViewGroup -> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG, "TwoViewGroup -> onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d(TAG, "TwoViewGroup -> onTouchEvent");
        return super.onTouchEvent(event);
    }

    public TwoViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
