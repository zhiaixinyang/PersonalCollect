package com.example.mbenben.studydemo.view.opengl.custom;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 18/1/23.
 */

public class CustomOpenGLActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView surfaceView = new GLSurfaceView(this);
        surfaceView.setRenderer(new DurianGLRender(this));
        setContentView(surfaceView);
    }
}
