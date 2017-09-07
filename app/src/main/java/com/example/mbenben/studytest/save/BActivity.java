package com.example.mbenben.studytest.save;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mbenben.studytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/17.
 */

public class BActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Log.d("aaa","BActivity:onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("aaa","BActivity:onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("aaa","BActivity:onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("aaa","BActivity:onResume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("aaa","BActivity:onRestart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("aaa","BActivity:onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaa","BActivity:onDestroy");
    }
}
