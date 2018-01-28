package com.example.mbenben.studydemo.layout.recyclerview.sticky.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gavin
 * Created date 17/6/5
 * Created log
 */

public class SimpleAdapter extends RecyclerView.Adapter {
    private List<City> mCities = new ArrayList<>();
    private Context mContext;

    public SimpleAdapter(Context context, List<City> cities) {
        mCities.addAll(cities);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int i = position % 5 + 1;
        if (i == 1) {
            holder.mIvCity.setImageResource(R.drawable.subject1);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg1));
        } else if (i == 2) {
            holder.mIvCity.setImageResource(R.drawable.subject2);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg2));
        } else if (i == 3) {
            holder.mIvCity.setImageResource(R.drawable.subject3);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg3));
        } else if (i == 4) {
            holder.mIvCity.setImageResource(R.drawable.subject4);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg4));
        } else {
            holder.mIvCity.setImageResource(R.drawable.subject5);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg5));
        }
        holder.mTvCity.setText(mCities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_city)
        ImageView mIvCity;
        @BindView(R.id.tv_city)
        TextView mTvCity;
        @BindView(R.id.tv_brief)
        TextView mTvBrief;
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
