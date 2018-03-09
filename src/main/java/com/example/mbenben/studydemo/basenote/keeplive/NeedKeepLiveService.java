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

public class NeedKeepLiveService extends Service {

    private ServiceConnection remoteConnection;
    private ServiceConnection localConnection;
    private IKeepGuardService mRemoteIBinder;
    private IKeepGuardService mLocalIBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        remoteConnection = new RemoteConnection();
        localConnection = new LocalConnection();
    }

    public static void start(Context context) {
        Intent start = new Intent(context, NeedKeepLiveService.class);
        context.startService(start);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        bindService(new Intent(this, LocalService.class), localConnection, Context.BIND_IMPORTANT);
        bindService(new Intent(this, RemoteService.class), remoteConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private class RemoteConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteIBinder = IKeepGuardService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteIBinder = null;
        }
    }

    private class LocalConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocalIBinder = IKeepGuardService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocalIBinder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRemoteIBinder != null) {
            try {
                mRemoteIBinder.serivceIsDead();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (mLocalIBinder != null) {
            try {
                mLocalIBinder.serivceIsDead();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

