package com.example.mbenben.studydemo.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2017/12/31.
 */

public class AnimatorActivity extends AppCompatActivity {
    private static final LinearInterpolator DEFAULT_INTERPOLATOR = new LinearInterpolator();
    private static final long DURATION = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
    }

    public void viewCompat(View view) {
        ViewCompat.animate(view)
                .scaleY(0f)
                .scaleX(0f)
                .alpha(0f)
                .setDuration(DURATION)
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .start();
    }
}
