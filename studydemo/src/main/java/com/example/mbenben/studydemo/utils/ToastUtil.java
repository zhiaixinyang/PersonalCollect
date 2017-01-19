package com.example.mbenben.studydemo.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.example.mbenben.studydemo.App;


/**
 * Created by MBENBEN on 2016/2/17.
 */
public class ToastUtil {
    private static Toast toast;
    private static  Context context= App.getInstance().getContext();
    //获得全局Context;
    public static void toastShort(String content){
        if(toast==null) {
            toast=Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }
    public static void toastLong(String content){
        if(toast==null) {
            toast=Toast.makeText(context, content, Toast.LENGTH_LONG);
        }else{
            toast.setText(content);
        }
        toast.show();
    }

    //非UI线程使用Toast
    public static void noUIToastShort(String content){
        Looper.prepare();
        if(toast==null) {
            toast=Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
        Looper.loop();
    }
    public static void noUIToastLong(String content){
        Looper.prepare();
        if(toast==null) {
            toast=Toast.makeText(context, content, Toast.LENGTH_LONG);
        }else{
            toast.setText(content);
        }
        toast.show();
        Looper.loop();
    }
}
