package com.example.mbenben.studydemo.basenote.service.downloadfile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.aidltest.RemoteActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownLoadFileStartActivity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DownLoadFileStartActivity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_downloadfile_start);
        ButterKnife.bind(this);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @OnClick({R.id.single, R.id.multith})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                DownloadFileActivity.start(this,"单线程下载");
                break;
            case R.id.multith:
                DownloadFile2Activity.start(this,"多线程下载");
                break;
        }
    }


}
