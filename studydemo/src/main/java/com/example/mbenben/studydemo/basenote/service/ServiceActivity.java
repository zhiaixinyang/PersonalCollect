package com.example.mbenben.studydemo.basenote.service;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtil;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
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
            ToastUtil.show("当前线程：" + Thread.currentThread());
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
        }
    }

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
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

}
