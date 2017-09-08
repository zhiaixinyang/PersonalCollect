package com.example.mbenben.studydemo.basenote.javanode;

import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by MBENBEN on 2017/8/30.
 */

public class JavaCollection {
    //适用于Java1.5以上版本
    private void mapForeach(Map<String,String> maps){
        for (Map.Entry<String,String> map:maps.entrySet()){
            String key=map.getKey();
            String value=map.getValue();
            Log.d("mylog",key+" = "+value);
        }


        //单一遍历map中的键
        for (String key : maps.keySet()) {
            Log.d("mylog","key : "+key);
        }

        //单一遍历map中的值
        for (String value : maps.values()) {
            Log.d("mylog","value : "+value);
        }
    }
}
