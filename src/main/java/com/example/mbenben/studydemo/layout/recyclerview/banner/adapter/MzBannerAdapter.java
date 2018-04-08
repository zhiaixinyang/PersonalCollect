package com.example.mbenben.studydemo.layout.recyclerview.banner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.banner.view.RecyclerViewBannerBase;

import java.util.List;

/**
 * Created by MDove on 18/4/8.
 */

public class MzBannerAdapter extends BaseBannerAdapter<MzBannerAdapter.MzViewHolder> {

    private RecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener;

    public MzBannerAdapter(Context context, List<String> urlList, RecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener) {
        super(context, urlList);
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    protected MzBannerAdapter.MzViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_base_image, parent, false));
    }

    @Override
    public void bindCustomViewHolder(MzViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        String url = urlList.get(position % urlList.size());
        ImageView img = (ImageView) holder.imageView;
        Glide.with(context).load(url).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(position % urlList.size());
                }
            }
        });
    }

    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
