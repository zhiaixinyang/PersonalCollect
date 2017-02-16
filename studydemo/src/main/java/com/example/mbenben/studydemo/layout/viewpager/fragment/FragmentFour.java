package com.example.mbenben.studydemo.layout.viewpager.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.viewpager.transformation.DepthTransformation;
import com.example.mbenben.studydemo.layout.viewpager.transformation.ZoomTransformation;
import com.example.mbenben.studydemo.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class FragmentFour extends Fragment implements ViewPager.OnPageChangeListener{
    public static FragmentFour newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name",name);
        FragmentFour fragment = new FragmentFour();
        fragment.setArguments(args);
        return fragment;
    }

    //通过Handler实现自动轮播
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //更新当前viewpager的 要显示的当前条目
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tv_text) TextView tvText;
    @BindView(R.id.tv_text_ad) TextView tvTextAd;
    @BindView(R.id.ll_dots) LinearLayout llDots;
    private List<ImageView> imageViewList;
    private String[] titles;
    private boolean isSwitchPager = false; //默认不切换
    private int previousPosition = 0; //默认为0

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_viewpager_ad, container, false);
        ButterKnife.bind(this,view);
        initViewAndDatas();
        viewPager.setAdapter(new AdViewPagerAdapter(imageViewList));
        viewPager.setPageTransformer(true,new ZoomTransformation());
        //设置当前viewpager要显示第几个条目
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        viewPager.setCurrentItem(item);

        //把第一个小圆点设置为白色，显示第一个textview内容
        llDots.getChildAt(previousPosition).setEnabled(true);
        tvTextAd.setText(titles[previousPosition]);
        //设置viewpager滑动的监听事件
        viewPager.addOnPageChangeListener(this);

        //实现自动切换的功能
        new Thread() {
            public void run() {
                while (!isSwitchPager) {
                    SystemClock.sleep(3000);
                    //拿着我们创建的handler 发消息
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();


        return  view;
    }

    /**
     * 初始化ViewPager的数据
     */
    private void initViewAndDatas() {
        Bundle bundle = getArguments();
        tvText.setText(bundle.getString("name"));
        titles = new String[]{"广告描述1", "广告描述2", "广告描述3", "广告描述4", "广告描述5"};
        imageViewList = new ArrayList<ImageView>();
        int imgIds[] = {R.drawable.pintu, R.drawable.pintu, R.drawable.pintu,
                R.drawable.pintu, R.drawable.pintu};
        ImageView iv;
        View dotView;

        for (int i = 0; i < imgIds.length; i++) {
            iv = new ImageView(App.getInstance().getContext());
            iv.setBackgroundResource(imgIds[i]);
            imageViewList.add(iv);
            //准备小圆点的数据
            dotView = new View(App.getInstance().getContext());
            dotView.setBackgroundResource(R.drawable.selector_dot);
            //设置小圆点的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DpUtils.dp2px(10), DpUtils.dp2px(10));
            //设置每个小圆点之间距离
            if (i != 0) {
                params.leftMargin = DpUtils.dp2px(10);
            }
            dotView.setLayoutParams(params);
            //设置小圆点默认状态
            dotView.setEnabled(false);
            //把dotview加入到线性布局中
            llDots.addView(dotView);
        }
        LinearLayout.LayoutParams paramsllDots = new LinearLayout.LayoutParams(DpUtils.dp2px(10)*11, DpUtils.dp2px(18));
        llDots.setLayoutParams(paramsllDots);
        llDots.setGravity(Gravity.CENTER);
    }


    //当新的页面被选中的时候调用
    @Override
    public void onPageSelected(int position) {
        //拿着position位置 % 集合.size
        int newposition = position % imageViewList.size();
        //取出postion位置的小圆点 设置为true
        llDots.getChildAt(newposition).setEnabled(true);
        //把一个小圆点设置为false
        llDots.getChildAt(previousPosition).setEnabled(false);
        tvTextAd.setText(titles[newposition]);
        previousPosition = newposition;
    }

    @Override
    public void onDestroyView() {
        //当Fragment销毁的时候 把是否切换的标记置为true
        isSwitchPager = true;
        super.onDestroyView();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //当页面开始滑动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
}
