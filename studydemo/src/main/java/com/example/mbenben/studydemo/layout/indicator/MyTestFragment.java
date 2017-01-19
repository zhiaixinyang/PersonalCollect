package com.example.mbenben.studydemo.layout.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MBENBEN on 2016/12/13.
 */

public class MyTestFragment extends Fragment {
    private static String AAA="aaa";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        TextView textView=new TextView(getActivity());
        textView.setText(bundle.getString(AAA));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public static MyTestFragment newInstance(String content) {

        Bundle args = new Bundle();
        args.putString(AAA,content);
        MyTestFragment fragment = new MyTestFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
