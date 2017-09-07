package com.example.mbenben.studydemo.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MBENBEN on 2017/1/7.
 */

public class MyBufferKnife {
    public static void bind(Activity activity){
        getAnnotationInfos(activity);
    }
    private static void getAnnotationInfos(Activity activity) {
        Class clazz = activity.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            MyBindView myBindView = field.getAnnotation(MyBindView.class);
            if (myBindView != null) {
                int id = myBindView.value();
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(id));
                } catch (IllegalAccessException e) {
                }
            }
        }

        Method[] methods=clazz.getDeclaredMethods();
        for(Method method:methods){
            MyOnClick myOnClick=method.getAnnotation(MyOnClick.class);
            if (myOnClick!=null){
                int[] values=myOnClick.value();
                if (values.length>0){
                    for(int value:values){
                        View view=activity.findViewById(value);
                        view.setOnClickListener(new DeclaredOnClickListener(method, activity));
                    }
                }
            }
        }
    }

    //如果方法内部有异常，不执行点击事件，且不报异常错误
    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method method;
        private Object handlerType;

        public DeclaredOnClickListener(Method method, Object handlerType) {
            this.method = method;
            this.handlerType = handlerType;
        }

        @Override
        public void onClick(View v) {
            method.setAccessible(true);
            try {
                method.invoke(handlerType, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    method.invoke(handlerType);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
