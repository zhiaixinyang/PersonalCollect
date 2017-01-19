package com.example.mbenben.studydemo.layout.multilinechoose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/crazyandcoder/MultiLineChoose
 */

public class MultiLineChooseActivity extends AppCompatActivity{


    @BindView(R.id.singleChoose) MultiLineChooseLayout singleChoose;
    @BindView(R.id.multiChoose) MultiLineChooseLayout multiChoose;
    @BindView(R.id.flowLayout) MultiLineChooseLayout flowLayout;
    @BindView(R.id.singleChooseTv) TextView singleChooseTv;
    @BindView(R.id.multiChooseTv) TextView multiChooseTv;
    @BindView(R.id.flowLayoutTv) TextView flowLayoutTv;
    @BindView(R.id.button) Button button;
    private List<String> mDataList = new ArrayList<>();
    private List<String> multiChooseResult = new ArrayList<>();
    private List<String> flowLayoutResult = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multilinechoose);
        initViewAndDatas();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleChoose.cancelAllSelectedItems();
                multiChoose.cancelAllSelectedItems();
                flowLayout.cancelAllSelectedItems();
            }
        });

        //单选
        singleChoose.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                singleChooseTv.setText("结果：position: " + position + "   " + text);
            }
        });

        //多选
        multiChoose.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                multiChooseResult = multiChoose.getAllItemSelectedTextWithListArray();
                if (multiChooseResult != null && multiChooseResult.size() > 0) {
                    String textSelect = "";
                    for (int i = 0; i < multiChooseResult.size(); i++) {
                        textSelect += multiChooseResult.get(i) + " , ";
                    }
                    multiChooseTv.setText("结果：" + textSelect);
                }
            }
        });

        //流式布局
        flowLayout.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                flowLayoutResult = flowLayout.getAllItemSelectedTextWithListArray();
                if (flowLayoutResult != null && flowLayoutResult.size() > 0) {
                    String textSelect = "";
                    for (int i = 0; i < flowLayoutResult.size(); i++) {
                        textSelect += flowLayoutResult.get(i) + " , ";
                    }
                    flowLayoutTv.setText("结果：" + textSelect);
                }
            }
        });

    }

    private void initViewAndDatas() {
        ButterKnife.bind(this);
        mDataList.add("赵云");
        mDataList.add("关羽");
        mDataList.add("张飞");
        mDataList.add("黄忠");
        mDataList.add("马超");
        mDataList.add("吕布");
        mDataList.add("高顺");
        mDataList.add("张辽");
        mDataList.add("诸葛亮");
        singleChoose.setList(mDataList);
        multiChoose.setList(mDataList);
        flowLayout.setList(mDataList);

    }
}
