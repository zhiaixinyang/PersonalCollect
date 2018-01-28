package com.example.mbenben.studydemo.layout.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<String> datas;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    public RecyclerAdapter(List<String> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_info,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvText.setText(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.id_info) TextView tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
