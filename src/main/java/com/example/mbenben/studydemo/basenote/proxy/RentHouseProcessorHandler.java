package com.example.mbenben.studydemo.basenote.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by MBENBEN on 2018/8/2.
 */

public class RentHouseProcessorHandler  implements InvocationHandler {
    private Object target;

    public RentHouseProcessorHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Log.d(DynamicProxyActivity.TAG, "-----------------------------");
        Log.d(DynamicProxyActivity.TAG, "我是中介，有人找我租房子了，看看他能出多少钱：" + args[0]);
        Log.d(DynamicProxyActivity.TAG, "我既然是中介，那我收他4000元的好处费，500块钱给你组个地下室，不过分吧？！！");
        Object result = method.invoke(target, new Object[]{"500"});
        Log.d(DynamicProxyActivity.TAG, "赚了一大笔钱，美滋滋~");
        return result;
    }
}
