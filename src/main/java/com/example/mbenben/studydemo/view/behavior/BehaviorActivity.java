package com.example.mbenben.studydemo.view.behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/19.
 */

public class BehaviorActivity extends AppCompatActivity {
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setImgTransparent(this);
        setContentView(R.layout.activity_view_behavior);
        initViewAndDatas();

        rlvMain.setAdapter(new CommonAdapter<String>(
                App.getInstance().getContext(),
                R.layout.item_info,
                datas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info,s);
            }
        });
    }

    private void initViewAndDatas() {
        ButterKnife.bind(this);

        datas=new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            datas.add("我就是一条数据->"+i);
        }
    }
}
