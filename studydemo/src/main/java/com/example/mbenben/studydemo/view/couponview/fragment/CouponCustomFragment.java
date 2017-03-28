package com.example.mbenben.studydemo.view.couponview.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.couponview.view.CouponView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 原项目GitHub：https://github.com/dongjunkun/CouponView
 */
public class CouponCustomFragment extends Fragment {


    @BindView(R.id.couponView) CouponView mCouponView;
    @BindView(R.id.semicircle_top) CheckBox mSemicircleTop;
    @BindView(R.id.semicircle_bottom) CheckBox mSemicircleBottom;
    @BindView(R.id.semicircle_left) CheckBox mSemicircleLeft;
    @BindView(R.id.semicircle_right) CheckBox mSemicircleRight;
    @BindView(R.id.dash_line_top) CheckBox mDashLineTop;
    @BindView(R.id.dash_line_bottom) CheckBox mDashLineBottom;
    @BindView(R.id.dash_line_left) CheckBox mDashLineLeft;
    @BindView(R.id.dash_line_right) CheckBox mDashLineRight;
    @BindView(R.id.sbSemicircleRadius) SeekBar mSbSemicircleRadius;
    @BindView(R.id.sbSemicircleCap) SeekBar mSbSemicircleCap;
    @BindView(R.id.sbDashLineLength) SeekBar mSbDashLineLength;
    @BindView(R.id.sbDashLineGap) SeekBar mSbDashLineGap;
    @BindView(R.id.sbDashLineHeight) SeekBar mSbDashLineHeight;
    @BindView(R.id.sbTopDashLineMargin) SeekBar mSbTopDashLineMargin;
    @BindView(R.id.sbBottomDashLineMargin) SeekBar mSbBottomDashLineMargin;
    @BindView(R.id.sbLeftDashLineMargin) SeekBar mSbLeftDashLineMargin;
    @BindView(R.id.sbRightDashLineMargin) SeekBar mSbRightDashLineMargin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_coupon_custom, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSemicircleTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleTop(isChecked);
            }
        });
        mSemicircleBottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleBottom(isChecked);
            }
        });
        mSemicircleLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleLeft(isChecked);
            }
        });
        mSemicircleRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setSemicircleRight(isChecked);
            }
        });

        mDashLineTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineTop(isChecked);
            }
        });
        mDashLineBottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineBottom(isChecked);
            }
        });
        mDashLineLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineLeft(isChecked);
            }
        });
        mDashLineRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCouponView.setDashLineRight(isChecked);
            }
        });
        mSbSemicircleRadius.setProgress((int) mCouponView.getSemicircleRadius());
        mSbSemicircleRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setSemicircleRadius(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSbSemicircleCap.setProgress((int) mCouponView.getSemicircleGap());
        mSbSemicircleCap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setSemicircleGap(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineLength.setProgress((int) mCouponView.getDashLineLength());
        mSbDashLineLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineLength(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineGap.setProgress((int) mCouponView.getDashLineGap());
        mSbDashLineGap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineGap(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbDashLineHeight.setProgress((int) mCouponView.getDashLineHeight() * 10);
        mSbDashLineHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineHeight(dp2Px(progress) / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbTopDashLineMargin.setProgress((int) mCouponView.getDashLineMarginTop());
        mSbTopDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginTop(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSbBottomDashLineMargin.setProgress((int) mCouponView.getDashLineMarginBottom());
        mSbBottomDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginBottom(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbLeftDashLineMargin.setProgress((int) mCouponView.getDashLineMarginLeft());
        mSbLeftDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginLeft(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSbRightDashLineMargin.setProgress((int) mCouponView.getDashLineMarginRight());
        mSbRightDashLineMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCouponView.setDashLineMarginRight(dp2Px(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private int dp2Px(float dp) {
        return (int) (dp * getActivity().getResources().getDisplayMetrics().density + 0.5f);
    }

}
