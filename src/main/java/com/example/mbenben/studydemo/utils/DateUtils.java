package com.example.mbenben.studydemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MDove on 2017/10/9.
 */
public class DateUtils {
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;

    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd ");
        time *= 1000;
        Date date = new Date(time);
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();    //昨天
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        current.setTime(date);
        if (current.after(today) && (current.getTimeInMillis() - today.getTimeInMillis()) < getLongTimeofDay()) {
            return "今天";
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天";
        } else {
            return format.format(date);
        }


    }


    public static String getDateHHMM(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        time *= 1000;
        Date date = new Date(time);
        return format.format(date);

    }

    public static boolean isInDay(long time) {
        long current = System.currentTimeMillis();
        if (current - time <= DAY_IN_MILLIS && current - time > 0) {
            return true;
        }
        return false;
    }

    static SimpleDateFormat formatHM = new SimpleDateFormat("HH:mm");

    public static String getDateHM(long time) {
        return formatHM.format(time);
    }

    public static String getRecorderDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }


    public static String getPersonalExpenseDetailDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getPointDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        time *= 1000;
        Date date = new Date(time);
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();    //昨天
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        current.setTime(date);
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = current.get(Calendar.DAY_OF_WEEK);
        if (current.after(today) && (current.getTimeInMillis() - today.getTimeInMillis()) < getLongTimeofDay()) {
            return "今天 " + format.format(date) + dayNames[dayOfWeek - 1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + format.format(date) + dayNames[dayOfWeek - 1];
        } else {
            return format.format(date);
        }
    }


    public static String getGroupExpenseTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        time *= 1000;
        Date date = new Date(time);
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();    //昨天
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        current.setTime(date);
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = current.get(Calendar.DAY_OF_WEEK);
        if (current.after(today) && (current.getTimeInMillis() - today.getTimeInMillis()) < getLongTimeofDay()) {
            return "今天 " + getDateHHMM(time / 1000);
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + getDateHHMM(time / 1000);
        } else {
            return format.format(date);
        }
    }

    /**
     * get the day of week
     *
     * @param time
     * @return
     */
    public static String getDayOfWeek(long time) {
        String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String getPersonalDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        time *= 1000;
        Date date = new Date(time);
        return format.format(date);
    }


    public static String getyyyyMMdd(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        time *= 1000;
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getyyyyMMdd1(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        time *= 1000;
        Date date = new Date(time);
        String data = format.format(date);
        return data;
    }

    public static String getyyyyMMdd2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(time);
        String data = format.format(date);
        return data;
    }


    public static String getyyyyMMdd3(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String data = format.format(time);
        return data;
    }

    public static String getyyyyMMdd4(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String data = format.format(time);
        return data;
    }

    public static String getyyyyMMdd5(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String data = format.format(time);
        return data;
    }

    public static String getTrackTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(time);
        return data;
    }

    public static String getRecordeDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getMonthDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date date = new Date(time);
        return format.format(date);
    }


    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);

    }

    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;

    }


    public static int getYear(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.YEAR);

    }

    public static int getMonth(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH) + 1;

    }

    public static int getDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_MONTH);

    }

    public static String getFormatDate(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }


    /**
     * Format calendar at begin of the day
     *
     * @param calendar
     */
    public static void formatCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

    }

    /**
     * Format calendar at end of the day
     *
     * @param calendar
     */
    public static void formatCalendarEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

    }

    public static String getCreateExpenseDate(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }


    public static long getLongTime(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime().getTime();
    }

    public static long getLongTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime().getTime();
    }

    public static String getInfoTime(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date date = new Date(time);
        return format.format(date);
    }

    public static String getCreateExpenseDate2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }


    public static int getLastDayofMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static boolean getSameDay(long time1, long time2) {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(time1);
        calendar2.setTimeInMillis(time2);
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar1.get(calendar1.MONTH) == calendar2.get(Calendar.MONTH)) {
                return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
            }
        }
        return false;

    }


    public static boolean getSameMonth(long time1, long time2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(time1);
        calendar2.setTimeInMillis(time2);
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            return calendar1.get(calendar1.MONTH) == calendar2.get(Calendar.MONTH);
        }
        return false;

    }

    public static boolean getSameYear(long time1, long time2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(time1);
        calendar2.setTimeInMillis(time2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);

    }


    /**
     * 通过传入指定格式的日期,得到毫秒的时间值
     *
     * @param dateStr
     * @return
     */
    public static long getLongTime(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long time = 0;
        try {
            //把指定格式的时间字符串,转换为date对象
            Date date = simpleDateFormat.parse(dateStr);
            time = date.getTime();//毫秒
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    public static long getLongTimeofDay() {
        return 24 * 60 * 60 * 1000;
    }

    /**
     * 把毫秒值转换为指定格式的时间
     * 这里的格式是 10-01 MM-dd
     *
     * @param time
     * @return
     */
    public static String getMMDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(time);
    }

    private static SimpleDateFormat mmddSdf = new SimpleDateFormat("MM月dd日");

    public static String getMMDD2(long time) {
        return mmddSdf.format(time);
    }

    /**
     * Get the day of month,format is '1号' or '20号'
     *
     * @param time
     * @return
     */
    public static String getDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String result = sdf.format(time);
        if (Integer.parseInt(result) < 10) {
            result = result.substring(1);
        }
        return result + "号";
    }


    public static String getFormatMonth(int month) {
        if (month > 9) {
            return month + "";
        } else {
            return "0" + month;
        }
    }

    public static String getFormatDay(int day) {
        if (day > 9) {
            return String.valueOf(day);
        } else {
            return "0" + day;
        }
    }


    /**
     * 根据账单日获取当前月
     */
    public static int getMonthByMonthFirstDay(int firstDay) {
        long currentTime = System.currentTimeMillis();
        long offset = (firstDay - 1) * getLongTimeofDay();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime - offset);
        return cal.get(Calendar.MONTH) + 1;
    }


    public static int getMonthByMonthFirstDay(long time, int firstDay) {

        long offset = (firstDay - 1) * getLongTimeofDay();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time - offset);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据账单日获取当前年
     */
    public static int getYearByMonthFirstDay(int firstDay) {
        long currentTime = System.currentTimeMillis();
        long offset = (firstDay - 1) * getLongTimeofDay();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime - offset);
        return cal.get(Calendar.YEAR);
    }

    public static int getYearByMonthFirstDay(long time, int firstDay) {
        long offset = (firstDay - 1) * getLongTimeofDay();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time - offset);
        return cal.get(Calendar.YEAR);
    }


    public static String timetodate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        return d;
    }

    /**
     * 获取当前月的最小long时间
     *
     * @return
     */
    public static long getMinOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前月的最大long时间
     *
     * @return
     */
    public static long getMaxOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当前月的最小long时间
     *
     * @return
     */
    public static long getMinOfMonth(int monthoffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MONTH, monthoffset);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前月的最大long时间
     *
     * @return
     */
    public static long getMaxOfMonth(int monthoffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MONTH, monthoffset);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当前周的最大long时间
     *
     * @return
     */
    public static long getMaxOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        calendar.roll(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前周的最小long时间
     *
     * @return
     */
    public static long getMinOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.roll(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当前年的最大long时间
     *
     * @return
     */
    public static long getMaxOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前周的最小long时间
     *
     * @return
     */
    public static long getMinOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 得到增加或减去变化量之后的新时间
     * Calendar.add() 加上或减去的时间(amount)指定金额给定日历字段(field)，基于日历的规则。
     * Calendar.add()方法的声明
     * public abstract void add(int field,int amount)
     *
     * @param monthOffset 加上或减去的指定单位的时间倍数
     * @param time        当前时间点
     * @return
     */

    public static long getLongTimeByMonthOffset(int monthOffset, long time) {
        Calendar calendar = Calendar.getInstance();
        //time是传入的,一个时间点
        calendar.setTime(new Date(time));
//        Calendar.add() 加上或减去的时间(amount)指定金额给定日历字段(field)，基于日历的规则
//          参数1指定单位是月,参数2为加上或减去的指定单位时间的个数
        calendar.add(Calendar.MONTH, monthOffset);
        //Calendar.getTimeInMillis() 方法返回此Calendar以毫秒为单位的时间
        //得到新时间
        return calendar.getTimeInMillis();
    }

    public static long getSystemCurrentTime() {
        return System.currentTimeMillis();
    }

    public static int getMaxDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}