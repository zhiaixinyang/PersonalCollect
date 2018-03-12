package com.example.mbenben.studydemo.basenote.service.downloadfile.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.service.downloadfile.bean.FileInfo;
import com.example.mbenben.studydemo.basenote.service.downloadfile.service.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadFileActivity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DownloadFileActivity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String url;
    FileInfo fileInfo;
    @BindView(R.id.pro_text)
    TextView proText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_downloadfile);
        ButterKnife.bind(this);
        init();

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    private void init() {
        proText.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        //创建文件信息对象
        url = "http://dldir1.qq.com/weixin/android/weixin6316android780.apk";
        fileInfo = new FileInfo(0, url, "WeChat", 0, 0);
        name.setText(fileInfo.getFileName());
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                progressBar.setProgress(finished);
                proText.setText(new StringBuffer().append(finished).append("%"));
            }
        }
    };


    @OnClick({R.id.start, R.id.pause})
    public void onClick(View view) {
        Intent intent = new Intent(DownloadFileActivity.this, DownloadService.class);
        switch (view.getId()) {
            case R.id.start:
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
            case R.id.pause:
                intent.setAction(DownloadService.ACTION_PAUSE);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
