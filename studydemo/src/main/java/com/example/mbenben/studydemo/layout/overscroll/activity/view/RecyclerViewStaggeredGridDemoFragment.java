package com.example.mbenben.studydemo.layout.overscroll.activity.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.overscroll.activity.control.DemoContentHelper;
import com.example.mbenben.studydemo.layout.overscroll.view.OverScrollDecoratorHelper;

import java.util.ArrayList;

/**
 * @author amitd
 */
public class RecyclerViewStaggeredGridDemoFragment extends Fragment {

    private static final int GRID_SPAN_COUNT = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.recyclerview_stgrid_overscroll_demo, null, false);
        initVerticalRecyclerView((RecyclerView) fragmentView.findViewById(R.id.vertical_recycler_view));
        return fragmentView;
    }

    private void initVerticalRecyclerView(RecyclerView recyclerView) {
        LayoutInflater appInflater = LayoutInflater.from(getActivity().getApplicationContext());
        final DemoRecyclerAdapterBase adapter = new DemoRecyclerAdapterVertical(new ArrayList<>(DemoContentHelper.getReverseSpectrumItems(getResources())), appInflater);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(GRID_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

}
