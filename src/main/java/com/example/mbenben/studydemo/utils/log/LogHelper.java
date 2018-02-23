package com.example.mbenben.studydemo.utils.log;

import android.support.compat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import com.example.mbenben.studydemo.utils.Singleton;

/**
 * @author MDove on 2018/2/23.
 */

public class LogHelper implements ILog {

    private static boolean DEBUG = BuildConfig.DEBUG;

    private LogHelper() {
    }

    private static Singleton<LogHelper> gInstance = new Singleton<LogHelper>() {
        @Override
        protected LogHelper create() {
            return new LogHelper();
        }
    };

    public static LogHelper getInstance() {
        return gInstance.get();
    }

    public LogHelper setDebug(boolean debug) {
        DEBUG = debug;
        return this;
    }

    @Override
    public void d(String tag, String msg) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    @Override
    public void d(String tag, String msg, Throwable throwable) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg, throwable);
        }
    }

    @Override
    public void i(String tag, String msg) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    @Override
    public void i(String tag, String msg, Throwable throwable) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg, throwable);
        }
    }

    @Override
    public void w(String tag, String msg) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    @Override
    public void w(String tag, String msg, Throwable throwable) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg, throwable);
        }
    }

    @Override
    public void e(String tag, String msg) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    @Override
    public void e(String tag, String msg, Throwable throwable) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg, throwable);
        }
    }
}
