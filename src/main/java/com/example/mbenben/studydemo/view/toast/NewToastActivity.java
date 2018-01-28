package com.example.mbenben.studydemo.view.toast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.toast.view.LoadToast;

/**
 * Created by MBENBEN on 2017/2/13.
 */

public class NewToastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_toast);

        final String text = "Sending reply...";
        final LoadToast lt = new LoadToast(this).setText(text).setTranslationY(100).show();
        final ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View v = new View(this);
        v.setBackgroundColor(Color.RED);
        root.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt.show();
            }
        });
        findViewById(R.id.error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt.error();
            }
        });
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lt.success();
            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = new View(App.getInstance().getContext());
                v.setBackgroundColor(Color.rgb((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
                root.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
            }
        });
    }
}
