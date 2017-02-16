package com.example.mbenben.studydemo.view.behavior;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.StatusBarUtil;

import java.lang.ref.WeakReference;

/**
 * Created by cyandev on 2016/12/14.
 */
public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<View> {

    private WeakReference<View> dependentView;
    private ArgbEvaluator argbEvaluator;

    public HeaderFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header) {
            dependentView = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Resources resources = getDependentView().getResources();
        final float progress = 1.f - Math.abs(
                dependency.getTranslationY() / (dependency.getHeight()
                        - (resources.getDimension(R.dimen.behavior_collapsed_header_height))));

        // Translation
        final float collapsedOffset = resources.getDimension(R.dimen.behavior_collapsed_float_offset_y);
        //marginTop=130dp+状态栏高度
        final float initOffset = resources.getDimension(R.dimen.behavior_init_float_offset_y);
        final float translateY = collapsedOffset + (initOffset - collapsedOffset) * progress
                + StatusBarUtil.getStatusBarHeight(App.getInstance().getContext());
        child.setTranslationY(translateY);

        // EditText从后面参数的颜色变到前面参数
        child.setBackgroundColor((int) argbEvaluator.evaluate(
                progress, ContextCompat.getColor(App.getInstance().getContext(),R.color.trans),
                ContextCompat.getColor(App.getInstance().getContext(),R.color.trans)));

        // Margins
        final float collapsedMargin = resources.getDimension(R.dimen.behavior_collapsed_float_margin);
        final float initMargin = resources.getDimension(R.dimen.behavior_init_float_margin);
        final int margin = (int) (collapsedMargin + (initMargin - collapsedMargin) * progress);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.setMargins(margin, 0, margin, 0);
        child.setLayoutParams(lp);
        //CoordinatorLayout从透明变到colorPrimary
        parent.setBackgroundColor(ContextCompat.getColor(App.getInstance().getContext(),R.color.colorPrimary));
        return true;
    }

    private View getDependentView() {
        return dependentView.get();
    }

}
