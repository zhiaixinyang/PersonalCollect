package com.example.mbenben.studydemo.layout.photowall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/9/13.
 */

public class PhotoWallActivity extends AppCompatActivity {
    @BindView(R.id.rlv_main)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new PhotoWallAdapter(this,Images.images, recyclerView));
    }
}
