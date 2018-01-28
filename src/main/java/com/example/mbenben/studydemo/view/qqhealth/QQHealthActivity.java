package com.example.mbenben.studydemo.view.qqhealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

public class QQHealthActivity extends AppCompatActivity {

    private QQHealthView view;
    public static List<Integer> sizes = new ArrayList<>();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqhealth);
        initview();
    }

    private void initview() {
        view = (QQHealthView) findViewById(R.id.myQQView);
        view.setMySize(2345);
        view.setRank(11);
        view.setAverageSize(5436);
        sizes.add(1234);
        sizes.add(2234);
        sizes.add(4234);
        sizes.add(6234);
        sizes.add(3834);
        sizes.add(7234);
        sizes.add(5436);
        btn = (Button) findViewById(R.id.set_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.reSet(6534, 8, 4567);
            }
        });


        int iv_image = R.id.iv_image;

   }
}
