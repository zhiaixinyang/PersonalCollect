package com.example.mbenben.studydemo.basenote.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ShapeDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by MBENBEN on 2017/7/31.
 */

public class AlertController {
    private MyAlertDialog myAlertDialog;
    private Window window;
    public AlertController(MyAlertDialog myAlertDialog, Window window) {
        this.myAlertDialog=myAlertDialog;
        this.window=window;
    }

    public MyAlertDialog getMyAlertDialog() {
        return myAlertDialog;
    }



    public void setMyAlertDialog(MyAlertDialog myAlertDialog) {
        this.myAlertDialog = myAlertDialog;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }



    public static class AlertParams{

        public Context context;
        public int themeResId;
        public boolean cancelable=true;
        public DialogInterface.OnCancelListener onCancelListener;
        public DialogInterface.OnDismissListener onDismissListener;
        public DialogInterface.OnKeyListener onKeyListener;
        //相当于一个<Integer，Object>的HashMap，但是更高效
        public SparseArray<String> viewIdArray=new SparseArray<>();
        public SparseArray<View.OnClickListener> listenerArray=new SparseArray<>();
        //布局View
        public View view;
        //布局Id
        public int  layoutId;
        public int animation;
        public int gravity= Gravity.CENTER;
        public int width= ViewGroup.LayoutParams.WRAP_CONTENT;
        public int height= ViewGroup.LayoutParams.WRAP_CONTENT;
        private MyAlertDialog myAlertDialog;

        public AlertParams(Context context, int themeResId) {
            this.context=context;
            this.themeResId=themeResId;
        }

        public void apply(AlertController alertController) {
            myAlertDialog=alertController.getMyAlertDialog();
            DialogViewHelper dialogViewHelper=null;
            if (layoutId!=0){
                dialogViewHelper=new DialogViewHelper(context,layoutId);
            }
            if (view!=null){
                dialogViewHelper=new DialogViewHelper();

                dialogViewHelper.setContentView(view);
            }
            myAlertDialog.setDialogViewHelper(dialogViewHelper);
            //如果view和layoutId都没有设置，可以放回默认效果。
            if (dialogViewHelper==null){
                throw new IllegalArgumentException("请设置布局参数，View/layoutId。（setContentView）");
            }

            //设置布局
            alertController.getMyAlertDialog().setContentView(dialogViewHelper.getContentView());

            //设置setViewText
            for(int i=0;i<viewIdArray.size();i++){
                dialogViewHelper.setViewText(viewIdArray.keyAt(i),viewIdArray.valueAt(i));
            }

            //监听事件
            for(int i=0;i<listenerArray.size();i++){
                dialogViewHelper.setOnClickListener(listenerArray.keyAt(i),listenerArray.valueAt(i));
            }

            Window window=alertController.getWindow();

            window.setGravity(gravity);
            if (animation!=0){
                window.setWindowAnimations(animation);
            }

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width=width;
            layoutParams.height=height;
            window.setAttributes(layoutParams);
        }
    }

}
