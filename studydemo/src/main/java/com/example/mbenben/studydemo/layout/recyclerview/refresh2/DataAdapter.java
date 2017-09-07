package com.example.mbenben.studydemo.layout.recyclerview.refresh2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * create by luoxiaoke 2017/3/30 11:00
 * eamil:wtimexiaoke@gmail.com
 * github:https://github.com/103style
 * csdn:http://blog.csdn.net/lxk_1993
 * 简书：http://www.jianshu.com/u/109656c2d96f
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public DataAdapter(Context mContext, List<String> datas) {
        this.mContext = mContext;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvShow.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null)
            return 0;
        return mDatas.size();
    }

    public void setDatasAndNotify(List<String> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvShow;

        private ViewHolder(View itemView) {
            super(itemView);
            tvShow = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
