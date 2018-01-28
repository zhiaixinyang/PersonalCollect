package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.viewpager.transformation.CardTransformer;
import com.example.mbenben.studydemo.layout.viewpager.utils.ImageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/8/5.
 */

public class FragmentFive extends Fragment {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<ImageView> datas;


    public static FragmentFive newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name",name);
        FragmentFive fragment = new FragmentFive();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_card_viewpager, container, false);
        ButterKnife.bind(this,view);
        init();
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=datas.get(position);
                container.addView(imageView,position);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        viewPager.setPageTransformer(true,new CardTransformer(50));
        return view;

    }

    private void init() {
        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ImageView imageView = new ImageView(App.getInstance().getContext());
            imageView.setImageResource(R.drawable.pintu);
            datas.add(imageView);
        }
    }
}
