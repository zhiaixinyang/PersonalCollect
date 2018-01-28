package com.example.mbenben.studydemo.basenote.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.example.mbenben.studydemo.utils.ToastUtils;


/**
 * Created by MBENBEN on 2017/8/5.
 */

public class MyMessagerService extends Service {
    public static final int MESSAGE_FROM_SERVICE_TO_ACTIVITY =1;
    private Messenger messengerFromActivity;
    private class MessageHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_FROM_SERVICE_TO_ACTIVITY:
                    ToastUtils.showShort(msg.obj+"");
                    break;
                case 6566:
                    messengerFromActivity= (Messenger) msg.obj;
                    if (messengerFromActivity!=null){
                        Message message=new Message();
                        message.what=MESSAGE_FROM_SERVICE_TO_ACTIVITY;
                        message.obj="我是来自Service的龙妈。";
                        try {
                            messengerFromActivity.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    private Messenger messenger;
    @Override
    public void onCreate() {
        super.onCreate();
        messenger=new Messenger(new MessageHandler());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
