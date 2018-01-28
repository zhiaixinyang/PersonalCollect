package com.example.mbenben.studydemo.basenote.lockscreen;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by MDove on 2017/10/17.
 */

public class LockScreenService extends Service {

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        //监听关屏广播必须使用动态注册
        IntentFilter mScreenOffFilter = new IntentFilter();
        mScreenOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new LockScreenBroadcastReceiver(), mScreenOffFilter);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在此重新启动,使服务常驻内存
        startService(new Intent(this, LockScreenService.class));
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
