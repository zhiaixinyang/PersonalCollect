package com.example.mbenben.studydemo.basenote.proxy.hook;

import android.content.ClipData;
import android.content.Context;
import android.os.IBinder;

import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by MDove  on 2018/8/19.
 */

public class ClipboardHook {
    private static final String TAG = ClipboardHook.class.getSimpleName();

    public static void hookService(Context context) {
        IBinder clipboardService = ServiceManager.getService(Context.CLIPBOARD_SERVICE);
        String IClipboard = "android.content.IClipboard";

        if (clipboardService != null) {
            IBinder hookClipboardService =
                    (IBinder) Proxy.newProxyInstance(IBinder.class.getClassLoader(),
                            new Class[]{IBinder.class},
                            new ServiceHook(clipboardService, IClipboard, true, new ClipboardHookHandler()));
            ServiceManager.setService(Context.CLIPBOARD_SERVICE, hookClipboardService);
        } else {
            throw new NullPointerException("通过反射获取系统ClipboardService失败，请自行版本适配。");
        }
    }

    public static class ClipboardHookHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            int argsLength = args.length;
            //每次从本应用复制的文本，后面都加上分享的出处
            if ("setPrimaryClip".equals(methodName)) {
                if (argsLength >= 2 && args[0] instanceof ClipData) {
                    ClipData data = (ClipData) args[0];
                    String text = data.getItemAt(0).getText().toString();
                    text += "请注明分享地址，来自MDove";
                    args[0] = ClipData.newPlainText(data.getDescription().getLabel(), text);
                }
            }
            return method.invoke(proxy, args);
        }
    }
}
