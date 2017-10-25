package com.example.mbenben.studydemo.view.dianzan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/10/26.
 */

public class DianZanActivity extends AppCompatActivity {
    @BindView(R.id.thumbUpView)
    ThumbUpView thumbUpView;
    @BindView(R.id.ed_num)
    EditText edNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianzai);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_set_num)
    public void setNum(View v) {
        try {
            int num = Integer.valueOf(edNum.getText().toString().trim());
            thumbUpView.setCount(num).setThumbUp(true);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort("只能输入整数");
        }
    }
}
