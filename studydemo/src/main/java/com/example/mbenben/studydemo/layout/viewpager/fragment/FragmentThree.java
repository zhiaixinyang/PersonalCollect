package com.example.mbenben.studydemo.layout.viewpager.fragment;

import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.viewpager.transformation.DepthTransformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class FragmentThree extends Fragment{
    public static FragmentThree newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name",name);

        FragmentThree fragment = new FragmentThree();
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tv_text) TextView tvText;
    private List<ImageView> datas;
    private int pagerWidth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_viewpager, container, false);

        initDatas(view);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return datas.size();
            }
            /**
             * 确定instantiateItem返回的特定key对象是否与page View有关联。
             * 由于instantiateItem中可以以自身page为key返回
             */
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
            /**
             * 这个方法是负责向ViewGroup也就是ViewPager中添加新的页面，
             * 并返回一个继承自Object的key，
             * 这个key将会在第三个方法destroyItem和第四个方法isViewFromObject中作为参数调用。
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=datas.get(position);
                container.addView(imageView,position);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(datas.get(position));

            }
        });
        viewPager.setPageTransformer(true,new DepthTransformation());

        return  view;
    }
    private void initDatas(View view) {
        ButterKnife.bind(this,view);

        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ImageView imageView = new ImageView(App.getInstance().getContext());
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    App.getInstance().getContext().getResources(),R.drawable.pintu));
            datas.add(imageView);
        }

        Bundle bundle = getArguments();
        tvText.setText(bundle.getString("name"));
        pagerWidth= (int) (getResources().getDisplayMetrics().widthPixels*4.0f/5.0f);
        ViewGroup.LayoutParams lp= viewPager.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        viewPager.setLayoutParams(lp);

    }
}
