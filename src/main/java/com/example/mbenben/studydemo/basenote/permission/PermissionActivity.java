package com.example.mbenben.studydemo.basenote.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PermissionActivity extends AppCompatActivity {
    @BindView(R.id.btn_call)
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        ButterKnife.bind(this);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PermissionActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PermissionActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},111);
                }else{
                    call();
                }
            }
        });
    }

    private void call() {
        //如果不try需要强制在此写权限申明方法
        try {
            Intent toCall = new Intent(Intent.ACTION_CALL);
            toCall.setData(Uri.parse("tel:10086"));
            startActivity(toCall);
        }catch (SecurityException e){

        }
    }

}
