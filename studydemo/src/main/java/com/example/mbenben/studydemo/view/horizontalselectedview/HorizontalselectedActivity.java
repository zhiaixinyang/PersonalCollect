package com.example.mbenben.studydemo.view.horizontalselectedview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/6/22.
 */

public class HorizontalselectedActivity extends AppCompatActivity {
    @BindView(R.id.horizontal_select) HorizontalselectedView horizontalselectedView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalselected);

        ButterKnife.bind(this);
        List<String> data=new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add((i+1)*1000+"");
        }
        horizontalselectedView.setData(data);
    }
}
