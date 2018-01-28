package com.example.mbenben.studydemo.view.enviews.allfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.enviews.allviews.ENDownloadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/codeestX/ENViews
 */

public class DownloadFragment extends Fragment{
    public static DownloadFragment newInstance() {

        Bundle args = new Bundle();

        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.view_download) ENDownloadView downloadView;

    @BindView(R.id.btn_start) Button btnStart;
    @BindView(R.id.btn_reset) Button btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_enview_download,container,false);
        ButterKnife.bind(this,view);

        downloadView.setDownloadConfig(2000, 20 , ENDownloadView.DownloadUnit.MB);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadView.start();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadView.reset();
            }
        });
        return view;
    }
}
