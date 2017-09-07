package com.example.mbenben.studytest.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by MBENBEN on 2017/2/15.
 */

public class CButton extends Button {
    public CButton(Context context) {
        super(context);
    }

    public CButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "CButton-dispatchTouchEvent-ACTION_DOWN..."+super.dispatchTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "CButton-dispatchTouchEvent-ACTION_UP..."+super.dispatchTouchEvent(ev));
                break;
            default:break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "CButton-onTouchEvent-ACTION_DOWN..."+super.onTouchEvent(ev));
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa", "CButton-onTouchEvent-ACTION_UP..."+super.onTouchEvent(ev));
                break;
            default:break;
        }
        return super.onTouchEvent(ev);
    }
}
