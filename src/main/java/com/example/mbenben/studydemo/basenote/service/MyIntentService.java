package com.example.mbenben.studydemo.basenote.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by MBENBEN on 2017/3/20.
 */

public class MyIntentService extends IntentService {
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**
         *  如果您决定还重写其他回调方法（如 onCreate()、onStartCommand() 或 onDestroy()），
         *  请确保调用超类实现，以便 IntentService 能够妥善处理工作线程的生命周期。
         */
        //START_STICKY：被杀死后重启
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //此处运行在子线程中，但是耗时操作完成后，将会调用stopSelf自我销毁
        //通常我们会在这里做一些工作，比如下载文件。
    }
}
