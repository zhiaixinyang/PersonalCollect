package com.example.mbenben.studydemo.layout.ricktext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.SmileUtils;
import com.example.mbenben.studydemo.utils.TextCommonUtils;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.example.mbenben.studydemo.layout.ricktext.emoji.view.EditTextAtUtils;
import com.example.mbenben.studydemo.layout.ricktext.emoji.view.EditTextEmoji;
import com.example.mbenben.studydemo.layout.ricktext.emoji.view.EmojiLayout;
import com.example.mbenben.studydemo.layout.ricktext.listener.EditTextAtUtilJumpListener;
import com.example.mbenben.studydemo.layout.ricktext.listener.SpanAtUserCallBack;
import com.example.mbenben.studydemo.layout.ricktext.listener.SpanUrlCallBack;
import com.example.mbenben.studydemo.layout.ricktext.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MBENBEN on 2017/1/15.
 *
 * 原作者项目GitHub：https://github.com/CarGuo/RickText
 */

public class RickTextActivity extends AppCompatActivity{
    public final static int REQUEST_USER_CODE_INPUT = 1111;
    public final static int REQUEST_USER_CODE_CLICK = 2222;

    @BindView(R.id.emoji_edit_text)
    EditTextEmoji emojiEditText;
    @BindView(R.id.emoji_show_bottom)
    ImageView emojiShowBottom;
    @BindView(R.id.emojiLayout)
    EmojiLayout emojiLayout;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.emoji_show_at)
    ImageView emojiShowAt;

    EditTextAtUtils editTextAtUtils;

    List<String> userNames = new ArrayList<>();
    List<String> userIds = new ArrayList<>();
    @BindView(R.id.rich_text)
    TextView richText;

    private String insertContent = "这是测试文本哟 www.baidu.com " +
            " 来@某个人  @22222 @kkk " +
            " 好的,来几个表情[e2][e4][e55]，最后来一个电话 13245685478";
    private List<UserModel> nameList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEmoji();
        setContentView(R.layout.activity_ricktext);
        ButterKnife.bind(this);
        initView();
    }
    /**
     * 处理自己的表情
     */
    private void initEmoji() {
        List<Integer> data = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        for (int i = 1; i < 64; i++) {
            int resId = getResources().getIdentifier("e" + i, "drawable", getPackageName());
            data.add(resId);
            strings.add("[e" + i + "]");
        }
        /**初始化为自己的**/
        SmileUtils.addPatternAll(SmileUtils.getEmoticons(), strings, data);
    }

    private void initView() {
        emojiLayout.setEditTextSmile(emojiEditText);
        editTextAtUtils = new EditTextAtUtils(emojiEditText, userNames, userIds);
        editTextAtUtils.setEditTextAtUtilJumpListener(new EditTextAtUtilJumpListener() {
            @Override
            public void notifyAt() {
                Intent intent = new Intent(RickTextActivity.this, UserListActivity.class);
                startActivityForResult(intent, RickTextActivity.REQUEST_USER_CODE_INPUT);
            }
        });

        resolveRichShow();
    }


    private void resolveRichShow() {
        String content = "这是测试文本哟 www.baidu.com " +
                "\n来@某个人  @22222 @kkk " +
                "\n好的,来几个表情[e2][e4][e55]，最后来一个电话 13245685478";

        UserModel userModel = new UserModel();
        userModel.setUser_name("22222");
        userModel.setUser_id("2222");
        nameList.add(userModel);
        userModel = new UserModel();
        userModel.setUser_name("kkk");
        userModel.setUser_id("23333");
        nameList.add(userModel);

        SpanUrlCallBack spanUrlCallBack = new SpanUrlCallBack() {
            @Override
            public void phone(String phone) {
                ToastUtils.showShort(phone+ " 被点击了");
            }

            @Override
            public void url(String url) {
                ToastUtils.showShort(url+ " 被点击了");
            }
        };

        SpanAtUserCallBack spanAtUserCallBack = new SpanAtUserCallBack() {
            @Override
            public void onClick(UserModel userModel1) {
                ToastUtils.showShort(userModel1.getUser_name() + " 被点击了");
            }
        };

        richText.setText(TextCommonUtils.getUrlSmileText(this, content, nameList, richText, Color.BLUE, true, spanAtUserCallBack, spanUrlCallBack));
    }

    @OnClick({R.id.emoji_show_bottom, R.id.emoji_show_at, R.id.insert_text_btn, R.id.jump_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emoji_show_bottom:
                emojiLayout.hideKeyboard();
                if (emojiLayout.getVisibility() == View.VISIBLE) {
                    emojiLayout.setVisibility(View.GONE);
                } else {
                    emojiLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.emoji_show_at:
                Intent intent = new Intent(RickTextActivity.this, UserListActivity.class);
                startActivityForResult(intent, RickTextActivity.REQUEST_USER_CODE_CLICK);
                break;
            case R.id.insert_text_btn:
                userIds.clear();
                userNames.clear();
                for (int i = 0; i < nameList.size(); i++) {
                    userNames.add(nameList.get(i).getUser_name());
                    userIds.add(nameList.get(i).getUser_id());
                }
                EditTextAtUtils.resolveInsertText(RickTextActivity.this, insertContent, nameList, "#f77521", emojiEditText);
                break;
            case R.id.jump_btn:
                Intent intent_ = new Intent(RickTextActivity.this, NewEmojiActivity.class);
                startActivity(intent_);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_USER_CODE_CLICK:
                    EditTextAtUtils.resolveAtResult(editTextAtUtils, "#f77500", (UserModel) data.getSerializableExtra(UserListActivity.DATA));
                    break;
                case REQUEST_USER_CODE_INPUT:
                    EditTextAtUtils.resolveAtResultByEnterAt(emojiEditText, editTextAtUtils, "#f77500", (UserModel) data.getSerializableExtra(UserListActivity.DATA));
                    break;
            }
        }

    }
}
