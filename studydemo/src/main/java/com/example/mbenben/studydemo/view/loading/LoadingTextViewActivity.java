package com.example.mbenben.studydemo.view.loading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.loading.view.Titanic;
import com.example.mbenben.studydemo.view.loading.view.TitanicTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/13.
 */

public class LoadingTextViewActivity extends AppCompatActivity{
    @BindView(R.id.my_text_view) TitanicTextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_textview);
        ButterKnife.bind(this);

        tv.setTypeface(Typefaces.get(this, "fonts/Satisfy-Regular.ttf"));
        new Titanic().start(tv);
    }
}
