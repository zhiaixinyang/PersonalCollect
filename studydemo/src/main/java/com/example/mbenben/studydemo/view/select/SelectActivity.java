package com.example.mbenben.studydemo.view.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.select.flowlayout.MyFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2017/1/3.
 */

public class SelectActivity extends AppCompatActivity implements SelectView.getNumberListener{
    private SelectView selectView;
    private MyFlowLayout myFlowLayout;
    private List<String> stringList = new ArrayList<>();
    private TextView numberTxt;
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

        stringList.add("数据库");
        stringList.add("移动开发");
        stringList.add("前端开发");
        stringList.add("微信小程序");
        stringList.add("服务器开发");
        stringList.add("PHP");
        stringList.add("人工智能");
        stringList.add("大数据");
        selectView = (SelectView) findViewById(R.id.my_selectview);
        myFlowLayout = (MyFlowLayout) findViewById(R.id.my_flowlayout);
        for (String textView : stringList) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 40, 40, 40);
            TextView showText = new TextView(this);
            showText.setLayoutParams(params);
            showText.setTextSize(20);
            showText.setText(textView);
            myFlowLayout.addView(showText);
        }
        numberTxt = (TextView) findViewById(R.id.number_txt);
        selectView.setListener(this);
    }
    @Override
    public void getNumber(int number) {
        numberTxt.setText("选择的数字为:" + number);
    }
}
