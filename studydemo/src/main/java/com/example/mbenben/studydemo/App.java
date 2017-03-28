package com.example.mbenben.studydemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by MBENBEN on 2016/3/10.
 */
public class App extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context =getApplicationContext();

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
