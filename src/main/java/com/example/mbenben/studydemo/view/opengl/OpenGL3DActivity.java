package com.example.mbenben.studydemo.view.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.opengl.show3d.view.GL3DRenderer;
import com.example.mbenben.studydemo.view.opengl.show3d.view.GL3DView;

/**
 * Created by MDove on 18/1/26.
 */

public class OpenGL3DActivity extends AppCompatActivity {
    private boolean supportsEs2;
    private GLSurfaceView glView;
    private float rotateDegreen = 0;
    private GL3DRenderer gl3DRenderer;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSupported();
        setContentView(R.layout.activity_opengl_3d);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        if (supportsEs2) {
            glView = (GL3DView) findViewById(R.id.glView);
            gl3DRenderer = new GL3DRenderer(this);
            glView.setRenderer(gl3DRenderer);

        } else {
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
        }
        seekBar.setMax(100);

        seekBar.setProgress(80);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gl3DRenderer.setScale(1f * progress / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void rotate(float degree) {
        gl3DRenderer.rotate(degree);
        glView.invalidate();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rotate(rotateDegreen);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (glView != null) {
            glView.onResume();

            //不断改变rotateDegreen值，实现旋转
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            sleep(100);

                            rotateDegreen += 5;
                            handler.sendEmptyMessage(0x001);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start();
        }
    }

    private void checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        supportsEs2 = supportsEs2 || isEmulator;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glView != null) {
            glView.onPause();
        }
    }
}