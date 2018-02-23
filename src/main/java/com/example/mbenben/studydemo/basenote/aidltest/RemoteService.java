package com.example.mbenben.studydemo.basenote.aidltest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by MDove on 2018/2/23.
 */

public class RemoteService extends Service {
    private static final String TAG="RemoteService";
    public static final String ACTION_HANDLE_ONE = "action_handle_one";
    public static final String ACTION_HANDLE_TWO = "action_handle_two";
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(),ACTION_HANDLE_ONE)){
                ToastUtils.showShort("响应action_handle_ONE广播");
            }else if (TextUtils.equals(intent.getAction(),ACTION_HANDLE_TWO)){
                ToastUtils.showShort("响应action_handle_TWO广播");
            }
        }
    };
    private RemoteBinder mBinder=new RemoteBinder(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //先于onBind执行，只会调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        registerBroadcast();
    }

    public void sayData(AidlData aidlData){
        LogUtils.d(TAG,aidlData.mData);
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_HANDLE_ONE);
        filter.addAction(ACTION_HANDLE_TWO);
        registerReceiver(mReceiver, filter);
    }

    private class RemoteBinder extends IBindService.Stub{
        private WeakReference<RemoteService> mReference;

        public RemoteBinder(RemoteService service){
            mReference=new WeakReference<RemoteService>(service);
        }

        @Override
        public void sendData(AidlData data) throws RemoteException {
            mReference.get().sayData(data);
        }
    }
}
