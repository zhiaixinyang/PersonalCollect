package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.content.Context;
import android.view.View;

/**
 * Created by MBENBEN on 2017/8/5.
 *
 * 原项目GitHub：https://github.com/crazysunj/CardSlideView
 */

public interface CardHandler<T> {
    View onBind(Context context, T data, int position);
}
