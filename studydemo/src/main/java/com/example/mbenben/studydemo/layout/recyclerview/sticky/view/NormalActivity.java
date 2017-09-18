package com.example.mbenben.studydemo.layout.recyclerview.sticky.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.view.widget.NormalDecorration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/9/19.
 */

public class NormalActivity extends AppCompatActivity {
    private RecyclerView rlv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_decoration_normal);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("我是第" + i + "条卖萌的数据");
        }
        rlv = (RecyclerView) findViewById(R.id.rlv_normal);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.addItemDecoration(new NormalDecorration());
        rlv.setAdapter(new CommonAdapter<String>(this, R.layout.item_info, data) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info, s);
                holder.getView(R.id.id_info).setBackgroundColor(ContextCompat.getColor(NormalActivity.this, R.color.gray));
            }
        });
    }
}
