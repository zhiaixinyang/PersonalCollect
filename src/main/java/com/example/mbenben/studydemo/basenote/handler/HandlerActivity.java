package com.example.mbenben.studydemo.basenote.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/7/13.
 */

public class HandlerActivity extends AppCompatActivity {
    @BindView(R.id.tv) TextView textView;
    private MyHandler myHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        myHandler=new MyHandler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                message.arg1=123;
                myHandler.sendMessage(message);
            }
        }).start();
    }

    private static class MyHandler extends Handler{
        WeakReference<HandlerActivity> weakReference;

        public MyHandler(HandlerActivity activity){
            weakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                weakReference.get().textView.setText(msg.arg1+"");
            }
        }
    }
}
