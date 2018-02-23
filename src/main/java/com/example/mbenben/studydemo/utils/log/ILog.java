package com.example.mbenben.studydemo.utils.log;

/**
 * @author MDove on 2018/2/23.
 */

public interface ILog {

    void d(String tag, String msg);

    void d(String tag, String msg, Throwable throwable);

    void i(String tag, String msg);

    void i(String tag, String msg, Throwable throwable);

    void w(String tag, String msg);

    void w(String tag, String msg, Throwable throwable);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable throwable);
}
