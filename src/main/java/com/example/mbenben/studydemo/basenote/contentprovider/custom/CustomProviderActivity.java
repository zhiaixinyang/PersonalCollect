package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

/**
 * Created by MDove on 2018/4/11.
 */

public class CustomProviderActivity extends BaseActivity {
    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_provider);
    }
}
