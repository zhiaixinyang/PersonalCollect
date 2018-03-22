package com.example.mbenben.studydemo.layout.guideview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.tantan.TantanActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnAty, mBtnFrag, mBtnList, mBtnView, mBtnFragView, mBtnMore;
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_guide_view_home);
        mBtnAty = (Button) findViewById(R.id.btn_aty);
        mBtnFrag = (Button) findViewById(R.id.btn_frag);
        mBtnFragView = (Button) findViewById(R.id.btn_frag_view);
        mBtnList = (Button) findViewById(R.id.btn_list);
        mBtnMore = (Button) findViewById(R.id.btn_more);
        mBtnView = (Button) findViewById(R.id.btn_view);
        mBtnAty.setOnClickListener(this);
        mBtnFrag.setOnClickListener(this);
        mBtnFragView.setOnClickListener(this);
        mBtnList.setOnClickListener(this);
        mBtnMore.setOnClickListener(this);
        mBtnView.setOnClickListener(this);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_aty:
                startActivity(new Intent(HomeActivity.this, FullActivity.class));
                break;
            case R.id.btn_frag:
                startActivity(new Intent(HomeActivity.this, FragActivity.class).putExtra("fragmentId", 0));
                break;
            case R.id.btn_frag_view:
                startActivity(new Intent(HomeActivity.this, FragActivity.class).putExtra("fragmentId", 1));
                break;
            case R.id.btn_list:
                startActivity(new Intent(HomeActivity.this, MyListActivity.class));
                break;
            case R.id.btn_more:
                startActivity(new Intent(HomeActivity.this, SimpleGuideViewActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
                break;
        }
    }
}
