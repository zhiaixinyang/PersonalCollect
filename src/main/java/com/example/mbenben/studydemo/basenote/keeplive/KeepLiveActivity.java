package com.example.mbenben.studydemo.basenote.keeplive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.aidltest.RemoteActivity;

/**
 * Created by MDove on 2018/3/9.
 */

public class KeepLiveActivity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RemoteActivity.class);
        intent.putExtra(EXTRA_ACTION,title);
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
        setContentView(R.layout.activity_keep_live);
        NeedKeepLiveService.start(this);
    }
}
