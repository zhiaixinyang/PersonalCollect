package com.example.mbenben.studydemo.layout.recyclerview.banner.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.mbenben.studydemo.layout.recyclerview.banner.adapter.MzBannerAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.banner.layoutmanager.BannerLayoutManager;

import java.util.List;

/**
 * Created by MDove on 18/4/8.
 */

public class RecyclerViewBannerMz  extends RecyclerViewBannerBase<BannerLayoutManager, MzBannerAdapter> {

    public RecyclerViewBannerMz(Context context) {
        this(context, null);
    }

    public RecyclerViewBannerMz(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewBannerMz(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBannerScrollStateChanged(RecyclerView recyclerView, int newState) {
        int first = mLayoutManager.getCurrentPosition();
        if (currentIndex != first) {
            currentIndex = first;
            refreshIndicator();
        }
    }

    @Override
    protected BannerLayoutManager getLayoutManager(Context context, int orientation) {
        return new BannerLayoutManager(orientation, dp2px(10));
    }

    @Override
    protected MzBannerAdapter getAdapter(Context context, List<String> list, OnBannerItemClickListener onBannerItemClickListener) {
        return new MzBannerAdapter(context, list,onBannerItemClickListener);
    }
}
