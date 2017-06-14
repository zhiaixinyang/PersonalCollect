package com.example.mbenben.studydemo.view.switchbutton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ScreenUtils;

/**
 * Created by MBENBEN on 2017/6/13.
 */

public class TestFragment extends Fragment {
    public static TestFragment newInstance() {

        Bundle args = new Bundle();

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView =new TextView(App.getInstance().getContext());
        textView.setText("我是测试Fragment");
        textView.setWidth(ScreenUtils.getScreenWidth());
        textView.setHeight(ScreenUtils.getScreenHeight());
        textView.setBackgroundColor(ContextCompat.getColor(App.getInstance().getContext(), R.color.blue_dark));
        return textView;
    }
}
