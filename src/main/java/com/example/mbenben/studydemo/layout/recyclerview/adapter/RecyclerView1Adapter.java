package com.example.mbenben.studydemo.layout.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/13.
 */

public class RecyclerView1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> datas;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecyclerView1Adapter(List<String> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }else if(position==datas.size()+1){
            return 2;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            return new HeaderVH(layoutInflater.inflate(R.layout.item_header,parent,false));
        }else if(viewType==2){
            return new FooterVH(layoutInflater.inflate(R.layout.item_footer,parent,false));
        }
        return new ContentVH(layoutInflater.inflate(R.layout.item_info,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentVH){
            ((ContentVH)holder).textView.setText(datas.get(position-1));
        }else if (holder instanceof FooterVH){
            //这里不进行赋值，使用布局的初始化内容
        }else if(holder instanceof HeaderVH){

        }
    }


    @Override
    public int getItemCount() {
        return datas==null? 2:datas.size()+2;
    }

    public class ContentVH extends RecyclerView.ViewHolder{
        @BindView(R.id.id_info) TextView textView;
        public ContentVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class HeaderVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_text) TextView tvText;
        @BindView(R.id.iv_image) ImageView ivImage;
        public HeaderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class FooterVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_text) TextView tvText;
        @BindView(R.id.iv_image) ImageView ivImage;
        public FooterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
