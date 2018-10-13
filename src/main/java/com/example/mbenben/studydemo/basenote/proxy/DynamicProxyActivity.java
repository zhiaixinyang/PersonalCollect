package com.example.mbenben.studydemo.basenote.proxy;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.proxy.hook.ClipboardHook;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.lang.reflect.Proxy;

/**
 * Created by MDove on 2018/8/2.
 */

public class DynamicProxyActivity extends BaseActivity {
    public static final String TAG = "DynamicProxyActivity";
    private static final String EXTRA_ACTION = "extra_action";
    EditText mEt;
    ClipboardManager clipboard;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DynamicProxyActivity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_dynamic_proxy);
        mEt= (EditText) findViewById(R.id.et_content);
        hookService();
        fun();
    }

    private void hookService() {
        ClipboardHook.hookService(this);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void fun() {
        RentHouseProcessorImpl dpImpl = new RentHouseProcessorImpl();
        dpImpl.rentHouse("5000");
        Log.d(TAG, "我准备找中介去组个房子。");

        RentHouseProcessorHandler handler = new RentHouseProcessorHandler(dpImpl);
        IRentHouseProcessor proxy = (IRentHouseProcessor) Proxy.newProxyInstance(
                dpImpl.getClass().getClassLoader(),
                dpImpl.getClass().getInterfaces(),
                handler);

        String content = proxy.rentHouse("5000");
        Log.d(TAG, content);

        printClass(proxy.getClass());

    }

    private void printClass(Class clazzProxy) {
        LogUtils.d(TAG, "$Proxy0.class全名: " + clazzProxy.getName());
    }
}
