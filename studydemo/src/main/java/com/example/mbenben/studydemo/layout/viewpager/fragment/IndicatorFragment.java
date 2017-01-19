package com.example.mbenben.studydemo.layout.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.indicator.MyTestFragment;
import com.example.mbenben.studydemo.layout.indicator.MyViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class IndicatorFragment extends Fragment{
    public static IndicatorFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name",name);
        IndicatorFragment fragment = new IndicatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.indicator) MyViewPagerIndicator myViewPagerIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tv_text) TextView tvText;
    private List<String> titles=new ArrayList<String>();
    private List<MyTestFragment> fragments=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_indicator, container, false);
        ButterKnife.bind(this,view);
        initViewAndDatas();
        for (String title:titles){
            fragments.add(MyTestFragment.newInstance(title));
        }

        myViewPagerIndicator.setTitles(titles);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.size();
            }
        });
        myViewPagerIndicator.setViewPager(viewPager);

        return  view;
    }

    /**
     * 初始化ViewPager的数据
     */
    private void initViewAndDatas() {

        for (int i = 0; i < 10; i++) {
            titles.add("标题-"+i);
        }

        for (String title:titles){
            fragments.add(MyTestFragment.newInstance(title));
        }

        Bundle bundle = getArguments();
        tvText.setText(bundle.getString("name"));
    }
}
