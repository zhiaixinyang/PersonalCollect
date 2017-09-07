package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

/**
 * Created by MBENBEN on 2017/8/5.
 */

public class MyCardHandler implements CardHandler<String> {

    @Override
    public View onBind(final Context context, final String data, final int position) {
        View view = View.inflate(context, R.layout.item_card_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Glide.with(context).load(data).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("data:" + data + "position:" + position);
            }
        });
        return view;
    }
}
