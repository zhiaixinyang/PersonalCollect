package com.example.mbenben.studydemo.view.justify.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.mbenben.studydemo.view.justify.JustifyTextActivity;


public class MyJustifiedEditText extends JustifiedEditText {

  public MyJustifiedEditText(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      final JustifyTextActivity activity = (JustifyTextActivity)context;
      setTypeface(activity.getTypeface());
    }
  }

}
