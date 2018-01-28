package com.example.mbenben.studydemo.anim;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2017/9/19.
 */

public class ColorAnimActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_anim);

        ObjectAnimator animator = ObjectAnimator.ofInt(findViewById(R.id.view_1), "color", 0xffff0000, 0xff00ff00);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
