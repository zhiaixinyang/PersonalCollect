package com.example.mbenben.studydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.anim.ValueAnimActivity;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.OnItemClickListener;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.view.pathview.PathViewActivity;
import com.example.mbenben.studydemo.view.pathview.svgpath.SVGPathActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/17.
 */

public class AnimsFragment extends Fragment{
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    @BindView(R.id.tv_desc) TextView tvDesc;

    private static final String KEY="anims";
    private List<String> datas=new ArrayList<>();
    private Map<String,String> map=new HashMap<>();

    private CommonAdapter<String> adapter;
    public static AnimsFragment newInstance(String desc) {
        
        Bundle args = new Bundle();
        args.putString(KEY,desc);
        AnimsFragment fragment = new AnimsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_main_layout,container,false);
        initView(view);
        initRlv();
        return view;
    }

    private void initRlv() {
        adapter=new CommonAdapter<String>(App.getInstance().getContext(),R.layout.item_info,datas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info,map.get(s));
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                switch (o.toString()){
                    case "PathViewActivity":
                        Intent intentNav=new Intent(App.getInstance().getContext(), PathViewActivity.class);
                        startActivity(intentNav);
                        break;
                    case "ValueAnimActivity":
                        Intent intentView=new Intent(App.getInstance().getContext(), ValueAnimActivity.class);
                        startActivity(intentView);
                        break;
                    case "SVGPathActivity":
                        Intent intentNestedScroll=new Intent(App.getInstance().getContext(), SVGPathActivity.class);
                        startActivity(intentNestedScroll);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        rlvMain.setAdapter(adapter);
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);

        rlvMain.setLayoutManager(new LinearLayoutManager(App.getInstance().getContext()));

        Bundle bundle = getArguments();
        String string = bundle.getString(KEY);
        tvDesc.setText(string);

        datas.add("PathViewActivity");
        map.put("PathViewActivity","PathView效果");

        datas.add("ValueAnimActivity");
        map.put("ValueAnimActivity","属性动画实例");

        datas.add("SVGPathActivity");
        map.put("SVGPathActivity","SVG-Path动画实例");

    }
}
