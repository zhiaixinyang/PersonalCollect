package com.example.mbenben.studydemo;

import android.app.Application;
import android.content.Context;

import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.model.HashSetSearchBean;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MDove on 2016/3/10.
 */
public class App extends Application{
    private static Context context;
    private static Set<HashSetSearchBean> data;

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context =getApplicationContext();
        data=new HashSet<>();
    }

    public static Set<HashSetSearchBean> getData() {
        return data;
    }

    public static void addData(HashSetSearchBean searchBean) {
        App.data.add(searchBean);
    }

    private static App instance;

    public static synchronized App getInstance() {
        if (instance==null){
            instance=new App();
        }
        return instance;
    }
    public Context getContext(){
        return context;
    }

}
