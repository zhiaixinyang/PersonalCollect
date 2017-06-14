package com.example.mbenben.studydemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.OnItemClickListener;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.db.SearchListBean;
import com.example.mbenben.studydemo.model.HashSetSearchBean;
import com.example.mbenben.studydemo.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/3/28.
 */

public class SearchRlvActivity extends AppCompatActivity {
    @BindView(R.id.rlv_search) RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rlv);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance().getContext()));
        List<SearchBean> searchList= (List<SearchBean>) getIntent().getSerializableExtra("search_rlv");
        CommonAdapter adapter=new CommonAdapter(SearchRlvActivity.this,R.layout.item_info,searchList) {
            @Override
            public void convert(ViewHolder holder, Object o) {
                holder.setText(R.id.id_info,((SearchBean)o).getTitle());
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Iterator<HashSetSearchBean> iterator = App.getData().iterator();
                while (iterator.hasNext()){
                    HashSetSearchBean next = iterator.next();
                    if (StringUtils.isEquals(((SearchBean)o).getActivity(),next.getName())){
                        Intent to=new Intent(SearchRlvActivity.this,next.getActivityClass());

                        startActivity(to);
                        finish();
                    }
                }

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

}
