package com.example.mbenben.studydemo.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MDove on 2016/12/22.
 */

public interface OnItemClickListener<T>
{
    void onItemClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
