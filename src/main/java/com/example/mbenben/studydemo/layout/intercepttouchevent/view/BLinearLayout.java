package com.example.mbenben.studydemo.layout.intercepttouchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by MBENBEN on 2017/2/15.
 */

public class BLinearLayout extends LinearLayout {
    public BLinearLayout(Context context) {
        super(context);
    }

    public BLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "BLinearLayout-onInterceptTouchEvent-ACTION_DOWN..."+super.onInterceptTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "BLinearLayout-onInterceptTouchEvent-ACTION_UP..."+super.onInterceptTouchEvent(ev));
                break;
            default:break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "BLinearLayout-dispatchTouchEvent-ACTION_DOWN..."+super.dispatchTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "BLinearLayout-dispatchTouchEvent-ACTION_UP..."+super.dispatchTouchEvent(ev));
                break;
            default:break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "BLinearLayout-onTouchEvent-ACTION_DOWN..."+super.onTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "BLinearLayout-onTouchEvent-ACTION_UP..."+super.onTouchEvent(ev));
                break;
            default:break;
        }

        return super.onTouchEvent(ev);
    }
}
