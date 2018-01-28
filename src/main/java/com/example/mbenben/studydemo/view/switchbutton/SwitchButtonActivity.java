package com.example.mbenben.studydemo.view.switchbutton;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.switchbutton.view.IconSwitch;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/6/13.
 */

public class SwitchButtonActivity extends AppCompatActivity implements IconSwitch.CheckedChangeListener {

    @BindView(R.id.icon_switch) IconSwitch iconSwitch;
    @BindView(R.id.text) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_button);

        ButterKnife.bind(this);

        iconSwitch.setCheckedChangeListener(this);
    }



    @Override
    public void onCheckChanged(IconSwitch.Checked current) {
        if (current== IconSwitch.Checked.LEFT){
            textView.setText("此时为SwitchButton-左边按钮");
        }else{
            textView.setText("此时为SwitchButton-右边按钮");
        }
    }
}

