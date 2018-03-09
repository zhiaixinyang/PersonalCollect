package com.example.mbenben.studydemo.basenote.service.downloadfile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.basenote.aidltest.RemoteActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownLoadFileStartActivity extends AppCompatActivity {
    private static final String EXTRA_ACTION = "extra_action";
    private String mTitle;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DownLoadFileStartActivity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadfile_start);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.single, R.id.multith})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                startActivity(new Intent(DownLoadFileStartActivity.this, DownloadFileActivity.class));
                break;
            case R.id.multith:
                startActivity(new Intent(DownLoadFileStartActivity.this, DownloadFile2Activity.class));
                break;
        }
    }


}
