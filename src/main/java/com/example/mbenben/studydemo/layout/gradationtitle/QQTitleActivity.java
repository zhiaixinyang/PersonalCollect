package com.example.mbenben.studydemo.layout.gradationtitle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.StatusBarUtils;
import com.example.mbenben.studydemo.layout.gradationtitle.view.GradationScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/14.
 *
 * 原作者项目博客：http://www.jianshu.com/p/67ed0530f909
 */

public class QQTitleActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{
    @BindView(R.id.scrollview) GradationScrollView scrollView;

    @BindView(R.id.listview) ListView listView;

    @BindView(R.id.textview) TextView textView;
    private int height;
    @BindView(R.id.iv_banner) ImageView ivBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtils.setImgTransparent(this);
        setContentView(R.layout.activity_qqtitle);

        ButterKnife.bind(this);

        ivBanner.setFocusable(true);
        ivBanner.setFocusableInTouchMode(true);
        ivBanner.requestFocus();

        initListeners();
        initData();
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = ivBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = ivBanner.getHeight();

                scrollView.setScrollViewListener(QQTitleActivity.this);
            }
        });
    }



    private void initData() {
        List<String> datas=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("我是一条数据->"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getInstance().getContext(), android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }


    /**
     * 滑动监听
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            textView.setBackgroundColor(Color.argb((int) 0, 144,151,166));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            textView.setTextColor(Color.argb((int) alpha, 255,255,255));
            textView.setBackgroundColor(Color.argb((int) alpha, 144,151,166));
        } else {    //滑动到banner下面设置普通颜色
            textView.setBackgroundColor(Color.argb((int) 255, 144,151,166));
        }
    }
}

