package com.example.mbenben.studydemo.layout.fragmenttabhost.ui;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2016/9/27.
 */
public class NewToolBar extends Toolbar{
    public NewToolBar(Context context) {
        super(context);

    }

    public NewToolBar(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public NewToolBar(Context context,AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    private void initView(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_ui_toolbar,null);
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

        CircleImageView iv_avatar= (CircleImageView) view.findViewById(R.id.iv_avatar);
        EditText et_toolbar= (EditText) view.findViewById(R.id.et_toolbar);
        ImageView iv_edittext= (ImageView) view.findViewById(R.id.iv_edittext);
        addView(view,layoutParams);

    }
}
