package com.example.mbenben.studydemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.utils.ScreenUtils;


/**
 * Created by MBENBEN on 2017/3/22.
 * 用于应对状态栏成为黑色的Bug
 */

public class MyBottomSheetDialog extends BottomSheetDialog {
    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public MyBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected MyBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = ScreenUtils.getScreenHeight();
        int statusBarHeight = ScreenUtils.getStatusHeight();
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }


    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
