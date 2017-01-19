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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class PlayFragment extends Fragment{
    public static PlayFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.view_play) ENPlayView playView;
    @BindView(R.id.btn_pause) Button btnPause;
    @BindView(R.id.btn_play) Button btnPlay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_play,container,false);
        ButterKnife.bind(this,view);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playView.pause();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playView.play();
            }
        });
        return view;
    }
    
}
