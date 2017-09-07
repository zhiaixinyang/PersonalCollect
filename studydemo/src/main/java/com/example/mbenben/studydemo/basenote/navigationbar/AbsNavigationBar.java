package com.example.mbenben.studydemo.basenote.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.basenote.dialog.MyAlertDialog;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by MBENBEN on 2017/8/3.
 */

public abstract class AbsNavigationBar implements INavigationBar {
    private Builder.AbsNavigationParams params;
    private View navigationView;

    public AbsNavigationBar(Builder.AbsNavigationParams params) {
        this.params = params;
        createAndBindView();
    }

    /**
     * 创建和绑定View
     */

    private void createAndBindView() {
        if(params.parent==null){
            ViewGroup activityRoot= (ViewGroup) ((Activity)params.context).getWindow().getDecorView();
            params.parent= (ViewGroup) activityRoot.getChildAt(0);
        }
        navigationView=LayoutInflater.from(params.context).inflate(bingLayoutId(),params.parent,false);
        params.parent.addView(navigationView,0);

        applyView();
    }

    protected void setText(int viewId,String content){
        TextView textView=findViewById(viewId);
        if (textView!=null){
            textView.setText(content);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener onClickListener){
        View view=findViewById(viewId);
        if (view!=null&&onClickListener!=null){
            view.setOnClickListener(onClickListener);
        }
    }

    protected Builder.AbsNavigationParams getParams(){
        return params;
    }

    protected View getNavigationView() {
        return navigationView;
    }
    private <T extends View> T findViewById(int viewId){
        return (T) navigationView.findViewById(viewId);
    }

    public abstract static class Builder{
        private AbsNavigationParams P;
        //直接将这个Builder出来的导航栏加到parent之中
        public Builder(Context context,ViewGroup parent){
            P=new AbsNavigationParams(context,parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams{
            public Context context;
            public ViewGroup parent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.context = context;
                this.parent = parent;
            }
        }
    }
}
