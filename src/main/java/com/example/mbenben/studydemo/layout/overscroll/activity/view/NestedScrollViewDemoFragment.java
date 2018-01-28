package com.example.mbenben.studydemo.layout.overscroll.activity.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.overscroll.view.OverScrollDecoratorHelper;

public class NestedScrollViewDemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ScrollView rootView = (ScrollView) inflater.inflate(R.layout.nested_scrollview_demo, null, false);
        OverScrollDecoratorHelper.setUpOverScroll(rootView);
        return rootView;
    }
}
