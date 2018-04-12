package com.example.mbenben.studydemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.example.mbenben.studydemo.basenote.contentprovider.custom.DataInitHelper;
import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.greendao.DaoSession;
import com.example.mbenben.studydemo.greendao.utils.DaoManager;
import com.example.mbenben.studydemo.model.HashSetSearchBean;
import com.example.mbenben.studydemo.utils.SystemUtils;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MDove on 2016/3/10.
 */
public class App extends Application {
    private static Context context;
    private static Set<HashSetSearchBean> data;
    public static DaoSession mDaoSession;
    public static DaoManager mDaoManager;

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        if (isMainProcess()) {
            context = getApplicationContext();
            data = new HashSet<>();

            mDaoManager = DaoManager.getInstance();
            mDaoManager.init(context);
            if (mDaoSession == null) {
                synchronized (App.class) {
                    if (null == mDaoSession) {
                        mDaoSession = mDaoManager.getDaoMaster().newSession();
                    }
                }
            }

            DataInitHelper.initData();
        }
    }

    /**
     * 判断是否当前允许在主进程
     *
     * @return
     */
    private boolean isMainProcess() {
        String processName = SystemUtils.getCurrentProcessName();
        LogUtils.d("BaseApplication", "process name:" + processName);
        if (TextUtils.isEmpty(processName)) {
            return true;
        }
        return getPackageName().equals(processName);
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static Set<HashSetSearchBean> getData() {
        return data;
    }

    public static void addData(HashSetSearchBean searchBean) {
        App.data.add(searchBean);
    }

    private static App instance;

    //这种写法没必要，Application每个进程只有一个，除非多进程才会有多个
    public static synchronized App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

}
