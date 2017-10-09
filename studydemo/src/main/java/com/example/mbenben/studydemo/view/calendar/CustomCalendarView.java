package com.example.mbenben.studydemo.view.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/10/9.
 */
public class CustomCalendarView extends RelativeLayout {

    @BindView(R.id.calendar)
    public GridView calendarView;

    private CalendarAdapter calendarAdapter;

    private Date date;

    private SelectDayListener selectDayListener;

    private List<CalendarEntity> calendarEntities;

    public interface SelectDayListener {
        public void selectMonthDay(Date date, int selectDay);
    }

    public CustomCalendarView(Context context) {
        this(context, null);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_custom_calendar, this, true);
        ButterKnife.bind(this);
        init();
    }


    public void setSelectDayListener(SelectDayListener selectDayListener) {
        this.selectDayListener = selectDayListener;
    }


    public void init() {
        date = new Date();
        calendarAdapter = new CalendarAdapter(getContext());
        calendarView.setAdapter(calendarAdapter);
        addGridView();
    }

    public void updateDate() {
        calendarEntities = getCalendarEntities(getContext(), date);
        calendarAdapter.setCalendarEntityList(calendarEntities);
        calendarAdapter.notifyDataSetChanged();
    }

    private List<CalendarEntity> getCalendarEntities(Context context, Date date) {
        Calendar now = Calendar.getInstance();
        List<CalendarEntity> list = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        fullPreMonth(date, list);
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            CalendarEntity calendarEntity = new CalendarEntity();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            calendar1.set(Calendar.DATE, i);
            calendarEntity.setDate(calendar1.getTime());
            calendarEntity.setYear(calendar.get(Calendar.YEAR));
            calendarEntity.setMonth(calendar.get(Calendar.MONTH));
            calendarEntity.setDay(i);
            calendarEntity.setCurrentMonth(true);
            if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && i == now.get(Calendar.DAY_OF_MONTH)) {
                calendarEntity.setToday(true);
            } else {
                calendarEntity.setToday(false);
            }
            list.add(calendarEntity);
        }
        fullNextMonth(date, list);
        return list;
    }

    private static void fullPreMonth(Date date, List<CalendarEntity> calendarEntities) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = getFirstDayOfWeek(date);
        if (dayOfWeek != 0) {
            calendar.add(Calendar.MONTH, -1);
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
                CalendarEntity calendarEntity = new CalendarEntity();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                calendar1.set(Calendar.DATE, maxDay - i);
                calendarEntity.setDate(calendar1.getTime());
                calendarEntity.setYear(calendar.get(Calendar.YEAR));
                calendarEntity.setMonth(calendar.get(Calendar.MONTH));
                calendarEntity.setDay(maxDay - i);
                calendarEntities.add(calendarEntity);
            }
        }

    }

    private static void fullNextMonth(Date date, List<CalendarEntity> calendarEntities) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SUNDAY) {
            calendar.add(Calendar.MONTH, 1);
            for (int i = 1; i <= (7 - dayOfWeek) + 1; i++) {
                CalendarEntity calendarEntity = new CalendarEntity();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                calendar1.set(Calendar.DATE, i);
                calendarEntity.setDate(calendar1.getTime());
                calendarEntity.setYear(calendar.get(Calendar.YEAR));
                calendarEntity.setMonth(calendar.get(Calendar.MONTH));
                calendarEntity.setDay(i);
                calendarEntities.add(calendarEntity);
            }
        }

    }

    public static int getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (firstDayOfWeek > 1) {
            return firstDayOfWeek - 1;
        } else {
            return 7;
        }
    }

    private void setSelectMonthDay(int monthDay) {
        calendarAdapter.setSelectDay(monthDay);
        if (selectDayListener != null) {
            selectDayListener.selectMonthDay(date, monthDay);
        }
    }

    private void addGridView() {
        calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                CalendarEntity calendarEntity = calendarEntities.get(position);
                setSelectMonthDay(calendarEntity.getDay());
            }
        });
    }

}
