package com.example.mbenben.studydemo.basenote.alarm;

/**
 * @author Created by MDove 2018/2/27
 */

public interface IAlarmTask {

    long getNextTriggerAt();

    long getInterval();

    void doWork();
}
