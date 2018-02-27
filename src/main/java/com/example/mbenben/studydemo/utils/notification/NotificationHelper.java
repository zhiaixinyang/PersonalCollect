package com.example.mbenben.studydemo.utils.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import com.example.mbenben.studydemo.R;

import java.util.Random;

/**
 * @author Created by MDove on 2018/2/27.
 */

public class NotificationHelper {
    public static final int TIME_REMIND_NOTIFICATION_ID = 111;
    public static final int CONNECT_ON_NOTIFICATION_ID = 112;

    //适配8.0需要的内容
    public static final String CHANNEL_ID_CONNECT = "channel_id_connect";
    public static final String CHANNEL_NAME_CONNECT = "channel_name_connect";
    public static final String CHANNEL_ID_TIME_REMIND = "channel_id_time_remind";
    public static final String CHANNEL_NAME_TIME_REMIND = "channel_name_time_remind";

    public static void sendTimeRemindNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //8.0+先创建消息渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_TIME_REMIND,
//                    CHANNEL_NAME_TIME_REMIND, NotificationManager.IMPORTANCE_DEFAULT);
//            if (manager != null) {
//                manager.createNotificationChannel(mChannel);
//            }
//        }
        int notificationId = TIME_REMIND_NOTIFICATION_ID;

        int contentTitleResId, contentTextResId;
        int randomNum = new Random().nextInt(8) + 1;
        //统计发送量
        contentTitleResId = context.getResources().getIdentifier("notification_time_remind_content_title_" + randomNum, "string", context.getPackageName());
        contentTextResId = context.getResources().getIdentifier("notification_time_remind_content_text_" + randomNum, "string", context.getPackageName());

        String bigText = context.getString(contentTextResId);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_black_24dp);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_TIME_REMIND);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setContentIntent(getTimeRemindContentIntent(context, randomNum, notificationId))
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        builder.setContentTitle(context.getString(contentTitleResId));
        builder.setContentText(bigText);

        if (manager != null) {
            manager.notify(notificationId, builder.build());
        }
    }

    private static PendingIntent getTimeRemindContentIntent(Context context, int notificationType, int notificationId) {
        return NotificationReceiver.createTimeRemindClickIntent(context, notificationType, notificationId);
    }

    private static PendingIntent getPermanentContentIntent(Context context, int notificationId) {
        return NotificationReceiver.createPermanentClickIntent(context, notificationId);
    }

    public static void sendConnectOnNotification(Context context) {
        sendConnectOnNotification(context, true);
    }

    public static void sendConnectOnNotification(Context context, boolean isOn) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //8.0+先创建消息渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_CONNECT,
//                    CHANNEL_NAME_CONNECT, NotificationManager.IMPORTANCE_DEFAULT);
//            if (manager != null) {
//                manager.createNotificationChannel(mChannel);
//            }
//        }
        int notificationId = CONNECT_ON_NOTIFICATION_ID;
        Bitmap largeIcon;
        if (isOn) {
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_black_24dp);
        } else {
            largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_black_24dp);
        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_CONNECT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setLargeIcon(largeIcon)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(getPermanentContentIntent(context, notificationId))
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        if (isOn) {
            builder.setContentTitle("这是一条定时Notification");
            builder.setContentText("哈哈哈哈哈");
        } else {
            builder.setContentTitle("这是一条定时Notification");
            builder.setContentText("呵呵呵呵呵");
        }

        if (manager != null) {
            manager.notify(notificationId, builder.build());
        }
    }

    /**
     * 取消定时通知栏消息
     */
    public static void cancelTimeRemindNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(TIME_REMIND_NOTIFICATION_ID);
        }
    }

    /**
     * 取消连接成功通知栏消息
     */
    public static void cancelConnectOnNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(CONNECT_ON_NOTIFICATION_ID);
        }
    }

    public static Notification createServiceForegroundNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //8.0+先创建消息渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_CONNECT,
//                    CHANNEL_NAME_CONNECT, NotificationManager.IMPORTANCE_DEFAULT);
//            if (manager != null) {
//                manager.createNotificationChannel(mChannel);
//            }
//        }
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_favorite_black_24dp);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_CONNECT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setLargeIcon(largeIcon)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(getPermanentContentIntent(context, CONNECT_ON_NOTIFICATION_ID))
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        builder.setContentTitle("我就是一条普通Notification");
        builder.setContentText("没错，很是普通。");
        return builder.build();
    }
}
