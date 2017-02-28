package com.example.mbenben.studydemo.basenote.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.mbenben.studydemo.utils.ToastUtil;

public class MyStartService extends Service {
	@Override
	public void onCreate() {
		//此方法只会在第一次触发一次。除非destroy之后在start
		Log.d("aaa", "Service--onCreate()");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//多次点击start只会重新调用这个方法
		Log.d("aaa", "Service--onStartCommand()");
		ToastUtil.show("当前线程："+Thread.currentThread());

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		//stopService之后被调用
		Log.d("aaa", "Service--onDestroy()");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 此方法只有在bind的模式启动才会调用
		Log.d("aaa", "Service--onBind()");
		return null;
	}

}
