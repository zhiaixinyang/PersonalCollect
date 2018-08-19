package com.example.mbenben.studydemo.basenote.proxy;

import android.util.Log;

/**
 * Created by MDove on 2018/8/2.
 */

public class RentHouseProcessorImpl implements IRentHouseProcessor {
    @Override
    public String rentHouse(String price) {
        Log.d(DynamicProxyActivity.TAG, "我还剩："+price+"元");
        String content = "我是租客，我找中介租了一个房子，我感觉被坑了";
        return content;
    }
}
