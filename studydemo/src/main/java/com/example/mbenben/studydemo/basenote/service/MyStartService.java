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
		/**
		 * 其中参数flags默认情况下是0，对应的常量名为START_STICKY_COMPATIBILITY。
		 * startId是一个唯一的整型，用于表示此次Client执行startService(...)的请求请求标识，在多次startService(...)的情况下，呈现0,1,2....递增。
		 *
		 * 返回值：
		 * START_NOT_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，
		 * 即使系统内存足够可用，系统也不会尝试重新创建此Service。除非程序中Client明确再次调用startService(...)启动此Service。
		 *
		 * START_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，
		 * 当系统内存足够可用的情况下，系统将会尝试重新创建此Service，一旦创建成功后将回调onStartCommand(...)方法，但其中的Intent将是null，pendingintent除外。
		 *
		 * START_REDELIVER_INTENT：与START_STICKY唯一不同的是，回调onStartCommand(...)方法时，
		 * 其中的Intent将是非空，将是最后一次调用startService(...)中的intent。
		 */
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
