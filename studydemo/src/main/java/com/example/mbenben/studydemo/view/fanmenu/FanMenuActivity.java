package com.example.mbenben.studydemo.view.fanmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;
import com.example.mbenben.studydemo.view.fanmenu.view.FanMenuChart;
import com.example.mbenben.studydemo.view.fanmenu.view.PieData;

import java.util.ArrayList;

public class FanMenuActivity extends AppCompatActivity {

    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    // 颜色表
    private int[] mColors = {0xffff6944, 0xff63ff9e, 0xff38474e, 0xff5effdd, 0xffb579ff, 0xfff4ea2a};
    private int[] pieLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_menu);

        initData();
        final FanMenuChart fanMenuChart = (FanMenuChart) findViewById(R.id.menuChart);
        fanMenuChart.setPieData(mPieDatas);
        fanMenuChart.setStartAngle(180);  //设置起始角度
        fanMenuChart.setPieShowAngle(180);//设置总共角度
        fanMenuChart.setCenterBitmap(R.drawable.menu, DpUtils.dp2px(60),DpUtils.dp2px(60));
        fanMenuChart.setTouchCenterTextSize(30);
        pieLocation = new int[2];
        findViewById(R.id.btn_click).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (fanMenuChart.getVisibility() == View.GONE) {
                            fanMenuChart.showStartAnim();
                        } else {
                            fanMenuChart.showEndAnim();
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:

                        fanMenuChart.getLocationOnScreen(pieLocation);
                        fanMenuChart.onPieTouchEvent(event, event.getRawX() - pieLocation[0], event.getRawY() - pieLocation[1]);

                        return true;
                }


                return false;
            }
        });


    }


    private void initData() {
        PieData pieData = new PieData();
        pieData.setName("不辣，川菜一点都不辣 T_T");
        pieData.setWeight(1);
        pieData.setName_label("CHUAN");
        pieData.setLabelColor(0xffd81e06);
        pieData.setDrawableId(R.drawable.btm_chuan);
        mPieDatas.add(pieData);


        PieData pieData2 = new PieData();
        pieData2.setName("咸，鲜，清淡");
        pieData2.setWeight(1);
        pieData2.setName_label("YUE");
        pieData2.setDrawableId(R.drawable.btm_yue);
        pieData2.setLabelColor(0xff90f895);
        mPieDatas.add(pieData2);


        PieData pieData5 = new PieData();
        pieData5.setName("重油盐辣，腊味");
        pieData5.setWeight(1);
        pieData5.setName_label("XIANG");
        pieData5.setDrawableId(R.drawable.btm_xiang);
        pieData5.setLabelColor(0xffe16531);
        mPieDatas.add(pieData5);

        PieData pieData4 = new PieData();
        pieData4.setName("咸甜");
        pieData4.setWeight(1);
        pieData4.setName_label("MIN");
        pieData4.setDrawableId(R.drawable.btm_min);
        pieData4.setLabelColor(0xfff5c9cb);
        mPieDatas.add(pieData4);


        PieData pieData3 = new PieData();
        pieData3.setName("甜，黄酒味");
        pieData3.setName_label("SU");
        pieData3.setWeight(1);
        pieData3.setDrawableId(R.drawable.btm_jiang);
        pieData3.setLabelColor(0xfff4ed61);
        mPieDatas.add(pieData3);

        PieData pieData6 = new PieData();
        pieData6.setName("鲜，浓油赤酱");
        pieData6.setWeight(1);
        pieData6.setName_label("LU");
        pieData6.setDrawableId(R.drawable.btm_lu);
        pieData6.setLabelColor(0xff944a48);
        mPieDatas.add(pieData6);

    }


    // 闽 f5c9cb  川d81e06  粤90f895    鲁90f895  苏#f4ed61   湘菜#e16531
}
