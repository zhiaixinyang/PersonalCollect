package com.example.mbenben.studydemo.view.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by MDove on 2018/12/15.
 */

public class StepsViewActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";
    private StepsView mStepView;
    private TextView mTvSign;
    private ArrayList<StepBean> mStepBeans = new ArrayList<>();

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, StepsViewActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_steps_view);

        mStepView = (StepsView) findViewById(R.id.steps_view);
        mTvSign = (TextView) findViewById(R.id.tv_sign_click);

        initData();

        initListener();
    }

    private void initListener() {
        mTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepView.startSignAnimation(2);
            }
        });
    }

    private void initData() {
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, 2));
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, 4));
        mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, 10));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 2));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 4));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 4));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 30));

        mStepView.setStepNum(mStepBeans);
    }
}
