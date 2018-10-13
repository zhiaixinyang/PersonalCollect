package com.example.mbenben.studydemo.basenote.proxy.hook;

import android.os.IBinder;

import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by MDove on 2018/8/19.
 */

public class ServiceHook  implements InvocationHandler {
    private static final String TAG = "ServiceHook";

    private IBinder mBase;
    private Class<?> mStub;
    private Class<?> mInterface;
    private InvocationHandler mInvocationHandler;

    public ServiceHook(IBinder mBase, String iInterfaceName, boolean isStub, InvocationHandler InvocationHandler) {
        this.mBase = mBase;
        this.mInvocationHandler = InvocationHandler;

        try {
            this.mInterface = Class.forName(iInterfaceName);
            this.mStub = Class.forName(String.format("%s%s", iInterfaceName, isStub ? "$Stub" : ""));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[] { mInterface },
                    new HookHandler(mBase, mStub, mInvocationHandler));
        }else{
            throw new IllegalArgumentException("系统queryLocalInterface方法已失效，请自行进行版本兼容。");
        }
    }

    private static class HookHandler implements InvocationHandler {
        private Object mBase;
        private InvocationHandler mInvocationHandler;

        public HookHandler(IBinder base, Class<?> stubClass,
                           InvocationHandler InvocationHandler) {
            mInvocationHandler = InvocationHandler;

            try {
                Method asInterface = stubClass.getDeclaredMethod("asInterface", IBinder.class);
                this.mBase = asInterface.invoke(null, base);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (mInvocationHandler != null) {
                return mInvocationHandler.invoke(mBase, method, args);
            }
            return method.invoke(mBase, args);
        }
    }
}
