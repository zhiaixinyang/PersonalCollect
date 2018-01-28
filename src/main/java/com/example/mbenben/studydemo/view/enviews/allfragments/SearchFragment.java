package com.example.mbenben.studydemo.view.enviews.allfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allviews.ENScrollView;
import com.example.mbenben.studydemo.view.enviews.allviews.ENSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class SearchFragment extends Fragment{
    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.view_search) ENSearchView searchView;
    @BindView(R.id.btn_search) Button btnStart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_search,container,false);
        ButterKnife.bind(this,view);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.start();
            }
        });

        return view;
    }
    
}
