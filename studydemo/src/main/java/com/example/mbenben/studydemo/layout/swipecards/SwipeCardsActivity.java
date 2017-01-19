package com.example.mbenben.studydemo.layout.swipecards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2017/1/2.
 *
 * 原作者项目GitHub：https://github.com/Diolor/Swipecards
 */

public class SwipeCardsActivity extends AppCompatActivity{
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    private SwipeFlingAdapterView swipeCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipecards);
        initView();
        swipeCard.setAdapter(arrayAdapter);
        swipeCard.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                ToastUtil.toastShort( "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                ToastUtil.toastShort( "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = swipeCard.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        swipeCard.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                ToastUtil.toastShort( "Clicked!");
            }
        });

    }

    private void initView() {
        swipeCard= (SwipeFlingAdapterView) findViewById(R.id.swipeCard);
        al = new ArrayList<>();
        al.add("php");
        al.add("c");
        al.add("python");
        al.add("java");
        al.add("html");
        al.add("c++");
        al.add("css");
        al.add("javascript");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_swipecard, R.id.helloText, al );
    }
}
