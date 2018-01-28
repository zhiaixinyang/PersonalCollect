package com.example.mbenben.studydemo.layout.intercepttouchevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.intercepttouchevent.view.ALinearLayout;
import com.example.mbenben.studydemo.layout.intercepttouchevent.view.BLinearLayout;
import com.example.mbenben.studydemo.layout.intercepttouchevent.view.CButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/14.
 */

public class InterceptActivity extends AppCompatActivity {

//    @BindView(R.id.a) ALinearLayout a;
//    @BindView(R.id.c) CButton c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept);



        ButterKnife.bind(this);
//        a.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i("aaa", "ALinearLayout-onTouch-ACTION_DOWN...");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i("aaa", "ALinearLayout-onTouch-ACTION_UP...");
//                        break;
//                    default:break;
//
//                }
//                return false;
//            }
//        });
//        c.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("aaaa","CButton:onClick");
//            }
//        });
    }
}
