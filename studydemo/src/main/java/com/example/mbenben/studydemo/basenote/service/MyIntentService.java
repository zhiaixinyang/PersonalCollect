package com.example.mbenben.studydemo.basenote.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by MBENBEN on 2017/3/20.
 */

public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //此处运行在子线程中，但是耗时操作完成后，将会调用stopSelf自我销毁
    }
}
