package com.example.mbenben.studydemo.layout.expandableviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.expandableviewpager.view.ExpandableViewpager;
import com.example.mbenben.studydemo.utils.DpUtils;
import com.example.mbenben.studydemo.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/28.
 */

public class ExpandableViewPagerActivity extends AppCompatActivity {
    @BindView(R.id.expandable_viewpager) ExpandableViewpager viewpager;
    @BindView(R.id.iv_expand) ImageView ivExpand;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_viewpager);
        ButterKnife.bind(this);

        viewpager.setOffscreenPageLimit(5);


        View view = View.inflate(this,R.layout.layout_expandable_viewpager_background,null);
        viewpager.setBackgroundView(view);

        //set the animation duration
        viewpager.setDuration(500);

        //set the viewpager min height
        viewpager.setMinHeight(1000);

        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(2);


        viewpager.setOnStateChangedListener(new ExpandableViewpager.OnStateChangedListener() {
            @Override
            public void onCollaps() {
                ivExpand.setVisibility(View.GONE);
            }

            @Override
            public void onExpand() {

                ivExpand.setVisibility(View.VISIBLE);
            }
        });

        ivExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.collaps();
            }
        });

    }

    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = View.inflate(App.getInstance().getContext(), R.layout.item_expandable_viewpager_pager, null);
            ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth()/5*3,
                    ScreenUtils.getScreenHeight()/5*3);
            v.setLayoutParams(lp);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.expand();
                }
            });
            container.addView(v);
            return v;
        }


    };
}
