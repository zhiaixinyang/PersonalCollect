package com.example.mbenben.studydemo.layout.recyclerview.layoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2018/1/14.
 */

public class LayoutManagerActivity extends AppCompatActivity {
    @BindView(R.id.rlv)
    RecyclerView rlv;
    List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("Hahaha:" + i);
        }
        rlv.setLayoutManager(new MyLayoutManager());
        rlv.setAdapter(new MyLayoutAdapter());
    }

    public class MyLayoutAdapter extends RecyclerView.Adapter<MyLayoutAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(LayoutManagerActivity.this).
                    inflate(R.layout.item_info, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.id_info);
            }
        }
    }
}
