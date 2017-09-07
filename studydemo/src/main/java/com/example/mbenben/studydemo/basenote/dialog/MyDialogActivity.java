package com.example.mbenben.studydemo.basenote.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MBENBEN on 2017/8/1.
 */

public class MyDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_show)
    public void onViewClicked() {
        MyAlertDialog dialog=new MyAlertDialog.Builder(this)
                .setContentView(R.layout.layout_my_dialog)
                .setViewText(R.id.tv_name,"杨戬")
                .setFromBottom(true)
                .setFullWidth()
                .create();
        final EditText editText=dialog.getView(R.id.et_nick);
        dialog.setOnClickListener(R.id.btn_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(editText.getText().toString());
            }
        });
        dialog.show();
    }
}
