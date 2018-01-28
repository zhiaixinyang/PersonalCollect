package com.example.mbenben.studydemo.base.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MBENBEN on 2017/8/30.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler handler;

    public void init(){
        //设置捕获
        Thread.currentThread().setUncaughtExceptionHandler(this);
        handler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //此处即可捕获全局的异常，在此处进行传递至服务器的操作
        getExceptionMess(e);
        //系统处理捕获到的异常（不设置可以屏蔽掉闪退的提示，但是没有日志的（因为此条语句是系统处理异常的方式））
        handler.uncaughtException(t,e);
    }

    //简单获取应用信息
    private Map<String,String> getSimpleInfo(Context context){
        Map<String,String> data=new HashMap<>();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), packageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        data.put("versionName",packageInfo.versionName);
        data.put("versionCode",packageInfo.versionCode+"");
        data.put("MODEL", Build.MODEL+"");
        data.put("SDK_INT", Build.VERSION.SDK_INT+"");
        data.put("PRODUCT", Build.PRODUCT+"");
        data.put("MOBILE_INFO", getMobileInfo());
        return data;
    }

    //通过反射拿到手机的全部信息
    public String getMobileInfo() {
        StringBuffer sb=new StringBuffer();
        Field[] fields = Build.class.getFields();
        try {
            for (Field field:fields){
                field.setAccessible(true);
                String name=field.getName();
                String value=field.get(null).toString();
                sb.append(name).append(" = ").append(value);
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //读取异常信息
    public String getExceptionMess(Throwable throwable){
        StringWriter sw=new StringWriter();
        PrintWriter pw=new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }
}
