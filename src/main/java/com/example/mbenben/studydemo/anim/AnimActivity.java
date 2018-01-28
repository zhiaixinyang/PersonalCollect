package com.example.mbenben.studydemo.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.tempview.TempControlView;

/**
 * Created by MBENBEN on 2016/12/8.
 */

public class AnimActivity extends AppCompatActivity{
    private TextView test;
    private TempControlView btnTemp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        btnTemp= (TempControlView) findViewById(R.id.btn_temp);
        btnTemp.setTemp(16,40,25);
        btnTemp.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
            @Override
            public void change(int temp) {
                Toast.makeText(AnimActivity.this, temp + "°", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
