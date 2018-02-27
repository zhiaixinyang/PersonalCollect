package com.example.mbenben.studydemo.basenote.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author Created by MDove on 2018/2/27.
 */
public class AlarmTaskManager {

    public enum Task {
        VPN_CONNECT_NOTIFICATION {
            @Override
            public IAlarmTask newTask() {
                return new TimeRemindNotificationAlarmTask();
            }
        };

        public abstract IAlarmTask newTask();
    }

    public static void schedule(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Task task : Task.values()) {
            IAlarmTask alarmTask = task.newTask();
            Intent intent = buildIntent(context, task.name());

            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            long nextTriggerAt = alarmTask.getNextTriggerAt();
//            nextTriggerAt = System.currentTimeMillis() + 10 * 1000;
            long interval = alarmTask.getInterval();
            if (am != null) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, nextTriggerAt, interval, pendingIntent);
            }
        }
    }

    private static Intent buildIntent(Context context, String taskName) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(taskName);
        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}

