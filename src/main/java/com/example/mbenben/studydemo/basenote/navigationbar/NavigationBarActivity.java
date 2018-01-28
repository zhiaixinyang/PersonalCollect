package com.example.mbenben.studydemo.basenote.navigationbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/8/4.
 */

public class NavigationBarActivity extends AppCompatActivity {
    @BindView(R.id.mainLayout)
    LinearLayout main;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        ButterKnife.bind(this);

        DefaultNavigationBar defaultNavigationBar=new DefaultNavigationBar
                .Builder(this, main)
                .setLeftText("傻逼2")
                .setTitleText("傻吊2")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .builder();
    }
}
