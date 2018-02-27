package com.example.mbenben.studydemo.basenote.alarm;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

/**
 * @author Created by MDove on 2018/2/27.
 */

public class AlarmService extends IntentService {

    public AlarmService() {
        super("alarm_service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        try {
            AlarmTaskManager.Task task = AlarmTaskManager.Task.valueOf(action);
            IAlarmTask alarmTask = task.newTask();
            alarmTask.doWork();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
