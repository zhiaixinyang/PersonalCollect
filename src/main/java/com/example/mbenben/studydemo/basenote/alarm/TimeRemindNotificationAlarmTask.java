package com.example.mbenben.studydemo.basenote.alarm;


import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.utils.notification.NotificationHelper;

import java.util.Calendar;

/**
 * @author Created by MDvoe on 2018/2/27.
 */
public class TimeRemindNotificationAlarmTask implements IAlarmTask {

    private static final int NOTIFICATION_HOUR = 8;

    private static final int NOTIFICATION_DURATION = 24 * 60 * 60 * 1000;

    @Override
    public long getNextTriggerAt() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.HOUR_OF_DAY) >= NOTIFICATION_HOUR) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        cal.set(Calendar.HOUR_OF_DAY, NOTIFICATION_HOUR);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    @Override
    public long getInterval() {
        return NOTIFICATION_DURATION;
    }

    @Override
    public void doWork() {
        NotificationHelper.sendTimeRemindNotification(App.getInstance().getContext());
    }
}

