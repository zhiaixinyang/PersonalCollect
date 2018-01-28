package com.example.mbenben.studydemo.basenote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by MBENBEN on 2017/7/31.
 */

public class DialogViewHelper{
    private View contentView;
    private SparseArray<WeakReference<View>> viewArray;


    public DialogViewHelper(Context context, int layoutId) {
        this();
        contentView= LayoutInflater.from(context).inflate(layoutId,null);
    }

    public DialogViewHelper() {
        viewArray=new SparseArray<>();
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public void setViewText(int viewId, String text) {
        TextView view= getView(viewId);
        if (view!=null) {
            view.setText(text);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view=getView(viewId);
        if (view!=null){
            view.setOnClickListener(onClickListener);
        }
    }

    public <T extends View> T getView(int viewId){
        WeakReference<View> weakReference=viewArray.get(viewId);
        View view=null;
        if (weakReference!=null){
            view= weakReference.get();
        }

        if (view==null) {
            view = contentView.findViewById(viewId);
            if (view != null) {
                viewArray.put(viewId, new WeakReference<View>(view));
            }
        }
        return (T)view;
    }



    public View getContentView() {
        return contentView;
    }
}
