package com.example.mbenben.studydemo.view.bezier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/12.
 *
 *
 * https://github.com/AlanCheen/PeriscopeLayout
 */

public class GiftBezierActivity extends AppCompatActivity{
    @BindView(R.id.view_gift) GiftBezierView gift;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beziergift);
        ButterKnife.bind(this);
        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift.addHeart();
            }
        });
    }
}
