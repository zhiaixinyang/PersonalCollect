package com.example.mbenben.studydemo.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

/**
 * Created by MBENBEN on 2017/1/7.
 */

public class MyButterKnifeActivity extends Activity{
    @MyBindView(R.id.text) TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        MyBufferKnife.bind(this);
        text.setText("Hahahahah");
    }

    @MyOnClick(R.id.text)
    private void onClick(View view) {

        ToastUtils.showShort("asdfghjk");
    }


}
