package com.example.mbenben.studydemo.basenote.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BC2 extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String s = intent.getStringExtra("msg");
		Log.d("aaaa","reveiver2收到消息："+s);
		//拦截广播，优先级其后的接收器收不到广播
//		abortBroadcast();
		Bundle bundle = getResultExtras(true);
		String s2 = bundle.getString("test");
		Log.d("aaaa","得到的处理结果是：" + s2);
	}

}
