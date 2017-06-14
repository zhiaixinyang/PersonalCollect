package com.example.mbenben.studydemo.layout.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.adapter.RecyclerAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse.CommonAdapterActivity;
import com.example.mbenben.studydemo.layout.recyclerview.others.dragItemAnimatorUse.DragItemAnimatorActivity;
import com.example.mbenben.studydemo.layout.recyclerview.others.headerFooterUse.HeaderFooterActivity;
import com.example.mbenben.studydemo.layout.recyclerview.others.refreshLoad.RefreshLoadActivity;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.view.MainStickyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/13.
 */

public class RecyclerActivity extends AppCompatActivity {
    @BindView(R.id.rlv_main)
    RecyclerView rlvMain;
    private List<String> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();
    }

    private void initData() {
        ButterKnife.bind(this);
        data = new ArrayList<>();
        data.add("Footer和Header");
        data.add("多种Item布局共存");
        data.add("官方可滑动删除Item");
        data.add("简单封装ViewHolder以及Adapter");
        data.add("----------");
        data.add("万能Adapter展示");
        data.add("可拖动的Item+动画");
        data.add("封装后的Footer和Header展示");
        data.add("下拉刷新+上拉加载");
        data.add("悬浮固定头部Item");

        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(data);
        rlvMain.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (data.get(position)){
                    case "Footer和Header":
                        Intent footerAndrHeader=new Intent(RecyclerActivity.this,RecyclerView1Activity.class);
                        startActivity(footerAndrHeader);
                        break;
                    case "多种Item布局共存":
                        Intent intent=new Intent(RecyclerActivity.this,RecyclerView2Activity.class);
                        startActivity(intent);
                        break;
                    case "官方可滑动删除Item":
                        Intent drag=new Intent(RecyclerActivity.this,RecyclerView3Activity.class);
                        startActivity(drag);
                        break;
                    case "简单封装ViewHolder以及Adapter":
                        Intent base=new Intent(RecyclerActivity.this,RecyclerView4Activity.class);
                        startActivity(base);
                        break;
                    case "万能Adapter展示":
                        Intent commonAdapter=new Intent(RecyclerActivity.this,CommonAdapterActivity.class);
                        startActivity(commonAdapter);
                        break;
                    case "可拖动的Item+动画":
                        Intent intentDragItem = new Intent(RecyclerActivity.this, DragItemAnimatorActivity.class);
                        startActivity(intentDragItem);
                        break;
                    case "封装后的Footer和Header展示":
                        Intent intentHeaderFooter = new Intent(RecyclerActivity.this, HeaderFooterActivity.class);
                        startActivity(intentHeaderFooter);
                        break;
                    case "下拉刷新+上拉加载":
                        Intent intentRefreshLoad = new Intent(RecyclerActivity.this, RefreshLoadActivity.class);
                        startActivity(intentRefreshLoad);
                        break;
                    case "悬浮固定头部Item":
                        Intent intentSticky = new Intent(RecyclerActivity.this, MainStickyActivity.class);
                        startActivity(intentSticky);
                        break;
                }
            }
        });
    }
}
