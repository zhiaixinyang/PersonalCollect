package com.example.mbenben.studydemo.net.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/5.
 */

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder>{
    private Context context;
    private List<RetrofitBean> datas;
    private LayoutInflater layoutInflater;

    public RetrofitAdapter(Context context, List<RetrofitBean> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_retrofit,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RetrofitBean retrofitBean=datas.get(position);
        holder.tvName.setText(retrofitBean.getName());
        holder.tvContent.setText(retrofitBean.getContent());
        Glide.with(context).load(retrofitBean.getImgpath()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_content) TextView tvContent;
        @BindView(R.id.iv_image) ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
