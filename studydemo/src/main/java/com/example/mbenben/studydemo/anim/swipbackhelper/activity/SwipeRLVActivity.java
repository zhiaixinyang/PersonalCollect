package com.example.mbenben.studydemo.anim.swipbackhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.anim.swipbackhelper.view.SwipeBackHelper;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/9/18.
 */

public class SwipeRLVActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        RecyclerView recyclerView = new RecyclerView(App.getInstance().getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(lp);
        setContentView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("我是第" + i + "条卖萌的数据");
        }
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_info, data) {
            @Override
            public void convert(ViewHolder holder, String o) {
                holder.setText(R.id.id_info, o);
            }

        });

        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false)
                .setSwipeBackEnable(true);
    }
}
