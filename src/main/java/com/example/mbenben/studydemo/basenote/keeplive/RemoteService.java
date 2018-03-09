package com.example.mbenben.studydemo.basenote.keeplive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.basenote.aidltest.IKeepGuardService;

/**
 * Created by MDove on 2018/3/9.
 */

public class RemoteService extends Service {
    private ServiceConnection connection;

    public static void start(Context context) {
        Intent start = new Intent(context, LocalService.class);
        context.startActivity(start);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connection = new LocalConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, LocalService.class), connection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private class MyBinder extends IKeepGuardService.Stub {
        @Override
        public void serivceIsDead() throws RemoteException {
            NeedKeepLiveService.start(RemoteService.this);
        }
    }

    public class LocalConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LocalService.start(RemoteService.this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        LocalService.start(RemoteService.this);
    }
}
