package com.example.mbenben.studydemo.view.enviews.allfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allviews.ENLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class LoadingFragment extends Fragment{
    public static LoadingFragment newInstance() {

        Bundle args = new Bundle();

        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.view_loading) ENLoadingView loadingView;

    @BindView(R.id.btn_show) Button btnShow;
    @BindView(R.id.btn_hide) Button btnHide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_loading,container,false);
        ButterKnife.bind(this,view);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.show();
            }
        });
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.hide();
            }
        });
        return view;
    }
}
