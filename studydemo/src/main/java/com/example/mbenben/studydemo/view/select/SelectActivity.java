package com.example.mbenben.studydemo.view.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.select.flowlayout.MyFlowLayout;
import com.example.mbenben.studydemo.view.select.select.BooheeRuler;
import com.example.mbenben.studydemo.view.select.select.KgNumberLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/1/3.
 */

public class SelectActivity extends AppCompatActivity implements SelectView.getNumberListener{
    private SelectView selectView;
    private List<String> stringList = new ArrayList<>();
    private TextView numberTxt;
    private BooheeRuler mBooheeRuler;
    private KgNumberLayout mKgNumberLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectView.setmStartWidth(0);
    }

    public void init() {
        selectView = (SelectView) findViewById(R.id.my_selectview);
        mBooheeRuler = (BooheeRuler) findViewById(R.id.br);
        mKgNumberLayout = (KgNumberLayout) findViewById(R.id.knl);
        numberTxt = (TextView) findViewById(R.id.number_txt);

        mKgNumberLayout.bindRuler(mBooheeRuler);
        selectView.setListener(this);
    }
    @Override
    public void getNumber(int number) {
        numberTxt.setText("选择的数字为:" + number);
    }
}
