package com.example.mbenben.studydemo.basenote.service;

import com.example.mbenben.studydemo.R;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;

/**
 * 系统内置服务：
 * 		MountService监听SD卡
 * 		ClipboardService剪切板服务
 * 		...
 * 		getSystemService(参数见官网)获取
 * 			例如：WINDOW_SERVICE ("window")...
 */
public class ServiceActivity extends Activity {
	Intent intent1;
	Intent intent2;
	MyBindService service;
	ServiceConnection conn = new ServiceConnection() {

		//当服务跟启动源断开的时候 会自动回调
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		//当服务跟启动源连接的时候 会自动回调
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = ((MyBindService.MyBinder)binder).getService();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicce);
	}
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.start:
			//start方式启动服务
			 intent1 = 	new Intent(ServiceActivity.this, MyStartService.class);
			startService(intent1);
			break;

		case R.id.stop:
			stopService(intent1);
			break;
		case R.id.play:
			service.play();
			break;
		case R.id.pause:
			service.pause();
			break;
		case R.id.pervious:
			service.pervious();
			break;
		case R.id.next:
			service.next();
			break;
		case R.id.bind:
			/**
			 * bind方式启动服务，两者有不同
			 * 涉及到绑定：重复绑定将报错
			 * 			要在正确的退出时机进行解绑服务
			 */
			intent2 = new Intent(ServiceActivity.this, MyBindService.class);
			startService(intent2);
			bindService(intent2, conn, Service.BIND_AUTO_CREATE);
			break;
		case R.id.unbind:
			unbindService(conn);
			break;
		}
	}
	@Override
	protected void onDestroy() {
		stopService(intent2);
		unbindService(conn);
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_menu, menu);
		return true;
	}

}
