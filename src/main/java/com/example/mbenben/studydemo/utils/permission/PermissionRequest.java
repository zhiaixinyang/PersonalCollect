package com.example.mbenben.studydemo.utils.permission;

import java.util.ArrayList;

/**
 * @author MDove on 2018/2/27.
 */
public class PermissionRequest {
    private ArrayList<String> permissions;
    private int requestCode;
    private PermissionGrantCallback callback;

    public PermissionRequest(int requestCode) {
        this.requestCode = requestCode;
    }

    public PermissionRequest(ArrayList<String> permissions, int requestCode, PermissionGrantCallback callback) {
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.callback = callback;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public PermissionGrantCallback getCallback() {
        return callback;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof PermissionRequest) {
            return ((PermissionRequest) object).requestCode == this.requestCode;
        }
        return false;
    }
}
