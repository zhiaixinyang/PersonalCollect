package com.example.mbenben.studydemo.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by MDove 2018/2/27.
 */
public class IntentUtils {

//    public static void launchCurrentAppMarket(@NonNull Context context) {
//        launchMarket(context, context.getPackageName());
//    }

//    public static void launchMarket(@NonNull Context context, @NonNull String packageName) {
//        launchMarket(context, packageName, null);
//    }

//    public static void launchMarket(@NonNull Context context, @NonNull String packageName, @Nullable String mediaSource) {
//        String url = "market://details?id=" + packageName;
//        if (!TextUtils.isEmpty(mediaSource)) {
//            url = UrlHelper.appendUrlParams(url, mediaSource);
//        }
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//        try {
//            context.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            String targetUrl = "https://play.google.com/store/apps/details?id=" + packageName;
//            if (!TextUtils.isEmpty(mediaSource)) {
//                targetUrl = UrlHelper.appendUrlParams(targetUrl, mediaSource);
//            }
//            uri = Uri.parse(targetUrl);
//            intent = new Intent(Intent.ACTION_VIEW, uri);
//            try {
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            } catch (ActivityNotFoundException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }

    public static void launchSystemShareDialog(@NonNull Context context, @NonNull String shareContent, String
            shareDialogTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        context.startActivity(Intent.createChooser(intent, shareDialogTitle));
    }

    public static void launchByUri(@NonNull Context context, @NonNull String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        boolean enable = resolveActivity(context, intent);
        if (!enable) {
            return;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public static boolean resolveActivity(@NonNull Context context, @NonNull Intent intent) {
        PackageManager pm = context.getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        return cn != null;
    }

    public static boolean isReceiverRegisted(@NonNull Context context, @NonNull Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        return resolveInfos != null && !resolveInfos.isEmpty();
    }

    /**
     * 判断程序是否安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isAppInstalled(@NonNull Context context, @NonNull String pkgName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(pkgName, 0);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return installed;
    }

    /**
     * 打开安装的程序
     *
     * @param context
     * @param pkgName
     */
    public static void startupApp(@NonNull Context context, @NonNull String pkgName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 打开已安装程序的制定Activity
     *
     * @param context
     * @param pkgName
     * @param activityName
     */
    public static void startupAppActivity(@NonNull Context context, @NonNull String pkgName, @NonNull String activityName) {
        ComponentName componetName = new ComponentName(pkgName, activityName);
        try {
            Intent intent = new Intent();
            intent.setComponent(componetName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void openOverlayPermissionActivity(@NonNull Activity activity, @NonNull String pkg, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
        activity.startActivityForResult(intent, requestCode);
    }
}
