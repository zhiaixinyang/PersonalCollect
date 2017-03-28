package com.example.mbenben.studydemo.view.couponview.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.view.couponview.view.CouponView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 原项目GitHub：https://github.com/dongjunkun/CouponView
 */
public class CouponCombinationFragment extends Fragment {


    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private CommonAdapter<Integer> commonAdapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_coupon_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int[] intArray = getResources().getIntArray(R.array.colors);
        list.clear();
        for (int anIntArray : intArray) {
            list.add(anIntArray);
        }
        commonAdapter = new CommonAdapter<Integer>(getActivity(), R.layout.item_coupon_combination, list) {
            @Override
            public void convert(ViewHolder holder, Integer integer) {
                CouponView couponView = holder.getView(R.id.couponView);
                couponView.setBackgroundColor(integer);
            }
        };
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(dp2Px(6), dp2Px(6), dp2Px(6), 0);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(commonAdapter);
    }


    private int dp2Px(float dp) {
        return (int) (dp * getActivity().getResources().getDisplayMetrics().density + 0.5f);
    }
}
