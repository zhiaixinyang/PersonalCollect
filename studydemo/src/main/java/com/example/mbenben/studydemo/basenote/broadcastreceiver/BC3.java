package com.example.mbenben.studydemo.basenote.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BC3 extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("aaaa","收到了异步广播!");
	}

}
