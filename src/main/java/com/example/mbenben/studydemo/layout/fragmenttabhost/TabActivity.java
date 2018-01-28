package com.example.mbenben.studydemo.layout.fragmenttabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.fragmenttabhost.fragment.OneFragment;
import com.example.mbenben.studydemo.layout.fragmenttabhost.fragment.ThreeFragment;

/**
 * Created by MBENBEN on 2016/12/22.
 */

public class TabActivity extends AppCompatActivity{
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        layoutInflater=LayoutInflater.from(this);
        /**
         * 这里可以这么理解：
         *      TabHost就是底部导航栏的长条集合，而它的内部类TabSpec就是具体的按钮
         */
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //这里构造方法传递的值并不会对实际效果产生影响，其实和下文的TabHost.TabSpec重名
        TabHost.TabSpec one=mTabHost.newTabSpec("one");
        /**
         * R.layout.fth_tabspec_item
         * 就是底部按钮的布局文件，自己想要啥样的就整成啥样的。
         * 我这里是一个ImageView和一个TextView竖直排列
         */
        View oneView= layoutInflater.inflate(R.layout.fth_tabspec_item,null);
        ImageView iv= (ImageView) oneView.findViewById(R.id.iv_fth);
        //这里是一个图片选择selector
        iv.setBackgroundResource(R.drawable.tab_icon_select);
        TextView tv= (TextView) oneView.findViewById(R.id.tv_fth);
        tv.setText("One!");
        one.setIndicator(oneView);

        TabHost.TabSpec two=mTabHost.newTabSpec("one");
        View twoView= layoutInflater.inflate(R.layout.fth_tabspec_item,null);
        ImageView iv_= (ImageView) twoView.findViewById(R.id.iv_fth);
        iv_.setBackgroundResource(R.drawable.tab_icon_select);
        TextView tv_= (TextView) twoView.findViewById(R.id.tv_fth);
        tv_.setText("Two!");
        two.setIndicator(twoView);

        //给导航按钮绑定你所要跳转的Fragment，具体有啥实现效果直接在Fragment中写就好
        mTabHost.addTab(one,OneFragment.class, null);
        mTabHost.addTab(two,ThreeFragment.class, null);
    }
}
