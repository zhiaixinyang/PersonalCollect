package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by MBENBEN on 2017/8/5.
 *
 * 原项目GitHub：https://github.com/crazysunj/CardSlideView
 */

public class BaseCardItem<T> extends Fragment {

    protected CardHandler<T> mHandler;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void bindHandler(CardHandler<T> handler) {
        mHandler = handler;
    }
}
