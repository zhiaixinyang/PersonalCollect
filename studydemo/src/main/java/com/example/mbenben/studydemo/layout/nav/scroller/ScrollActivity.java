package com.example.mbenben.studydemo.layout.nav.scroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/12/23.
 */

public class ScrollActivity extends AppCompatActivity{
    @BindView(R.id.rlv_main) RecyclerView rlv_main;
    private List<String> datas=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        init();
        rlv_main= (RecyclerView) findViewById(R.id.rlv_main);
        rlv_main.setLayoutManager(new LinearLayoutManager(this));
        rlv_main.setAdapter(new CommonAdapter<String>(this,R.layout.item_info,datas){
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info,s);
            }
        });
    }

    private void init() {
        for (int i=0;i<30;i++){
            datas.add("Haaahaaa..."+i);
        }
    }
}
