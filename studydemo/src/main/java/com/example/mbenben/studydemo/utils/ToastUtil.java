package com.example.mbenben.studydemo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;


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

    private static Handler handler=new Handler();
    public static void show(String msg){
        newToastShort(msg).show();
    }
    private  static Toast newToastShort(String msg){

        final View contentView = View.inflate(context, R.layout.view_dialog_toast, null);

        Animation enter = AnimationUtils.loadAnimation(contentView.getContext(),R.anim.toast_scale_enter);
        final Animation exit = AnimationUtils.loadAnimation(contentView.getContext(),R.anim.toast_scale_exit);
        enter.setDuration(500);
        exit.setDuration(500);

        contentView.startAnimation(enter);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(contentView != null){
                    contentView.startAnimation(exit);
                }
            }
        }, 1000);

        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }
}
