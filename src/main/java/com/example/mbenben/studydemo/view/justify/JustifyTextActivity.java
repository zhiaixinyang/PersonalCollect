package com.example.mbenben.studydemo.view.justify;

import java.util.Locale;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.justify.view.NotNull;


public class JustifyTextActivity extends FragmentActivity {

  private Typeface mTypeface = null;

  public Typeface getTypeface() {
    return mTypeface;
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      mTypeface = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
    }
    catch (final Exception ignore) {}
    setContentView(R.layout.activity_justify_textview);
    final MyViewPager viewPager = (MyViewPager)findViewById(R.id.pager);
    if (viewPager != null) {
      viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
    }
  }

  public void closeHelp(final @NotNull View v) {
    findViewById(R.id.help).setVisibility(View.GONE);
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(final @NotNull FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
      return JustifyFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
      Locale l = Locale.getDefault();
      switch (position) {
        case 0:
          return getString(R.string.title_section1).toUpperCase(l);
        case 1:
          return getString(R.string.title_section2).toUpperCase(l);
      }
      return null;
    }
  }

  public static class JustifyFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int[] LAYOUT_RES_IDS = new int[] {
      R.layout.frag_justfy_example1,
      R.layout.frag_justfy_example2,
    };

    public static JustifyFragment newInstance(final int sectionNumber) {
      final JustifyFragment fragment = new JustifyFragment();
      final Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(final @NotNull LayoutInflater inflater,
                             final @NotNull ViewGroup container,
                             final Bundle savedInstanceState) {
      final int layoutResId = LAYOUT_RES_IDS[getArguments().getInt(ARG_SECTION_NUMBER)];
      return inflater.inflate(layoutResId, container, false);
    }

  }

}
