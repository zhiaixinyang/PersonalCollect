package com.example.mbenben.studydemo.layout.intercepttouchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MBENBEN on 2017/2/14.
 */

public class MyLinearLayout extends LinearLayout {

    private int mMove;
    private int yDown, yMove;
    private int i = 0;
    private boolean isIntercept = false;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollView scrollView;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        scrollView = (ScrollView) getChildAt(0);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        onInterceptTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int y = (int) ev.getY();
//        int mScrollY = scrollView.getScrollY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                yDown = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                yMove = y;
//                if (yMove - yDown > 0 && mScrollY == 0) {
//                    if (!isIntercept) {
//                        isIntercept = true;
//                    }
//                }else{
//                    isIntercept= false;
//                }
//                break;
//        }
//        return isIntercept;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int y = (int) event.getY();
//        int mScrollY = scrollView.getScrollY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                yDown = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                yMove = y;
//                if (yMove - yDown > 0 && mScrollY == 0) {
//                        mMove = yMove - yDown;
//                        i += mMove;
//                        layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
//                i = 0;
//                break;
//        }
//        return true;
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int mScrollY = scrollView.getScrollY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown > 0 && mScrollY == 0) {
                    if (!isIntercept) {
                        yDown = (int) ev.getY();
                        isIntercept = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                i = 0;
                isIntercept = false;
                break;
        }
        if (isIntercept) {
            mMove = yMove - yDown;
            i += mMove;
            layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
        }
        return isIntercept;
    }
}
