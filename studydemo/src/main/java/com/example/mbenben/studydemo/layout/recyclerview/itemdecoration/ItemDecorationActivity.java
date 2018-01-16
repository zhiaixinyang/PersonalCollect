package com.example.mbenben.studydemo.layout.recyclerview.itemdecoration;

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

/**
 * Created by MDove on 2018/1/1.
 */

public class ItemDecorationActivity extends AppCompatActivity {
    private RecyclerView rlv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_decoration);
        rlv = (RecyclerView) findViewById(R.id.rlv);

        rlv.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("Hahahaha:" + i);
        }
        rlv.addItemDecoration(new MyItemDecoration());
        rlv.setAdapter(new CommonAdapter<String>(this, R.layout.item_info, data) {
            @Override
            public void convert(ViewHolder holder, String o) {
                holder.setText(R.id.id_info, o);
            }
        });

    }
}
