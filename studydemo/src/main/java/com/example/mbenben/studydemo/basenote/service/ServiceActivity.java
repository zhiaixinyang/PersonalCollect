package com.example.mbenben.studydemo.basenote.service;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

/**
 * 服务并非运行在后台线程
 */
public class ServiceActivity extends Activity {
    Intent intent1;
    Intent intent2;
    private boolean isBind = false;
    MyBindService service;
    ServiceConnection conn = new ServiceConnection() {

        //当服务跟启动源断开的时候 会自动回调
        @Override
        public void onServiceDisconnected(ComponentName name) {
            ToastUtils.show("当前线程：" + Thread.currentThread());
        }

        //当服务跟启动源连接的时候 会自动回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service = ((MyBindService.MyBinder) binder).getService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicce);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                //start方式启动服务
                intent1 = new Intent(ServiceActivity.this, MyStartService.class);
                startService(intent1);
                break;

            case R.id.stop:
                stopService(intent1);
                break;
            case R.id.play:
                if (isBind) {
                    service.play();
                }
                break;
            case R.id.pause:
                if (isBind) {
                    service.pause();
                }
                break;
            case R.id.pervious:
                if (isBind) {
                    service.pervious();
                }
                break;
            case R.id.next:
                if (isBind) {
                    service.next();
                }
                break;
            case R.id.bind:
                bind();
                break;
            case R.id.unbind:
                unbindService(conn);
                break;
            case R.id.messenger:
                Intent messenger=new Intent(this,MyMessagerService.class);
                bindService(messenger,messengerSC, Context.BIND_AUTO_CREATE);
                break;
            case R.id.say_hello:
                if (this.messengerFromService !=null){
                    try {
                        this.messengerFromService.send(Message.obtain(null,MyMessagerService.MESSAGE_FROM_SERVICE_TO_ACTIVITY,0,0,"我是来自Activity的囧雪!"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    //跨进程
    private Messenger messengerFromService;
    private ServiceConnection messengerSC=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //由此可以拿到在Service端持有Handler的Messenger，这样就可以发送消息了
            messengerFromService =new Messenger(service);
            //在连接成功弄时，通过来自Service的Messenger，把我们Activity的Messenger发送过去
            //这样Service就可以通过发送过去的Messenger给我们的Activity发送消息了
            Message message=new Message();
            message.what=6566;
            message.obj=messengerFromActivity;
            try {
                messengerFromService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messengerFromService =null;
        }
    };
    //接受Service发送过来的消息
    public static final int MESSAGE_FROM_ACTIVITY_TO_SERVICE=01;
    private Messenger messengerFromActivity=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==MESSAGE_FROM_ACTIVITY_TO_SERVICE){
                ToastUtils.showShort(msg.obj.toString());
            }
        }
    });

    private void bind() {
        if (!isBind) {
            /**
             * bind方式启动服务，两者有不同
             * 涉及到绑定：重复绑定将报错
             * 			要在正确的退出时机进行解绑服务
             */
            intent2 = new Intent(ServiceActivity.this, MyBindService.class);
            startService(intent2);
            bindService(intent2, conn, Service.BIND_AUTO_CREATE);
            isBind = true;
        }
    }

    @Override
    protected void onDestroy() {
        if (intent2!=null) {
            stopService(intent2);
        }
        if (conn!=null) {
            unbindService(conn);
        }
        if (messengerSC!=null){
            unbindService(messengerSC);
        }
        super.onDestroy();
    }

}
