package com.example.mbenben.studytest.save;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

public class SaveActivity extends AppCompatActivity {
    @BindView(R.id.et_content)
    EditText etContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        ButterKnife.bind(this);

        Log.d("aaa","AActivity:onCreate");
    }

    public void btn(View view){
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        Intent intent=new Intent(this,BActivity.class);
//        startActivity(intent);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Haha");
        builder.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("aaa","AActivity:onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("aaa","AActivity:onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("aaa","AActivity:onResume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("aaa","AActivity:onRestart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("aaa","AActivity:onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaa","AActivity:onDestroy");
    }
}
