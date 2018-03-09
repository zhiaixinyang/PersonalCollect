package com.example.mbenben.studydemo.basenote.keeplive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.basenote.aidltest.IKeepGuardService;

/**
 * Created by MDove on 2018/3/9.
 */

public class LocalService extends Service {
    private ServiceConnection connection;

    public static void start(Context context) {
        Intent start = new Intent(context, RemoteService.class);
        context.startService(start);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connection = new RemoteConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, RemoteService.class), connection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    public class RemoteConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            RemoteService.start(LocalService.this);
        }
    }

    private class MyBinder extends IKeepGuardService.Stub {

        @Override
        public void serivceIsDead() throws RemoteException {
            NeedKeepLiveService.start(LocalService.this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        RemoteService.start(LocalService.this);
    }
}
