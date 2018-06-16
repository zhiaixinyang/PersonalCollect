package com.example.mbenben.studydemo.basenote.tetsjni;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.base.NativeLibraryLoader;

/**
 * Created by MDove on 2018/6/16.
 */

public class JniActivity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";
    private TextView tv;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, JniActivity.class);
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
        setContentView(R.layout.activity_jni);

        tv=((TextView) findViewById(R.id.tv_content));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NativeLibraryLoader.loadLibrarySafely(JniActivity.this, "mdove");

                tv.setText(JniKit.myFirstC());
            }
        });
    }
}
