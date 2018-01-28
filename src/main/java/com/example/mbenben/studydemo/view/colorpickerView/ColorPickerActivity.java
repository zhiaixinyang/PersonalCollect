package com.example.mbenben.studydemo.view.colorpickerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/9/9.
 */

public class ColorPickerActivity extends AppCompatActivity {
    @BindView(R.id.colorPickerView)
    ColorPickerView colorPickerView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private boolean FLAG_PALETTE = false;
    private boolean FLAG_SELECTOR = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        ButterKnife.bind(this);

        colorPickerView.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                setLayoutColor(color);
            }
        });
    }

    /**
     * set layout color & textView html code
     *
     * @param color
     */
    private void setLayoutColor(int color) {
        textView.setText("#" + colorPickerView.getColorHtml());
        linearLayout.setBackgroundColor(color);
    }

    /**
     * change palette drawable resource
     * you must initialize at first in xml
     *
     * @param v
     */
    public void palette(View v) {
        if (FLAG_PALETTE)
            colorPickerView.setPaletteDrawable(ContextCompat.getDrawable(this, R.drawable.palette));
        else
            colorPickerView.setPaletteDrawable(ContextCompat.getDrawable(this, R.drawable.palettebar));
        FLAG_PALETTE = !FLAG_PALETTE;
    }

    /**
     * change selector drawable resource
     * you must initialize at first in xml
     *
     * @param v
     */
    public void selector(View v) {
        if (FLAG_SELECTOR)
            colorPickerView.setSelectorDrawable(ContextCompat.getDrawable(this, R.drawable.wheel));
        else
            colorPickerView.setSelectorDrawable(ContextCompat.getDrawable(this, R.drawable.wheel_dark));
        FLAG_SELECTOR = !FLAG_SELECTOR;
    }

    /**
     * moving selector's points (x, y)
     *
     * @param v
     */
    public void points(View v) {
        int x = (int) (Math.random() * 600) + 100;
        int y = (int) (Math.random() * 400) + 150;
        colorPickerView.setSelectorPoint(x, y);
    }
}
