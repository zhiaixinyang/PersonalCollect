package com.example.mbenben.studydemo.layout.bottomnavigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/8.
 */

public class TextFragment extends Fragment{
    public static TextFragment newInstance(String msg) {

        Bundle args = new Bundle();

        TextFragment fragment = new TextFragment();
        args.putString("fragment",msg);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tv_text) TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_text, container, false);
        ButterKnife.bind(this,view);


        return view;
    }
    public void setText(String content){
        textView.setText(content);
    }
}
