package com.example.mbenben.studydemo.layout.materialdesign.coordinatorlayouttest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

public class UCActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout=null;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View mToolbar1=null;
    private View mToolbar2=null;

    private ImageView account =null;
    private TextView account_txt =null;
    private ImageView address =null;
    private ImageView add =null;

    private ImageView account_2 =null;
    private ImageView sweep =null;
    private ImageView search =null;
    private ImageView photo =null;

    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        myRecyclerView=(RecyclerView)findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.color1984D1));

        myRecyclerView.setAdapter(new ToolbarAdapter(this));

        mAppBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        mToolbar1=(View)findViewById(R.id.toolbar1);
        mToolbar2=(View)findViewById(R.id.toolbar2);

        account =(ImageView)findViewById(R.id.img_zhangdan);
        account_txt =(TextView)findViewById(R.id.img_zhangdan_txt);
        address =(ImageView)findViewById(R.id.tongxunlu);
        add =(ImageView)findViewById(R.id.jiahao);

        account_2 =(ImageView)findViewById(R.id.img_shaomiao);
        sweep =(ImageView)findViewById(R.id.img_fukuang);
        search =(ImageView)findViewById(R.id.img_search);
        photo =(ImageView)findViewById(R.id.img_zhaoxiang);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0){
                    //张开
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //收缩
                    mToolbar1.setVisibility(View.GONE);
                    mToolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                } else {
                    int alpha=255-Math.abs(verticalOffset);
                    if(alpha<0){
                        Log.e("alpha",alpha+"");
                        //收缩toolbar
                        mToolbar1.setVisibility(View.GONE);
                        mToolbar2.setVisibility(View.VISIBLE);
                        setToolbar2Alpha(Math.abs(verticalOffset));
                    }else{
                        //张开toolbar
                        mToolbar1.setVisibility(View.VISIBLE);
                        mToolbar2.setVisibility(View.GONE);
                        setToolbar1Alpha(alpha);
                    }
                }
            }
        });
    }

    //设置展开时各控件的透明度
    public void setToolbar1Alpha(int alpha){
        account.getDrawable().setAlpha(alpha);
        account_txt.setTextColor(Color.argb(alpha,255,255,255));
        address.getDrawable().setAlpha(alpha);
        add.getDrawable().setAlpha(alpha);
    }

    //设置闭合时各控件的透明度
    public void setToolbar2Alpha(int alpha){
        account_2.getDrawable().setAlpha(alpha);
        sweep.getDrawable().setAlpha(alpha);
        search.getDrawable().setAlpha(alpha);
        photo.getDrawable().setAlpha(alpha);
    }

}
