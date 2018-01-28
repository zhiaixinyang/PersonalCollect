package com.example.mbenben.studydemo.layout.recyclerview.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.model.DataBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/1/1.
 */

public class RecyclerView2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<DataBean> datas;
    private LayoutInflater layoutInflater;
    private DataBean dataBean;

    public RecyclerView2Adapter(Context context, List<DataBean> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            return new ViewHolderOne(layoutInflater.inflate(R.layout.item_rlv_one,parent,false));
        }else if(viewType==2){
            return new ViewHoldeTwo(layoutInflater.inflate(R.layout.item_rlv_two,parent,false));
        }
        return new ViewHoldeThree(layoutInflater.inflate(R.layout.item_rlv_three,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        dataBean=datas.get(position);
        if (holder instanceof ViewHolderOne){
            ((ViewHolderOne)holder).tvOne.setText(dataBean.getTvOne());
        }else if(holder instanceof ViewHoldeTwo){
            ((ViewHoldeTwo)holder).ivTwo.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),dataBean.getIvTwo()));
        }else{
            ((ViewHoldeThree)holder).ivThree.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),dataBean.getIvThree()));
            ((ViewHoldeThree)holder).tvThreeOne.setText(dataBean.getTvThreeOne());
            ((ViewHoldeThree)holder).tvThreeTwo.setText(dataBean.getTvThreeTwo());
        }
    }

    @Override
    public int getItemViewType(int position) {
        dataBean=datas.get(position);
        if (dataBean.getType()==1){
            return 1;
        }else if(dataBean.getType()==2){
            return 2;
        }
        return 3;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class ViewHolderOne extends RecyclerView.ViewHolder{
        private TextView tvOne;
        public ViewHolderOne(View itemView) {
            super(itemView);
            tvOne= (TextView) itemView.findViewById(R.id.tv_rlv_one);
        }
    }
    private class ViewHoldeTwo extends RecyclerView.ViewHolder{
        private ImageView ivTwo;
        public ViewHoldeTwo(View itemView) {
            super(itemView);
            ivTwo= (ImageView) itemView.findViewById(R.id.iv_rlv_two);
        }
    }
    private class ViewHoldeThree extends RecyclerView.ViewHolder{
        private TextView tvThreeOne,tvThreeTwo;
        private ImageView ivThree;
        public ViewHoldeThree(View itemView) {
            super(itemView);
            tvThreeOne= (TextView) itemView.findViewById(R.id.tv_rlv_three_one);
            tvThreeTwo= (TextView) itemView.findViewById(R.id.tv_rlv_three_two);
            ivThree= (ImageView) itemView.findViewById(R.id.iv_rlv_three);
        }
    }
}
