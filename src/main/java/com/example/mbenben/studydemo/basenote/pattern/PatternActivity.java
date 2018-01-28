package com.example.mbenben.studydemo.basenote.pattern;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/9/7.
 */

public class PatternActivity extends AppCompatActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_content_1)
    ClickableTextView tvContent1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        ButterKnife.bind(this);

        SpannableString spannableString = new SpannableString("This Just to clear this up once and for all,this was not a mistake");
        String regex = "((?i)this)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("This Just to clear this up once and for all,this was not a mistake");
        while (matcher.find()) {

            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        tvContent.setText(spannableString);
        tvContent1.setText("First you hate 'em, then you get used to 'em. Enough time passes, gets so you depend on them. That's institutionalized.");
        tvContent1.setHighlightTextClickListener(new ClickableTextView.HighLightTextClickListener() {
            @Override
            public void onHighlightTextClick(String text) {
                ToastUtils.showShort(text+"被点击");
            }
        });
    }
}
