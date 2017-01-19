package com.example.mbenben.studydemo.view.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 */

public class ProgressBarActivity extends AppCompatActivity implements View.OnClickListener , Runnable{
    @BindView(R.id.flikerbar) FlikerProgressBar flikerProgressBar;
    @BindView(R.id.round_flikerbar) FlikerProgressBar roundProgressbar;

    Thread downLoadThread;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            roundProgressbar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
                roundProgressbar.finishLoad();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        ButterKnife.bind(this);
        flikerProgressBar.setOnClickListener(this);
        roundProgressbar.setOnClickListener(this);

        downLoad();
    }
    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        if(!flikerProgressBar.isFinish()){
            flikerProgressBar.toggle();
            roundProgressbar.toggle();

            if(flikerProgressBar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }

        }
    }

    @Override
    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = flikerProgressBar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
