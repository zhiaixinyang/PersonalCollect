package com.example.mbenben.studydemo.view.enviews.allfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allviews.ENRefreshView;
import com.example.mbenben.studydemo.view.enviews.allviews.ENScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class ScrollFragment extends Fragment{
    public static ScrollFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ScrollFragment fragment = new ScrollFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.view_scroll) ENScrollView scrollView;
    @BindView(R.id.btn_switch) Button btnSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_scroll,container,false);
        ButterKnife.bind(this,view);

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollView.isSelected()) {
                    scrollView.unSelect();
                } else {
                    scrollView.select();
                }
            }
        });

        return view;
    }
    
}
