package com.example.mbenben.studydemo.view.circle.rclayout;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.databinding.ActivityRclayoutBinding;

import static com.example.mbenben.studydemo.R.id.seekbar_radius_top_left;

/**
 * Created by MDove on 2018/6/16.
 */

public class RCLayoutActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";
    private ActivityRclayoutBinding mBinding;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RCLayoutActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rclayout);

        mBinding.toolbar.setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setSupportActionBar(mBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mBinding.rcLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rcLayout.toggle();
            }
        });
        //剪裁背景
        mBinding.cbClipBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBinding.rcLayout.setClipBackground(isChecked);
            }
        });
        //圆形
        mBinding.cbCircle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBinding.rcLayout.setRoundAsCircle(isChecked);
            }
        });
        //边框粗细
        mBinding.seekbarStrokeWidth.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.rcLayout.setStrokeWidth(progress);
            }
        });
        //左上角半径
        mBinding.seekbarRadiusTopLeft.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.rcLayout.setTopLeftRadius(getProgressRadius(progress));
            }
        });
        //右上角半径
        mBinding.seekbarRadiusTopRight.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.rcLayout.setTopRightRadius(getProgressRadius(progress));
            }
        });
        //左下角半径
        mBinding.seekbarRadiusBottomLeft.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.rcLayout.setBottomLeftRadius(getProgressRadius(progress));
            }
        });
        //右下角半径
        mBinding.seekbarRadiusBottomRight.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.rcLayout.setBottomRightRadius(getProgressRadius(progress));
            }
        });
        mBinding.seekbarStrokeWidth.setProgress(getResources().getDimensionPixelSize(R.dimen.default_stroke_width));
        mBinding.cbClipBackground.setChecked(true);

    }

    private int getProgressRadius(int progress) {
        int size = getResources().getDimensionPixelOffset(R.dimen.size_example_image);
        return (int) ((float) progress / 100 * size) / 2;
    }


    public static abstract class SimpleSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
