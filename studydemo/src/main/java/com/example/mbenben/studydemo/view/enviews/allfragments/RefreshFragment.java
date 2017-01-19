package com.example.mbenben.studydemo.view.enviews.allfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allviews.ENPlayView;
import com.example.mbenben.studydemo.view.enviews.allviews.ENRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class RefreshFragment extends Fragment{
    public static RefreshFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RefreshFragment fragment = new RefreshFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.view_refresh) ENRefreshView refreshView;
    @BindView(R.id.btn_refresh) Button btnRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_refresh,container,false);
        ButterKnife.bind(this,view);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshView.startRefresh();
            }
        });

        return view;
    }
    
}
