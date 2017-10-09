package com.example.mbenben.studydemo.basenote.broadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.mbenben.studydemo.R;

public class BroadcastReceiverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcastreceiver);
		//动态注册
//		IntentFilter intentfilter = new IntentFilter("BC_One");
//		BC2 bc2 = 	new BC2();
//		registerReceiver(bc2, intentfilter);
	}
	public void doClick(View v){
		switch (v.getId()) {
			case R.id.send1://发送一条普通广播
				Intent intent = new Intent();
				intent.putExtra("msg", "这是一条普通广播");
				intent.setAction("BC_One");
				sendBroadcast(intent);
				break;
			case R.id.send2://发送一条有序广播，可被接受者拦截
				Intent intent2 = new Intent();
				intent2.putExtra("msg", "这是一条有序广播");
				intent2.setAction("BC_One");
				//有序广播可以被中断
				sendOrderedBroadcast(intent2, null);
				break;
			case R.id.send3://（即先发送广播，再注册...用处...）
				Intent intent3 = new Intent();
				intent3.putExtra("msg", "这是一条有序广播");
				intent3.setAction("BC_Three");
				sendStickyBroadcast(intent3);
				IntentFilter intentfilter = new IntentFilter("BC_Three");
				BC3 bc3 = 	new BC3();
				registerReceiver(bc3, intentfilter);
				break;
			case R.id.send4:
				Intent sendNotOpen=new Intent();
				sendNotOpen.setAction("TEST_NOT_OPEN");
				sendNotOpen.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				sendBroadcast(sendNotOpen);
				break;
			default:
				break;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		unregisterReceiver(receiver);
	}
}
