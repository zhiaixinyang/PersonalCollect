package com.example.mbenben.studydemo.view.calendar;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DecimalFormatUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MDove on 2017/10/9.
 */
public class CalendarAdapter extends BaseAdapter {


    private List<CalendarEntity> calendarEntityList = new ArrayList<>();

    private Context context;

    private int selectDay = -1;

    private int currentDay = -1;

    public CalendarAdapter(Context context) {
        this.context = context;
    }

    public void setSelectDay(int selectDay) {
        this.selectDay = selectDay;
        notifyDataSetChanged();

    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
        notifyDataSetChanged();
    }

    public void setCalendarEntityList(List<CalendarEntity> calendarEntityList) {
        this.calendarEntityList.clear();
        this.calendarEntityList.addAll(calendarEntityList);
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return calendarEntityList.size();
    }

    @Override
    public boolean isEnabled(int position) {
        CalendarEntity calendarEntity = calendarEntityList.get(position);
        if (calendarEntity.isCurrentMonth()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_calendar_statistics, null);
            holder = new ViewHolder();
            holder.day = (TextView) convertView.findViewById(R.id.date);
            holder.incomeView = (TextView) convertView.findViewById(R.id.income);
            holder.expenseView = (TextView) convertView.findViewById(R.id.expense);
            holder.currentDayView = (ImageView) convertView.findViewById(R.id.calendarSelect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CalendarEntity calendarEntity = calendarEntityList.get(position);
        holder.day.setText(calendarEntity.getDay() + "");
        if (!DecimalFormatUtil.isZero(calendarEntity.getIncome())) {
            holder.incomeView.setVisibility(View.VISIBLE);
            holder.incomeView.setText("+" + DecimalFormatUtil.getSeparatorDecimalStr(calendarEntity.getIncome()));
        } else {
            holder.incomeView.setVisibility(View.INVISIBLE);
        }
        if (!DecimalFormatUtil.isZero(calendarEntity.getExpense())) {
            holder.expenseView.setVisibility(View.VISIBLE);
            holder.expenseView.setText("-" + DecimalFormatUtil.getSeparatorDecimalStr(calendarEntity.getExpense()));
        } else {
            holder.expenseView.setVisibility(View.INVISIBLE);
        }


        if (calendarEntity.isCurrentMonth() && calendarEntity.getDay() == selectDay) {
            convertView.setBackgroundColor(Color.parseColor("#FFE6BD"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (calendarEntity.isCurrentMonth() && calendarEntity.getDay() == currentDay) {
            holder.currentDayView.setVisibility(View.VISIBLE);
        } else {
            holder.currentDayView.setVisibility(View.INVISIBLE);
        }
        if (calendarEntity.isCurrentMonth()) {
            holder.day.setTextColor(context.getResources().getColor(R.color.color_1D1D26));

        } else {
            holder.day.setTextColor(context.getResources().getColor(R.color.color_959595));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView day;
        TextView incomeView;
        TextView expenseView;
        ImageView currentDayView;
    }
}
