package com.example.mbenben.studytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by MBENBEN on 2017/4/13.
 */

public class ThreeActivity extends Activity {
    private TextView btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_three);
        btnBack= (TextView) findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain=new Intent(ThreeActivity.this,MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });
    }
}
