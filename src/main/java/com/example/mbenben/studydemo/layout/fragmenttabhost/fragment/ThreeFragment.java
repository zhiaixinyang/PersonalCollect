package com.example.mbenben.studydemo.layout.fragmenttabhost.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.net.retrofit.model.RetrofitApi;

/**
 * Created by MBENBEN on 2016/9/26.
 */
public class ThreeFragment extends Fragment{
    private TextView btn_rxjava;
    private ImageView iv_rxjava;
    private String url="http://ip.taobao.com/service/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fth_fragment_three,container,false);

        return view;
    }

}
