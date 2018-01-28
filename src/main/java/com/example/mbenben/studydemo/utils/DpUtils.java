package com.example.mbenben.studydemo.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import com.example.mbenben.studydemo.App;

/**
 * Created by MBENBEN on 2017/1/1.
 */

public class DpUtils {
    public static int dp2px(float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
            App.getInstance().getContext().getResources().getDisplayMetrics());
    }
    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                App.getInstance().getContext().getResources().getDisplayMetrics());
    }
    /** 获取状态栏高度
     * @param v
     * @return
     */
    public static int getStatusBarHeight(View v) {
        if (v == null) {
            return 0;
        }
        Rect frame = new Rect();
        v.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * px转为sp
     */
    public static int px2sp( float pxValue) {
        final float fontScale = App.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
