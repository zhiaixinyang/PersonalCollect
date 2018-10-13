package com.example.mbenben.studydemo.view.qqspacead;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ScrollView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.util.DensityUtil;
import com.example.mbenben.studydemo.view.canvasapi.ScollerView;

/**
 * Created by MDove on 2018/10/13.
 * <p>
 * 原项目Blog：https://www.jianshu.com/p/85f8e189d76f
 */

public class QQSpaceAdActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";

    private BeautView beautView;
    private ScrollView scollerView;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, QQSpaceAdActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));

        setContentView(R.layout.activity_qqspace_ad);
        beautView = (BeautView) findViewById(R.id.ad);
        scollerView = (ScrollView) findViewById(R.id.sv);

        scollerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int mode = beautView.getMode();
                if (mode == BeautView.MODE_UP) {
                    int bottom = beautView.getBottom() - scrollY;
                    beautView.setBitmapBottom(bottom);
                    if (bottom < 0) {
                        beautView.setMode(BeautView.MODE_DOWN);
                    }
                }
                if (mode == BeautView.MODE_DOWN) {
                    int top = beautView.getTop() - scrollY;
                    beautView.setBitmapTop(top);
                    if (top > DensityUtil.getScreenHeight(QQSpaceAdActivity.this)) {
                        beautView.setMode(BeautView.MODE_UP);
                    }
                }
            }
        });
    }
}
