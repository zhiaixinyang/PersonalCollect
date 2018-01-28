package com.example.mbenben.studydemo.layout.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class RecyclerView3Activity extends AppCompatActivity{
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    private List<String> datas;
    private ItemTouchHelper helper;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        datas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("我只是一条数据->"+i);
        }
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(datas);
        rlvMain.setAdapter(adapter);
        //Item滑动的系统Helper
        helper=new ItemTouchHelper(new MySimpleItemTouchCallback(adapter,datas));
        helper.attachToRecyclerView(rlvMain);
    }
}
