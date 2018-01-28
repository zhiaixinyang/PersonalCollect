package com.example.mbenben.studydemo.layout.overscroll.activity.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.overscroll.view.OverScrollDecoratorHelper;

/**
 * @author amitd
 */
public class MiscViewsDemoFragment extends Fragment {

    private static final String CHRONO_TIME_SAVE_ID = "chronoTime";

    private Chronometer mChrono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.misc_overscroll_demo, null, false);

        View textView = fragmentView.findViewById(R.id.demo_text);
        OverScrollDecoratorHelper.setUpStaticOverScroll(textView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        View imageView = fragmentView.findViewById(R.id.demo_image);
        OverScrollDecoratorHelper.setUpStaticOverScroll(imageView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        mChrono = (Chronometer) fragmentView.findViewById(R.id.demo_chronometer);
        if (savedInstanceState != null) {
            mChrono.setBase(savedInstanceState.getLong(CHRONO_TIME_SAVE_ID));
        }
        OverScrollDecoratorHelper.setUpStaticOverScroll(mChrono, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
        mChrono.start();

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(CHRONO_TIME_SAVE_ID, mChrono.getBase());

        super.onSaveInstanceState(outState);
    }
}
