package com.example.mbenben.studydemo.basenote.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.utils.log.LogUtils;

/**
 * Created by MDove on 2018/4/13.
 */

public class ThreeView extends View {
    private static final String TAG = "OneViewGroup";

    public ThreeView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.d(TAG, "ThreeView -> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d(TAG, "ThreeView -> onTouchEvent");
        return super.onTouchEvent(event);
    }

    public ThreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.d(TAG, "ThreeView -> onTouch");
                int type=event.getAction();
                switch (type){
                    case MotionEvent.ACTION_DOWN:{
                        LogUtils.d(TAG, "ThreeView -> ACTION_DOWN");
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        LogUtils.d(TAG, "ThreeView -> ACTION_MOVE");
                        return false;
                    }
                }
                return false;
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "ThreeView -> onClick");
            }
        });
    }

}
