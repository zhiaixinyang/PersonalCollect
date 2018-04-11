package com.example.mbenben.studydemo.basenote.reflect;

import com.example.mbenben.studydemo.utils.log.LogUtils;

import static com.example.mbenben.studydemo.basenote.reflect.ReflectActivity.TAG;

/**
 * Created by MDove on 2018/4/11.
 */

public class ReflectModel {
    private ReflectBean mReflectBean;
    private String mContent = "A";
    public int mNum = 1;
    public static int sNum = 666;

    public ReflectModel() {
        LogUtils.d(TAG, "ReflectModel()，无参构造方法执行");
    }

    public ReflectModel(String content) {
        LogUtils.d(TAG, "ReflectModel(String content)，一个String参数的构造方法执行");
        mContent = content;
    }

    private ReflectModel(String content, int num) {
        LogUtils.d(TAG, "ReflectModel(String content,int num)，俩个参数的私有构造方法执行");
        mContent = content;
        mNum = num;
    }

    public void fun() {
        LogUtils.d(TAG, "我就是一个方法，在本例子中。我是被反射生成的对象调用的->mContent:" + mContent + "-mNum:" + mNum);
    }

    private void printContent() {
        LogUtils.d(TAG, "我就是私有的打印方法->mContent:" + mContent + "-mNum:" + mNum);
    }

    private void setContent(String content) {
        LogUtils.d(TAG, "我就是一个带有一个String参数的私有方法->setContent:" + content);
        mContent = content;
    }

    public void printBean() {
        LogUtils.d(TAG, "我是ReflectBean的打印方法->mName:" + mReflectBean.getName());
    }

    public static void staticFun() {
        LogUtils.d(TAG, "我是静态方法staticFun");
    }
}
