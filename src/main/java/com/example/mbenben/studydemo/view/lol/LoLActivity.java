package com.example.mbenben.studydemo.view.lol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/1/3.
 */

public class LoLActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private LoLPolygonsview mv;
    private SeekBar sb1, sb2, sb3, sb4, sb5, sb6, sb7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol);
        initView();
    }

    private void initView() {
        mv = (LoLPolygonsview) findViewById(R.id.mv);
        sb1 = (SeekBar) findViewById(R.id.sb1);
        sb1.setOnSeekBarChangeListener(this);

        sb2 = (SeekBar) findViewById(R.id.sb2);
        sb2.setOnSeekBarChangeListener(this);

        sb3 = (SeekBar) findViewById(R.id.sb3);

        sb3.setOnSeekBarChangeListener(this);
        sb4 = (SeekBar) findViewById(R.id.sb4);

        sb4.setOnSeekBarChangeListener(this);
        sb5 = (SeekBar) findViewById(R.id.sb5);

        sb5.setOnSeekBarChangeListener(this);
        sb6 = (SeekBar) findViewById(R.id.sb6);

        sb6.setOnSeekBarChangeListener(this);
        sb7 = (SeekBar) findViewById(R.id.sb7);

        sb7.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        float values = (float) (seekBar.getProgress() / 10.0);
        switch (seekBar.getId()) {
            case R.id.sb1:
                mv.setValue1(values);
                break;
            case R.id.sb2:
                mv.setValue2(values);
                break;
            case R.id.sb3:
                mv.setValue3(values);
                break;
            case R.id.sb4:
                mv.setValue4(values);
                break;
            case R.id.sb5:
                mv.setValue5(values);
                break;
            case R.id.sb6:
                mv.setValue6(values);
                break;
            case R.id.sb7:
                mv.setValue7(values);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {


    }
}
