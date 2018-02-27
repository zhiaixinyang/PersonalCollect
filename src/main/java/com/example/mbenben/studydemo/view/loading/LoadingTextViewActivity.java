package com.example.mbenben.studydemo.view.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.loading.view.Titanic;
import com.example.mbenben.studydemo.view.loading.view.TitanicTextView;
import com.example.mbenben.studydemo.view.wave.WaveActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/2/13.
 */

public class LoadingTextViewActivity extends BaseActivity {
    @BindView(R.id.my_text_view)
    TitanicTextView tv;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, WaveActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_loading_textview);
        ButterKnife.bind(this);

        tv.setTypeface(Typefaces.get(this, "fonts/Satisfy-Regular.ttf"));
        new Titanic().start(tv);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
