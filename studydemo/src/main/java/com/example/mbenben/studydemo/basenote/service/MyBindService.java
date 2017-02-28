package com.example.mbenben.studydemo.basenote.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.mbenben.studydemo.utils.ToastUtil;

public class MyBindService extends Service{
	@Override
	public void onCreate() {
		Log.d("aaa", "BindService--onCreate()");
		super.onCreate();
	}
	public class MyBinder extends Binder{
		public MyBindService getService(){
			return MyBindService.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("aaa", "BindService--onBind()");
		return new MyBinder();
	}
	@Override
	public void unbindService(ServiceConnection conn) {
		//调用unbindService方法之后被调用
		Log.d("aaa", "BindService--unbindService()");
		super.unbindService(conn);
	}
	@Override
	public void onDestroy() {
		Log.d("aaa", "BindService--onDestroy()");
		super.onDestroy();
	}
	public void play(){
		ToastUtil.toastShort("我运行在BindService中：播放。当前线程-"+Thread.currentThread());
	}
	public void pause(){
		ToastUtil.toastShort("我运行在BindService中：暂停。当前线程-"+Thread.currentThread());

	}
	public void pervious(){
		ToastUtil.toastShort("我运行在BindService中：上一首。当前线程-"+Thread.currentThread());

	}
	public void next(){
		ToastUtil.toastShort("我运行在BindService中：下一首。当前线程-"+Thread.currentThread());

	}
}
