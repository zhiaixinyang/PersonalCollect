package com.example.mbenben.studydemo.view.renkstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;

/**
 * DemoFragment2
 * <><p/>
 * Created by woxingxiao on 2017-03-11.
 */

public class DemoFragment2 extends Fragment {

    public static DemoFragment2 newInstance() {
        return new DemoFragment2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_renk_star_demo_2, container, false);
    }
}
