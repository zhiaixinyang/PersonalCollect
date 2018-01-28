package com.example.mbenben.studydemo.basenote.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.mbenben.studydemo.utils.ToastUtils;

public class MyBindService extends Service{
	private final IBinder binder = new MyBinder();
	@Override
	public void onCreate() {
		super.onCreate();
	}
	public class MyBinder extends Binder{
		public MyBindService getService(){
			return MyBindService.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	@Override
	public void unbindService(ServiceConnection conn) {
		//调用unbindService方法之后被调用
		super.unbindService(conn);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public void play(){
		ToastUtils.showShort("我运行在BindService中：播放。当前线程-"+Thread.currentThread());
	}
	public void pause(){
		ToastUtils.showShort("我运行在BindService中：暂停。当前线程-"+Thread.currentThread());

	}
	public void pervious(){
		ToastUtils.showShort("我运行在BindService中：上一首。当前线程-"+Thread.currentThread());

	}
	public void next(){
		ToastUtils.showShort("我运行在BindService中：下一首。当前线程-"+Thread.currentThread());
	}
}
