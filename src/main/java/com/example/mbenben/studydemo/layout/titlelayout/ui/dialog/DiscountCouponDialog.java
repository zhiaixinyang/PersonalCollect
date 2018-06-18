package com.example.mbenben.studydemo.layout.titlelayout.ui.dialog;

import com.example.mbenben.studydemo.R;

/**
 * Created by ${GongWenbo} on 2018/5/23 0023.
 *
 * 原项目GItHub:https://github.com/GongWnbo/SuperRecycleView
 */
public class DiscountCouponDialog extends BaseDialog {

    @Override
    protected void init() {
        setCancelable(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_discount_coupon;
    }


}
