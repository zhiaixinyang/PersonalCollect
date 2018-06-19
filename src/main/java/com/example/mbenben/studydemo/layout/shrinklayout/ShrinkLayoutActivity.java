package com.example.mbenben.studydemo.layout.shrinklayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

/**
 * Created by MDove on 2018/6/19.
 */

public class ShrinkLayoutActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, ShrinkLayoutActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_shrink_layout);
        final EggacheDisplayView edv = (EggacheDisplayView) findViewById(R.id.edv);
        edv.postDelayed(new Runnable() {
            @Override
            public void run() {
                edv.collapse();
            }
        }, 2000);
    }
}
