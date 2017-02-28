package com.example.mbenben.studydemo.basenote.db;

import com.example.mbenben.studydemo.basenote.service.download.ThreadBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public interface ThreadDAO {
    void insertThread(ThreadBean threadBean);
    void deleteThread(String url,int thread_id);
    void updataThread(String url,int thread_id,int finished_length);
    List<ThreadBean> getThreadList(String url);
    boolean isExists(String url,int thread_id);
}
