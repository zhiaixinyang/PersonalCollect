package com.example.mbenben.studydemo.anim.swipbackhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;

/**
 * Created by MDove on 2017/9/18.
 */

public class SwipeFragment extends Fragment {
    public static SwipeFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name",name);

        SwipeFragment fragment = new SwipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView text=new TextView(App.getInstance().getContext());
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(lp);

        text.setText(getArguments().getString("name"));

        return text;
    }
}
