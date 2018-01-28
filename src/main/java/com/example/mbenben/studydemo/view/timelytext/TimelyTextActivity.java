package com.example.mbenben.studydemo.view.timelytext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.timelytext.view.TimelyView;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/13.
 */

public class TimelyTextActivity extends AppCompatActivity {
    @BindView(R.id.textView1)
    TimelyView timelyView     = null;
    @BindView(R.id.seekBar) SeekBar seekBar        = null;
    @BindView(R.id.fromSpinner) Spinner        fromSpinner    = null;
    @BindView(R.id.toSpinner) Spinner toSpinner      = null;
    private volatile ObjectAnimator objectAnimator = null;
    public static final int            DURATION       = 1000;
    public static final int            NO_VALUE       = -1;

    private volatile int from = NO_VALUE;
    private volatile int to   = NO_VALUE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timely_text);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.from_numbers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from = position - 1;
                if(from != NO_VALUE && to != NO_VALUE) {
                    objectAnimator = timelyView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                } else {
                    objectAnimator = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to = position - 1;
                if(from != NO_VALUE && to != NO_VALUE) {
                    objectAnimator = timelyView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                } else {
                    objectAnimator = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seekBar.setMax(DURATION);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(objectAnimator != null) objectAnimator.setCurrentPlayTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
