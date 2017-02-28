package com.example.mbenben.studydemo.basenote.service.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class DownLoadServiceActivity extends AppCompatActivity{
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.tv_name) TextView tvName;
    private FileBean fileBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        progressBar.setMax(100);
        fileBean=new FileBean(0,
                "http://www.ohonor.xyz/image/TestImage.png",
                "TestImage.png",
                0,0);
        IntentFilter intentFilter=new IntentFilter(DownLoadService.ACTION_UPDATE);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    public void startDown(View view){
        //开启Service
        Intent startDown=new Intent(this,DownLoadService.class);
        startDown.setAction(DownLoadService.ACTION_START);
        startDown.putExtra("intent_file",fileBean);
        startService(startDown);
    }

    public void stopDown(View view){
        //开启Service
        Intent stopDown=new Intent(this,DownLoadService.class);
        stopDown.setAction(DownLoadService.ACTION_STOP);
        stopDown.putExtra("intent_file",fileBean);
        startService(stopDown);
    }

    //通过广播接受器更新UI
    BroadcastReceiver receiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownLoadService.ACTION_UPDATE)){
                int finishedLength=intent.getIntExtra("thread_finished_length",0);
                progressBar.setProgress(finishedLength);
            }
        }
    };
}
