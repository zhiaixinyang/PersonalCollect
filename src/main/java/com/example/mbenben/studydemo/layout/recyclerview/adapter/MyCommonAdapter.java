package com.example.mbenben.studydemo.layout.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.ViewHolder;

import java.util.List;

/**
 * Created by MDove on 2017/1/16.
 */

public abstract class MyCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private int layoutId;
    private List<T> datas;

    public MyCommonAdapter(int layoutId, List<T> datas) {
        this.layoutId = layoutId;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(App.getInstance().getContext())
                .inflate(layoutId,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bind(holder,datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public abstract void bind(ViewHolder viewHolder,T item);
}
