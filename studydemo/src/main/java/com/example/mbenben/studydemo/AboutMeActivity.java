package com.example.mbenben.studydemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/10/17.
 */

public class AboutMeActivity extends AppCompatActivity {
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.bottom1)
    TextView bottom1;
    @BindView(R.id.bottom)
    TextView bottom;

    public static void start(Context context) {
        Intent to = new Intent(context, AboutMeActivity.class);
        context.startActivity(to);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(bottom,"translationY",0f, -300f);
                bottomAnim.setInterpolator(new LinearInterpolator());
                bottomAnim.setDuration(2000);
                bottomAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        bottom.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        bottom.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                bottomAnim.start();
                final ObjectAnimator bottomAnim1 = ObjectAnimator.ofFloat(bottom1,"translationY",0f, -30f);
                bottomAnim1.setDuration(2000);
                bottomAnim1.start();

            }
        });
    }
}
