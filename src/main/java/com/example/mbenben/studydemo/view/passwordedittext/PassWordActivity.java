package com.example.mbenben.studydemo.view.passwordedittext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.view.passwordedittext.dialog.CommonDialog;
import com.example.mbenben.studydemo.view.passwordedittext.view.CustomerKeyboard;
import com.example.mbenben.studydemo.view.passwordedittext.view.PasswordEditText;

import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/16.
 */

public class PassWordActivity extends AppCompatActivity implements CustomerKeyboard.CustomerKeyboardClickListener,
        PasswordEditText.PasswordFullListener{

    private CustomerKeyboard customerKeyboard;
    private PasswordEditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);


    }

    public void btnStart(View view){
        final CommonDialog.Builder builder = new CommonDialog.Builder(this).fullWidth().fromBottom()
                .setView(R.layout.dialog_customer_keyboard);
        builder.setOnClickListener(R.id.delete_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.create().show();
        customerKeyboard = builder.getView(R.id.custom_key_board);
        customerKeyboard.setOnCustomerKeyboardClickListener(this);
        passwordEditText =  builder.getView(R.id.password_edit_text);
        passwordEditText.setOnPasswordFullListener(this);
    }

    @Override
    public void click(String number) {
        passwordEditText.addPassword(number);
    }

    @Override
    public void delete() {
        passwordEditText.deleteLastPassword();
    }

    @Override
    public void passwordFull(String password) {
        ToastUtils.showShort("密码填充完毕：" + password);
    }
}
