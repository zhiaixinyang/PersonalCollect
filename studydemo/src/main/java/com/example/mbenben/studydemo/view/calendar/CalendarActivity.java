package com.example.mbenben.studydemo.view.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/10/9.
 */
public class CalendarActivity extends AppCompatActivity implements CustomCalendarView.SelectDayListener {


    @BindView(R.id.customCalendar)
    CustomCalendarView calendarView;
    @BindView(R.id.tv_title)
    TextView titleView;

    private List<Object> listItems = new ArrayList<>();

    private Calendar oldExpenseCalendar = Calendar.getInstance();
    private Calendar currentCalendar;
    private String bookId;
    private String userId;

    private double mStartX;
    private double mEndX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_statistics);
        ButterKnife.bind(this);
        init();
    }




    public void init() {
        currentCalendar = Calendar.getInstance();
        titleView.setText(DateUtils.getYear() + "年" + DateUtils.getMonth() + "月");
        calendarView.setSelectDayListener(this);
        setListViewDate(userId, Calendar.getInstance());
        calendarView.updateDate();

        calendarView.calendarView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        mEndX = event.getX();
                        if (mStartX < mEndX - 300) {
                            updateTime(-1);
                        }
                        if (mStartX > mEndX + 300) {
                            updateTime(1);
                        }
                        break;
                }
                return false;
            }
        });

    }

    private void updateTime(int offset) {
        currentCalendar.add(Calendar.MONTH, offset);
    }

    private void setListViewDate(String userId, Calendar calendar) {
        DateUtils.formatCalendar(calendar);
        long startTime = calendar.getTimeInMillis();
        long endTime = calendar.getTimeInMillis() + DateUtils.getLongTimeofDay() - 1;
        oldExpenseCalendar.setTimeInMillis(0);
    }

    @Override
    public void selectMonthDay(Date date, int selectDay) {
        currentCalendar.setTime(date);
        currentCalendar.set(Calendar.DAY_OF_MONTH, selectDay);
        setListViewDate(userId, currentCalendar);
    }
}
