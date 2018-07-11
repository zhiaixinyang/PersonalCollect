package com.example.mbenben.studydemo.anim.voiceAnim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.jbox.JBoxDemoActivity;

/**
 * 原项目GitHub：https://github.com/ChestnutPlus/ModulesUi
 */
public class VoiceAnimActivity extends BaseActivity {

    private VoiceAnimView voiceAnimView;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, VoiceAnimActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_anim);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        voiceAnimView = (VoiceAnimView) findViewById(R.id.voice_view);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceAnimView.startAnim();

            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceAnimView.stopAnim();
            }
        });
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        voiceAnimView.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        voiceAnimView.stopAnim();
    }
}
