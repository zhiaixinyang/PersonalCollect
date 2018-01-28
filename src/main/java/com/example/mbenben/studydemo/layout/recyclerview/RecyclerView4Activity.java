package com.example.mbenben.studydemo.layout.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.adapter.MyCommonAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/13.
 */

public class RecyclerView4Activity extends AppCompatActivity {
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();
    }

    private void initData() {
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("我是第一个数据 -> "+i);
        }
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        rlvMain.setAdapter(new MyCommonAdapter<String>(R.layout.item_info,datas) {
            @Override
            public void bind(ViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.id_info,item);
            }
        });
    }
}
