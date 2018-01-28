package com.example.mbenben.studydemo.basenote.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BC1 extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String s = intent.getStringExtra("msg");
		Log.d("aaaa","reveiver1收到消息："+s);
		//拦截广播，优先级其后的接收器收不到广播；普通广播中使用此方法无效
		abortBroadcast();
//		Bundle bundle = 	new Bundle();
//		bundle.putString("test", "广播处理的数据");
//		setResultExtras(bundle);
	}

}
