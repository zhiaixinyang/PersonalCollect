package com.example.mbenben.studydemo.utils.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author MDove on 2018/2/27.
 */
public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ACTION_CLICK_TIME_REMIND = BuildConfig.APPLICATION_ID + ".action.CLICK.TIME.REMIND";
    public static final String NOTIFICATION_ACTION_CLICK_PERMANENT = BuildConfig.APPLICATION_ID + ".action.CLICK.PERMANENT";

    public static final String KEY_TIME_REMIND_NOTIFICATION_TYPE = "key_time_remind_notification_type";

    public static final int TIME_REMIND_NOTIFICATION_1 = 1;
    public static final int TIME_REMIND_NOTIFICATION_2 = 2;

    @IntDef(value = {TIME_REMIND_NOTIFICATION_1, TIME_REMIND_NOTIFICATION_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeRemindNotificationType {
    }


    public static PendingIntent createTimeRemindClickIntent(@NonNull Context context, @TimeRemindNotificationType int notificationType, int requestCode) {
        Intent contentIntent = new Intent(NOTIFICATION_ACTION_CLICK_TIME_REMIND);
        contentIntent.setComponent(new ComponentName(context, NotificationReceiver.class));
        contentIntent.setPackage(context.getPackageName());
        contentIntent.putExtra(KEY_TIME_REMIND_NOTIFICATION_TYPE, notificationType);
        return PendingIntent.getBroadcast(context, requestCode, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent createPermanentClickIntent(@NonNull Context context, int requestCode) {
        Intent contentIntent = new Intent(NOTIFICATION_ACTION_CLICK_PERMANENT);
        contentIntent.setComponent(new ComponentName(context, NotificationReceiver.class));
        contentIntent.setPackage(context.getPackageName());
        return PendingIntent.getBroadcast(context, requestCode, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(NOTIFICATION_ACTION_CLICK_TIME_REMIND)) {
//            SplashActivity.Companion.startNotificationClicked(context);
        }else if (action.equals(NOTIFICATION_ACTION_CLICK_PERMANENT)){
//            SplashActivity.Companion.startNotificationClicked(context);
        }
        collapseStatusBar(context);
    }

    public static void collapseStatusBar(Context context) {
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }
}
