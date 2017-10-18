package com.example.mbenben.studydemo.basenote.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.mbenben.studydemo.MainActivity;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.anim.camera.CameraAnimActivity;
import com.example.mbenben.studydemo.view.verifycode.VerifyCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/10/17.
 */

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    //将Manifest里管理的activity作为back的下一个Activity
    @OnClick(R.id.btn_1)
    public void onViewClicked() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setAutoCancel(true);
        // 创建意图
        Intent resultIntent = new Intent(this, BackStackActivity.class);

        // 这样通过通知启动的Activity，返回键就直接退出应用
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // 需要同时再Manifest中添加回退Activity信息
        stackBuilder.addParentStack(BackStackActivity.class);
        // 将启动活动的Intent添加到堆栈的顶部
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Id 用于稍后更新通知
        mNotificationManager.notify(6566, mBuilder.build());
        //删除通知
//        mNotificationManager.cancel(6566);
    }

    //启动一个全新的栈，不清理之前的Activity
    @OnClick(R.id.btn_2)
    public void onViewClicked2() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent to = new Intent(this, NoBackStackActivity.class);
        to.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("tickerText:哈？")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(PendingIntent.getActivity(this,
                        112,
                        to,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);

        RemoteViews normalRemoteViews = initNormalRemoteView();
        builder.setContent(normalRemoteViews);
        mNotificationManager.notify(6567, builder.build());
    }

    protected RemoteViews initNormalRemoteView() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_notification_content);
        remoteViews.setImageViewBitmap(R.id.cover_view, BitmapFactory.decodeResource(getResources(), R.mipmap
                .ic_launcher));
        remoteViews.setTextViewText(R.id.title_view, "呵呵呵");
        remoteViews.setTextViewText(R.id.content_view, "哦哦哦");
        remoteViews.setTextViewText(R.id.action_text, "哈哈哈");
        return remoteViews;
    }

}
