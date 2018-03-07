package com.example.mbenben.studydemo.view.xiaomi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.jbox.JBoxDemoActivity;

/**
 * Created by MDove on 18/1/23.
 */

public class XiaoMiActivity extends BaseActivity {

    private static final String ACTION_EXTRA = "action_extra";
    private String mTitle;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, XiaoMiActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = TextUtils.isEmpty(mTitle) ? getIntent().getStringExtra(ACTION_EXTRA) : mTitle;
        setTitle(mTitle);

        setContentView(R.layout.activity_xiaomi);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
