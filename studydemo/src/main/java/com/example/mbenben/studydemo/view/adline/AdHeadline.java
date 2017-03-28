package com.example.mbenben.studydemo.view.adline;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.aopa.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MBENBEN on 2017/1/31.
 */

public class AdHeadline extends RelativeLayout {

    private HeadlineClickListener listener;

    private LayoutInflater inflater;
    private ViewFlipper viewFlipper;
    private List<View> data = new ArrayList<>();

    public AdHeadline(Context context) {
        this(context, null);
    }

    public AdHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_ad_headline_layout, this, true);
        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.ad_headline_viewflipper);
        for (View view : data) {
            viewFlipper.addView(view);
        }
        findViewById(R.id.ad_headline_more_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMoreClick();
                }
            }
        });
        //进入动画
        viewFlipper.setInAnimation(getContext(), R.anim.view_headline_in);
        //退出动画
        viewFlipper.setOutAnimation(getContext(), R.anim.view_headline_out);
        //动画间隔
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    //配置滚动的数据
    public void setData(List<HeadlineBean> data) {
        convertData(data);
        initView();
    }

    //将HeadlineBean数据转换成View数据
    private void convertData(final List<HeadlineBean> list) {
        for (final HeadlineBean bean : list) {
            final HeadlineBean b = bean;
            final View view = inflater.inflate(R.layout.view_ad_headline_holder, viewFlipper, false);
            final TextView headline_title = (TextView) view.findViewById(R.id.headline_title_tv);
            final TextView headline_content = (TextView) view.findViewById(R.id.headline_content_tv);
            headline_title.setText(bean.getTitle());
            headline_content.setText(bean.getContent());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onHeadlineClick(b);
                    }
                }
            });
            data.add(view);
        }
    }

    public void setHeadlineClickListener(HeadlineClickListener listener) {
        this.listener = listener;
    }

    public interface HeadlineClickListener {
        void onHeadlineClick(HeadlineBean bean);

        void onMoreClick();
    }
}
