package com.example.mbenben.studydemo.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.app.backup.BackupAgent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * @author MDove on 2018/4/12
 */
public abstract class WeakRefHandler<T> extends Handler {
    private static Class<?> sFragmentClass;
    private static Method sGetActivity;
    private final WeakReference<T> mContext;

    static {
        try {
            sFragmentClass = Class.forName("android.support.v4.app.Fragment", false,
                    Thread.currentThread().getContextClassLoader());
            sGetActivity = sFragmentClass.getDeclaredMethod("getActivity", new Class[0]);
        } catch (Exception e) {
            e.printStackTrace();
            sFragmentClass = null;
            sGetActivity = null;
        }
    }

    public WeakRefHandler(T context) {
        this.mContext = new WeakReference<T>(context);
    }

    public WeakRefHandler(Callback callback, T context) {
        super(callback);
        this.mContext = new WeakReference<T>(context);
    }

    public WeakRefHandler(Looper looper, T context) {
        super(looper);
        this.mContext = new WeakReference<T>(context);
    }

    public WeakRefHandler(Looper looper, Callback callback, T context) {
        super(looper, callback);
        this.mContext = new WeakReference<T>(context);
    }

    @Override
    public void dispatchMessage(Message msg) {
        T context = this.mContext.get();
        if (context != null) {
            Activity activity = null;
            if ((sFragmentClass != null) && (sFragmentClass.isInstance(context))) {
                try {
                    activity = (Activity) sGetActivity.invoke(context, (Object[]) null);
                } catch (Exception localException) {
                }
            } else if ((context instanceof Activity)) {
                activity = (Activity) context;
            }
            if (activity == null && (context instanceof Application
                    || context instanceof Service
                    || context instanceof BackupAgent)) {
                super.dispatchMessage(msg);
                return;
            }
            if ((activity == null) || activity.isFinishing()) {
                return;
            }
            super.dispatchMessage(msg);
        }
    }

    protected abstract void processMessage(T context, Message msg);
}
