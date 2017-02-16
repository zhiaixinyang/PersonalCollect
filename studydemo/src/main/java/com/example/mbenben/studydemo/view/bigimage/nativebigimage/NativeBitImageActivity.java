package com.example.mbenben.studydemo.view.bigimage.nativebigimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/20.
 */

public class NativeBitImageActivity extends AppCompatActivity{
    @BindView(R.id.native_bigimage)
    NativeBigImageView nativeBigImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_bigimage);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }
}
