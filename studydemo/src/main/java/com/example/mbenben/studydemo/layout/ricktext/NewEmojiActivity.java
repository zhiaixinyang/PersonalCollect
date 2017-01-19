package com.example.mbenben.studydemo.layout.ricktext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.ricktext.emoji.view.EmojiLayout2;
import com.example.mbenben.studydemo.utils.SmileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 原作者项目GitHub：https://github.com/CarGuo/RickText
 */
public class NewEmojiActivity extends AppCompatActivity {

    @BindView(R.id.emoji_edit_text2)
    EditText editText;
    @BindView(R.id.emoji_show_bottom)
    ImageView emojiShowBottom;
    @BindView(R.id.emojiLayout2)
    EmojiLayout2 emojiLayout2;
    @BindView(R.id.activity_new_emoji)
    RelativeLayout activityNewEmoji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Integer> data = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        data.add(R.drawable.gxx1);
        strings.add("[测试1]");
        data.add(R.drawable.gxx2);
        strings.add("[测试2]");
        data.add(R.drawable.gxx3);
        strings.add("[测试3]");
        data.add(R.drawable.gxx4);
        strings.add("[测试4]");
        data.add(R.drawable.gxx5);
        strings.add("[测试5]");
        data.add(R.drawable.gxx6);
        strings.add("[测试8]");
        /**初始化为自己的**/
        SmileUtils.addPatternAll(SmileUtils.getEmoticons(), strings, data);


        setContentView(R.layout.activity_new_emoji);
        ButterKnife.bind(this);

        emojiLayout2.setEditTextSmile(editText);

    }


    @OnClick({R.id.emoji_show_bottom, R.id.source_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emoji_show_bottom:
                emojiLayout2.hideKeyboard();
                if (emojiLayout2.getVisibility() == View.VISIBLE) {
                    emojiLayout2.setVisibility(View.GONE);
                } else {
                    emojiLayout2.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.source_btn:
                String text = editText.getText().toString();
                Toast.makeText(NewEmojiActivity.this, text, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
