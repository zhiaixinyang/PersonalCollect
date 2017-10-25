package com.example.mbenben.studydemo.view.misportsconnectview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/10/25.
 */

public class MiSportViewActivity extends AppCompatActivity{
    private Handler handler;
    boolean connect = false;

    @BindView(R.id.connect_button)
    Button connectButton;
    @BindView(R.id.mi_sports_loading_view)
    MISportsConnectView miSportsConnectView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_sport);
        ButterKnife.bind(this);

        SportsData sportsData = new SportsData();
        sportsData.step = 2714;
        sportsData.distance = 1700;
        sportsData.calories = 34;
        sportsData.progress = 75;
        miSportsConnectView.setSportsData(sportsData);

        handler = new Handler();
        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connect = !connect;
                        miSportsConnectView.setConnected(connect);
                        connectButton.setText(connect? getString(R.string.disconnect) : getString(R.string.connect));
                    }
                }, 500);
            }
        });
    }
}
