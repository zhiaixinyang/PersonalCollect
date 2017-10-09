package com.example.mbenben.studydemo.view.calendar;

import java.util.Date;

/**
 * Created by MDove on 2017/10/9.
 */
public class CalendarEntity {
    private int year;
    private int month;
    private int day;
    private double income;
    private double expense;
    private boolean isCurrentMonth;
    private boolean isToday;
    private Date mDate;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "CalendarEntity{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", mDate=" + mDate +
                '}';
    }
}
