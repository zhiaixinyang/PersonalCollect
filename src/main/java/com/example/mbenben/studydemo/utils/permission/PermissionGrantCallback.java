package com.example.mbenben.studydemo.utils.permission;

/**
 * 权限请求结果回调
 * <p>
 * 配合{@link PermissionUtils}使用
 *
 * @author MDove on 2018/2/27.
 */
public interface PermissionGrantCallback {

    /**
     * 权限请求被批准
     *
     * @param requestCode
     */
    void permissionGranted(int requestCode);

    /**
     * 权限请求被拒绝
     *
     * @param requestCode true 需要告知申请该权限的原因， false 请忽略
     */
    void permissionRefused(int requestCode);
}
