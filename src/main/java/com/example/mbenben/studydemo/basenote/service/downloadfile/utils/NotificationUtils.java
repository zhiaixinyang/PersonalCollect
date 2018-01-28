package com.example.mbenben.studydemo.basenote.service.downloadfile.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.mbenben.studydemo.MainActivity;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.basenote.service.downloadfile.ui.DownloadFileActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * create by luoxiaoke on 2016/4/29 14:37.
 * use for 通知栏工具类
 */
public class NotificationUtils {

    private Context mContext;

    private NotificationManager mNotificationManager;
    private Map<Integer,Notification> mNotifications;

    public NotificationUtils(Context context) {
        this.mContext = context;
        //获取通知系统服务
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //创建通知集合
        mNotifications = new HashMap<>();
    }

    /**
     * 显示通知
     */
    public void showNotification(Class c){
        //判断通知是否已经显示
        if (mNotifications.containsKey(c.getClass())){
            //创建通知对象
            Notification notification = new Notification();
            //设置滚动文字
            notification.tickerText = "开始下载";
            //通知时间
            notification.when = System.currentTimeMillis();
            //图片
            notification.icon = R.mipmap.ic_launcher;
            //通知特性
            notification.flags = Notification.FLAG_AUTO_CANCEL;//点击通知后消失
            //点击通知栏的操作
            Intent mIntent = new Intent(mContext, DownloadFileActivity.class);
            PendingIntent mPIntent = PendingIntent.getActivity(mContext,0,mIntent,0);
            notification.contentIntent = mPIntent;
            //创建RemoteVeiws对象
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification);
            //设置开始按钮操作
            Intent intentStart = new Intent(mContext,c);//修改
//            remoteViews.setOnClickPendingIntent(R.id.start,);

        }
    }
}
