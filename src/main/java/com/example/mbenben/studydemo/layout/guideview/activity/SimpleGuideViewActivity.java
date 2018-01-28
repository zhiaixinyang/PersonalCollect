package com.example.mbenben.studydemo.layout.guideview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.guideview.Component;
import com.example.mbenben.studydemo.layout.guideview.Guide;
import com.example.mbenben.studydemo.layout.guideview.GuideBuilder;
import com.example.mbenben.studydemo.layout.guideview.component.MutiComponent;
import com.example.mbenben.studydemo.layout.guideview.component.SimpleComponent;

public class SimpleGuideViewActivity extends Activity {

  private Button header_imgbtn;
  private LinearLayout ll_nearby;
  Guide guide;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_guide_view);
    header_imgbtn = (Button) findViewById(R.id.header_imgbtn);
    header_imgbtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(SimpleGuideViewActivity.this, "show", Toast.LENGTH_SHORT).show();
      }
    });
    ll_nearby = (LinearLayout) findViewById(R.id.ll_nearby);
    header_imgbtn.post(new Runnable() {
      @Override public void run() {
        showGuideView();
      }
    });
  }

  public void showGuideView() {
    GuideBuilder builder = new GuideBuilder();
    builder.setTargetView(header_imgbtn)
        .setAlpha(150)
        .setHighTargetCorner(20)
        .setHighTargetPadding(10)
        .setOverlayTarget(false)
        .setOutsideTouchable(false);
    builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
      @Override public void onShown() {
      }

      @Override public void onDismiss() {
        showGuideView2();
      }
    });

    builder.addComponent(new SimpleComponent());
    guide = builder.createGuide();
    guide.setShouldCheckLocInWindow(false);
    guide.show(SimpleGuideViewActivity.this);
  }

  public void showGuideView2() {
    final GuideBuilder builder1 = new GuideBuilder();
    builder1.setTargetView(ll_nearby)
        .setAlpha(150)
        .setHighTargetGraphStyle(Component.CIRCLE)
        .setOverlayTarget(false)
        .setExitAnimationId(android.R.anim.fade_out)
        .setOutsideTouchable(false);
    builder1.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
      @Override public void onShown() {
      }

      @Override public void onDismiss() {
      }
    });

    builder1.addComponent(new MutiComponent());
    Guide guide = builder1.createGuide();
    guide.setShouldCheckLocInWindow(false);
     guide.show(SimpleGuideViewActivity.this);
  }
}
