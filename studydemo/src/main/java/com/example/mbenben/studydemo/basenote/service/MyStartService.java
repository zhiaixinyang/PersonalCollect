package com.example.mbenben.studydemo.basenote.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyStartService extends Service {
	@Override
	public void onCreate() {
		//此方法只会在第一次触发一次。除非destroy之后在start
		Log.i("info", "Service--onCreate()");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//多次点击start只会重新调用这个方法
		Log.i("info", "Service--onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		//stopService之后被调用
		Log.i("info", "Service--onDestroy()");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 此方法只有在bind的模式启动才会调用
		Log.i("info", "Service--onBind()");
		return null;
	}

}
