package com.example.mbenben.studydemo.basenote.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

public class SystemServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) SystemServiceActivity.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.activity_system_service, null);
		setContentView(view);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.network:
			if (isNetWorkConnected(SystemServiceActivity.this)==true) {
				ToastUtils.showShort("网络已经打开");
			}else {
				ToastUtils.showShort("网络未连接");
			}
			break;
		case R.id.enableOrDisable_WIFI:
		WifiManager wifiManager =	(WifiManager) SystemServiceActivity.this.getSystemService(WIFI_SERVICE);
			if (wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(false);
				ToastUtils.showShort("WIFI已经关闭");
			}else {
				wifiManager.setWifiEnabled(true);
				ToastUtils.showShort("WIFI已经打开");
			}
			
		break;
		case R.id.getvoice:
			 AudioManager mAudioManager= (AudioManager) SystemServiceActivity.this.getSystemService(AUDIO_SERVICE);
			 int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			 int current = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
			ToastUtils.showShort( "系统的最大音量为："+max+",当前音量是："+current);
			 break;
		case R.id.getPackagename:
			ActivityManager activityManager = (ActivityManager) SystemServiceActivity.this.getSystemService(ACTIVITY_SERVICE);
			String packageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
			ToastUtils.showShort( "当前运行的Activity包名："+packageName);

			break;
		}
	}

	public boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo mNetWorkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetWorkInfo != null) {
				return mNetWorkInfo.isAvailable();
			}
		}
		return false;
	}
}
