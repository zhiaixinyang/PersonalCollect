package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.mbenben.studydemo.utils.permission.PermissionUtils;


/**
 * @author MDove on 2018/4/19.
 */
public class BallWidgetHelper {
    /**
     * 检测是否有悬浮权限
     * {@link #requestOverlaysPermission(Activity, int)}
     *
     * @param context
     * @return true 拥有 悬浮权限，反之没有
     */
    public static boolean checkOverlaysPermission(@NonNull Context context) {
        return PermissionUtils.hasOverlaysPermission(context);
    }

    public static void requestOverlaysPermission(@NonNull Activity activity, int requestCode) {
        PermissionUtils.openOverlayPermissionActivity(activity, activity.getPackageName(), requestCode);
    }
}
