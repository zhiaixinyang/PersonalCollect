package com.example.mbenben.studydemo.layout.overscroll.activity.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.overscroll.activity.control.DemoItem;

import java.util.List;

/**
 * @author amit
 */
public class DemoGridAdapter extends DemoListAdapterBase {

    protected DemoGridAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    public DemoGridAdapter(LayoutInflater inflater, List<DemoItem> items) {
        super(inflater, items);
    }

    @Override
    protected DemoViewHolder createViewHolder(ViewGroup parent) {
        return new DemoViewHolder(R.layout.grid_item, parent, mInflater);
    }
}
