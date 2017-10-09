package com.example.mbenben.studydemo.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MDove on 2017/10/9.
 */
public class DecimalFormatUtil {
    private static final double MAX_COST = 9999999;
    private static final boolean SHOW_NUMBER_DOUBLE = false;
    private static DecimalFormat FORMAT_SEPARATOR = new DecimalFormat("#,###.##");
    private static DecimalFormat FORMAT = new DecimalFormat("####.#####");

    public static String getSeparatorDecimalStr(double d) {
        if (SHOW_NUMBER_DOUBLE) {
            FORMAT_SEPARATOR = new DecimalFormat("#,##0.00");
        } else {
            FORMAT_SEPARATOR = new DecimalFormat("#,###.##");
        }
        return FORMAT_SEPARATOR.format(d);
    }

    public static String getThiredAccountSeparatorDecimalStr(double d) {
        FORMAT_SEPARATOR = new DecimalFormat("#,###.##");
        return FORMAT_SEPARATOR.format(d);
    }

    public static String getTowDecSeparator(double d) {
        FORMAT_SEPARATOR = new DecimalFormat("#,##0.00");
        return FORMAT_SEPARATOR.format(d);
    }

    public static double getDecimalDouble(double d) {
        String format = FORMAT.format(d);
        format = format.replace(',', '.');
        return Double.parseDouble(format);
    }

    public static boolean isZero(double balance) {
        if (balance == 0) {
            return true;
        } else {
            return Math.abs(balance) <= 0.009;
        }
    }


    public static String formatStaticCost(double cost) {

        if (cost > 10000000) {
            cost = cost / 10000;
            return getSeparatorDecimalStr(Math.ceil(cost)) + "万";
        }

        return getSeparatorDecimalStr(Math.ceil(cost));

    }


    public static String getUnsignedDecimalStr(double d) {
        if (d >= 0) {
            return getSeparatorDecimalStr(d);
        }
        return getSeparatorDecimalStr(-d);
    }

    public static String getDecimalStr(double d) {
        return FORMAT.format(d);
    }


    /**
     * 检查无符号金额输入是否合法
     */

    public static boolean checkCostUnsigned(double cost) {
        if (DecimalFormatUtil.isZero(cost) || Math.abs(cost) > MAX_COST || cost < 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查有符号金额输入是否合法
     */

    public static boolean checkCost(double cost) {
        if (DecimalFormatUtil.isZero(cost) || Math.abs(cost) > MAX_COST) {
            return false;
        }
        return true;
    }


    /**
     * 检查有符号金额输入是否合法
     */

    public static boolean checkAccountCost(double cost) {
        if (Math.abs(cost) > MAX_COST) {
            return false;
        }
        return true;
    }

    /**
     * 检测字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


}
