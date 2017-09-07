package com.example.mbenben.studytest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.mbenben.studytest.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MBENBEN on 2017/4/13.
 */

public class TwoActivity extends Activity {
    private TextView btnRegister;
    private TextView one,two,three;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_two);

        btnRegister= (TextView) findViewById(R.id.btn_register);
        one= (TextView) findViewById(R.id.one);
        two= (TextView) findViewById(R.id.two);
        three= (TextView) findViewById(R.id.three);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toThree=new Intent(TwoActivity.this,ThreeActivity.class);
                startActivity(toThree);
                finish();
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator oneAnime=ObjectAnimator.ofFloat(one, "rotationX", 0.0f, 360.0f);
                        oneAnime.setDuration(2000);
                        oneAnime.start();

                        ValueAnimator twoAnime=ObjectAnimator.ofFloat(two, "alpha", 0.0f, 1.0f,0.0f,1.0f);
                        twoAnime.setDuration(2000);
                        twoAnime.start();

                        ValueAnimator threeAnime=ObjectAnimator.ofFloat(three, "rotationX", 0.0f, 360.0f);
                        threeAnime.setDuration(2000);
                        threeAnime.start();
                    }
                });

            }
        },100);
    }
}
