package com.example.mbenben.studydemo.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.example.mbenben.studydemo.utils.IntentUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MDove on 2018/2/27.
 */
public class PermissionUtils {

    public static final int PERMISSION_REQUEST_CODE_ALL = 0;
    private static final List<String> PERMISSIONS = new ArrayList<>();
    private static List<PermissionRequest> sPermissionRequests = new ArrayList<>();

    /**
     * 将权限添加进集合中，方便批量请求{@link #PERMISSION_REQUEST_CODE_ALL}
     *
     * @param permission
     */
    public static void addMultiPermissions(String permission) {
        if (!TextUtils.isEmpty(permission)) {
            if (!PERMISSIONS.contains(permission)) {
                PERMISSIONS.add(permission);
            }
        }
    }

    /**
     * 将权限添加进集合中，方便批量请求{@link #PERMISSION_REQUEST_CODE_ALL}
     *
     * @param permissions
     */
    public static void addMultiPermissions(String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (!PERMISSIONS.contains(permission)) {
                    PERMISSIONS.add(permission);
                }
            }
        }
    }

    /**
     * 将权限添加进集合中，方便批量请求{@link #PERMISSION_REQUEST_CODE_ALL}
     *
     * @param permissions
     */
    public static void addMultiPermissions(List<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (!PERMISSIONS.contains(permission)) {
                    PERMISSIONS.add(permission);
                }
            }
        }
    }

    /**
     * 检测是否具有指定权限
     *
     * @param context
     * @param permissions 权限集合
     * @return
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            boolean hasPermission = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            if (!hasPermission) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测是否具有指定权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 验证权限是否都是授予
     * {@link PackageManager#PERMISSION_GRANTED}
     *
     * @param grantResults
     * @return 如果权限都被授予，返回true，否则false
     */
    public static boolean verifyPermissionsResult(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 该权限是否需要给用户解释原因
     *
     * @param activity
     * @param permission
     * @return true，需要告诉用户 app 请求该权限的原因
     */
    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * 该权限是否需要给用户解释原因
     *
     * @param activity
     * @param permissions
     * @return true，需要告诉用户 app 请求该权限的原因
     */
    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String[] permissions) {
        List<String> noGrantedPermissions = getNoGrantedPermission(activity, new ArrayList<String>(Arrays.asList(permissions)), true);
        if (noGrantedPermissions != null && !noGrantedPermissions.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 请求动态权限
     * <p>
     * {@link Manifest.permission}
     * 在 调用之前，最好先调用{@link #shouldShowRequestPermissionRationale(Activity, String)},如果需要告知用户，请请示用户后再请求权限
     *
     * @param activity
     * @param requestCode
     * @param permission
     * @param callback
     */
    public static void requestPermission(@NonNull Activity activity, int requestCode, @NonNull String permission, @Nullable PermissionGrantCallback callback) {
        boolean hasPermissions = hasPermissions(activity, permission);
        if (hasPermissions) {
            if (callback != null) {
                callback.permissionGranted(requestCode);
            }
            return;
        }
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(permission);
        sPermissionRequests.add(new PermissionRequest(permissionList, requestCode, callback));
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * 请求动态权限
     * <p>
     * 在 调用之前，最好先调用{@link #shouldShowRequestPermissionRationale(Activity, String)},如果需要告知用户，请请示用户后再请求权限
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param callback
     */
    public static void requestPermission(@NonNull Activity activity, int requestCode, @NonNull String[] permissions, @Nullable PermissionGrantCallback callback) {
        boolean hasPermissions = hasPermissions(activity, permissions);
        if (hasPermissions) {
            if (callback != null) {
                callback.permissionGranted(requestCode);
            }
            return;
        }
        sPermissionRequests.add(new PermissionRequest(new ArrayList<>(Arrays.asList(permissions)), requestCode, callback));
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * 调用该方法之前请 调用{@link #addMultiPermissions(List)},{@link #addMultiPermissions(String)},{@link #addMultiPermissions(String...)}
     * <p>
     * 否则无效请求.
     * 在 调用之前，最好先调用{@link #shouldShowRequestPermissionRationale(Activity, String[])},如果需要告知用户，请请示用户后再请求权限
     *
     * @param activity
     * @param callback
     * @see #requestPermission(Activity, int, String[], PermissionGrantCallback)
     */
    public static void requestMultiPermissions(@NonNull Activity activity, @Nullable PermissionGrantCallback callback) {
        List<String> permissionList = PERMISSIONS;
        if (permissionList.isEmpty()) {
            return;
        }

        List<String> noGrantedPermission = getNoGrantedPermission(activity, permissionList, false);
        if (noGrantedPermission.isEmpty()) {
            callback.permissionGranted(PERMISSION_REQUEST_CODE_ALL);
            return;
        }
        sPermissionRequests.add(new PermissionRequest(new ArrayList<>(permissionList), PERMISSION_REQUEST_CODE_ALL, callback));
        ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), PERMISSION_REQUEST_CODE_ALL);
    }

    /**
     * 根据授权结果回调,并刷新当前的权限列表
     * 请在{@link Activity#onRequestPermissionsResult(int, String[], int[])}中回调该方法
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionRequest requestResult = new PermissionRequest(requestCode);
        if (sPermissionRequests.contains(requestResult)) {
            PermissionRequest permissionRequest = sPermissionRequests.get(sPermissionRequests.indexOf(requestResult));
            PermissionGrantCallback callback = permissionRequest.getCallback();
            if (verifyPermissionsResult(grantResults)) {
                if (callback != null) {
                    callback.permissionGranted(requestCode);
                }
            } else {
                if (callback != null) {
                    callback.permissionRefused(requestCode);
                }
            }
            sPermissionRequests.remove(requestResult);
        }
    }

    /**
     * 查找未授予的权限列表
     *
     * @param activity        activity
     * @param permissions     待查询的权限列表
     * @param shouldRationale false 会返回所有未授权的 permissions，如果为true 只返回 需要rationale 的permission
     * @return
     */
    @NonNull
    public static List<String> getNoGrantedPermission(@NonNull Activity activity, @NonNull List<String> permissions, boolean shouldRationale) {
        List<String> result = new ArrayList<>();
        int size = permissions.size();
        for (int i = 0; i < size; i++) {
            String permission = permissions.get(i);
            if (!TextUtils.isEmpty(permission)) {
                int granted = ActivityCompat.checkSelfPermission(activity, permission);
                if (granted != PackageManager.PERMISSION_GRANTED) {
                    if (!shouldRationale) {
                        result.add(permission);
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        if (shouldRationale) {
                            result.add(permission);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 打开该应用的权限设置页面，让用户手动授权
     *
     * @param activity
     * @param requestCode
     */
    public static void openPermissionSettings(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 检测是否具有悬浮权限
     *
     * @param context
     * @return
     */
    public static boolean hasOverlaysPermission(Context context) {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context));
    }

    /**
     * 申请悬浮权限
     *
     * @param activity
     * @param pkg
     * @param requestCode
     */
    public static void openOverlayPermissionActivity(Activity activity, String pkg, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IntentUtils.openOverlayPermissionActivity(activity, pkg, requestCode);
        }
    }
}
