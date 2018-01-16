package com.example.mbenben.studydemo.view.surfaceview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.surfaceview.activity.MyCustomsActivity;
import com.example.mbenben.studydemo.view.surfaceview.activity.MyLoveActivity;

/**
 * Created by MDove on 2018/1/16.
 */

public class SurfaceViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        findViewById(R.id.btn_my_customs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SurfaceViewActivity.this, MyCustomsActivity.class));
            }
        });
        findViewById(R.id.btn_my_love).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SurfaceViewActivity.this, MyLoveActivity.class));
            }
        });
    }
}
