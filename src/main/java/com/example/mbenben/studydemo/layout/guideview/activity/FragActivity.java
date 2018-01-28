package com.example.mbenben.studydemo.layout.guideview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.mbenben.studydemo.R;

public class FragActivity extends FragmentActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide_view_frag);
    switch (getIntent().getIntExtra("fragmentId", -1)) {
      case 0:
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new MyFragment()).commit();
        break;
      case 1:
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new MyFragment2()).commit();
        break;
      default:
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new MyFragment()).commit();
    }
  }
}
