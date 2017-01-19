package com.example.mbenben.studydemo.annotation;

import android.app.Activity;

import java.lang.reflect.Field;

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
    }
}
