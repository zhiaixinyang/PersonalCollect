package com.example.mbenben.studydemo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.Window;
import android.view.WindowManager;

import com.example.mbenben.studydemo.R;

/**
 * @author MDove on 2018/1/28.
 */
public class AboutMeDialog extends BottomSheetDialog {

    private Context mCxt;

    public AboutMeDialog(@NonNull Context context) {
        super(context, R.style.AboutMeDialog);
        mCxt = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about_me);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }
}
