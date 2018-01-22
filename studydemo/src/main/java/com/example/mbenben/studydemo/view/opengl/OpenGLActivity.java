package com.example.mbenben.studydemo.view.opengl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.opengl.flag.FlagActivity;
import com.example.mbenben.studydemo.view.opengl.google.OpenGLGoogleActivity;

/**
 * Created by MDove on 2018/1/16.
 */

public class OpenGLActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        findViewById(R.id.btn_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpenGLActivity.this, OpenGLGoogleActivity.class));
            }
        });
//        findViewById(R.id.btn_flag).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(OpenGLActivity.this, FlagActivity.class));
//            }
//        });
    }
}
