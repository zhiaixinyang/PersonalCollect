package com.example.mbenben.studydemo.basenote.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

/**
 * Created by MDove on 2018/2/23.
 */

public class RemoteActivity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";
    private IBindService mBinder;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IBindService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RemoteActivity.class);
        intent.putExtra(EXTRA_ACTION,title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_remote_service);
        bindService(new Intent(this, RemoteService.class), mConn, Context.BIND_AUTO_CREATE);
    }

    public void broadcastOne(View view) {
        sendBroadcast(new Intent(RemoteService.ACTION_HANDLE_ONE));
    }

    public void broadcastTwo(View view) {
        sendBroadcast(new Intent(RemoteService.ACTION_HANDLE_TWO));
    }

    public void sendRemote(View view) {
        try {
            mBinder.sendData(new AidlData("Hi,我来自主进程"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
