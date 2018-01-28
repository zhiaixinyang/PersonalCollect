package com.example.mbenben.studydemo.layout.intercepttouchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by MBENBEN on 2017/2/15.
 */

public class ALinearLayout extends LinearLayout {
    public ALinearLayout(Context context) {
        super(context);
    }

    public ALinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ALinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "ALinearLayout-onInterceptTouchEvent-ACTION_DOWN..." + super.onInterceptTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "ALinearLayout-onInterceptTouchEvent-ACTION_UP..." + super.onInterceptTouchEvent(ev));
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "ALinearLayout-dispatchTouchEvent-ACTION_DOWN..." + super.dispatchTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "ALinearLayout-dispatchTouchEvent-ACTION_UP..." + super.dispatchTouchEvent(ev));
                break;
            default:
                break;
        }
        //返回false，将不会触发onClick一系列方法
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "ALinearLayout-onTouchEvent-ACTION_DOWN..." + super.onTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "ALinearLayout-onTouchEvent-ACTION_UP..." + super.onTouchEvent(ev));
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
