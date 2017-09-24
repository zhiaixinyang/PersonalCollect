package com.example.mbenben.studydemo.anim.lodinganim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2017/9/20.
 */

public class LoadingView extends LinearLayout {
    private ImageView mShapeLodingView;
    private ShapeView mIndicationView;
    private Context mContext;
    private final int DISTANCE_Y = 350;
    private final int DURANTION = 1500;

    private int curViewType = 0;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initLayout();
    }

    private void initLayout() {
        View.inflate(mContext, R.layout.view_loading_anim, this);
        mShapeLodingView = (ImageView) findViewById(R.id.shapeLoadingView);
        mIndicationView = (ShapeView) findViewById(R.id.indication);

        upAnim();
    }

    private void upAnim() {
        PropertyValuesHolder upValuesHolder = PropertyValuesHolder.ofFloat("translationY", 0, -DISTANCE_Y);
        PropertyValuesHolder rorationValuesHolder = PropertyValuesHolder.ofFloat("rotation", 360, 0);
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.25f);
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.25f);

        ObjectAnimator upAnimator = new ObjectAnimator().ofPropertyValuesHolder(mIndicationView,
                upValuesHolder, rorationValuesHolder, scaleXValuesHolder, scaleYValuesHolder);
        upAnimator.setDuration(DURANTION);
        upAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator shadowAnim = new ObjectAnimator().ofFloat(mShapeLodingView, "scaleX", 1, 0.4f);
        shadowAnim.setDuration(DURANTION);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(upAnimator, shadowAnim);

        upAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                curViewType++;
                changeType(curViewType);
                if (curViewType == 3) {
                    curViewType = 0;
                }
                downAnim();
            }
        });
        set.start();
    }

    private void downAnim() {
        PropertyValuesHolder downValuesHolder = PropertyValuesHolder.ofFloat("translationY", -DISTANCE_Y, 0);
        PropertyValuesHolder rotationValuesHolder = PropertyValuesHolder.ofFloat("rotation", 0, 360);
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.25f, 1f);
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1.25f, 1f);

        ObjectAnimator downAnimator = new ObjectAnimator().ofPropertyValuesHolder(mIndicationView,
                downValuesHolder, rotationValuesHolder, scaleXValuesHolder, scaleYValuesHolder);
        downAnimator.setDuration(DURANTION);
        downAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator shadowAnim = new ObjectAnimator().ofFloat(mShapeLodingView, "scaleX", 0.4f, 1f);
        shadowAnim.setDuration(DURANTION);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(downAnimator, shadowAnim);


        downAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                upAnim();
            }
        });
        set.start();
    }

    private void changeType(int curViewType) {
        switch (curViewType) {
            case 1:
                mIndicationView.setViewType(ShapeView.ViewType.Circle);
                break;
            case 2:
                mIndicationView.setViewType(ShapeView.ViewType.Square);
                break;
            case 3:
                mIndicationView.setViewType(ShapeView.ViewType.Triangle);
                break;
        }
    }

}
