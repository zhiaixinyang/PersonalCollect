package com.example.mbenben.studydemo.utils.log;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;

import com.example.mbenben.studydemo.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author MDove on 2018/2/14.
 */
public class LogUtils {

    private static final boolean DEBUG = App.isDebug();
    private static String logFileName = "log.txt";// 本类输出的日志文件名称

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat logSdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式

//    @SuppressLint("SimpleDateFormat")
//    private static SimpleDateFormat logSdf2 = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    private static ILog initLogger() {
        return LogHelper.getInstance().setDebug(DEBUG);
    }

    public static void i(String tag, String message) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
            ILog logger = initLogger();
            logger.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
            ILog logger = initLogger();
            logger.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
            ILog logger = initLogger();
            logger.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
            ILog logger = initLogger();
            logger.e(tag, message);
        }
    }

    public static void logToSdcard(String tag, String text) {// 新建或打开日志文件
        if (DEBUG) {
            return;
        }
        String needWriteMessage = logSdf1.format(new Date()) + "    " + tag + "    " + text;
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, logFileName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.newLine();
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
