package com.example.mbenben.studydemo.basenote.service.downloadfile.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mbenben.studydemo.basenote.service.downloadfile.bean.FileInfo;
import com.example.mbenben.studydemo.basenote.service.downloadfile.bean.ThreadInfo;
import com.example.mbenben.studydemo.basenote.service.downloadfile.db.ThreadDAOImpl2;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by luoxiaoke on 2016/4/29 16:45.
 * use for 下载任务类
 *
 * 原项目GitHub:https://github.com/103style/Download
 */

public class DownloadTask2 {
    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAOImpl2 mThreadDAO2 = null;
    private long mFinished = 0;
    private int mThreadCount = DownloadService2.runThreadCount;
    public boolean isPause = false;
    //线程池
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    private List<DownloadThread2> mThradList = null;

    public DownloadTask2(Context mContext, FileInfo mFileInfo, int threadCount) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadDAO2 = new ThreadDAOImpl2(mContext);
        this.mThreadCount = threadCount;
    }

    public void download() {
        //读取数据库的线程信息
        List<ThreadInfo> threadInfos = mThreadDAO2.getThread(mFileInfo.getUrl());
        Log.e("threadsize==", threadInfos.size() + "");

        if (threadInfos.size() == 0) {
            //获得每个线程下载的长度
            long length = mFileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), length * i, (i + 1) * length - 1, 0);
                if (i + 1 == mThreadCount) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                //添加到线程信息集合中
                threadInfos.add(threadInfo);

                //向数据库插入线程信息
                mThreadDAO2.insertThread(threadInfo);
            }
        }
        mThradList = new ArrayList<>();
        //启动多个线程进行下载
        for (ThreadInfo thread : threadInfos) {
            DownloadThread2 downloadThread = new DownloadThread2(thread);
//            downloadThread.start();
            DownloadTask2.sExecutorService.execute(downloadThread);
            //添加线程到集合中
            mThradList.add(downloadThread);
        }
    }


    /**
     * 下载线程
     */
    class DownloadThread2 extends Thread {
        private ThreadInfo threadInfo;
        public boolean isFinished = false;

        public DownloadThread2(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            //向数据库插入线程信息
//            Log.e("isExists==", mThreadDAO2.isExists(threadInfo.getUrl(), threadInfo.getId()) + "");
//            if (!mThreadDAO2.isExists(threadInfo.getUrl(), threadInfo.getId())) {
//                mThreadDAO2.insertThread(threadInfo);
//            }
            HttpURLConnection connection;
            RandomAccessFile raf;
            InputStream is;
            try {
                URL url = new URL(threadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                //设置下载位置
                long start = threadInfo.getStart() + threadInfo.getFinish();
                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService2.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent(DownloadService2.ACTION_UPDATE);
                mFinished += threadInfo.getFinish();
                Log.e("threadInfo.getFinish==", threadInfo.getFinish() + "");

//                Log.e("getResponseCode ===", connection.getResponseCode() + "");
                //开始下载
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    Log.e("getContentLength==", connection.getContentLength() + "");

                    //读取数据
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = is.read(buffer)) != -1) {

                        if (isPause) {
                            Log.e("mfinished==pause===", mFinished + "");
                            //下载暂停时，保存进度到数据库
                            mThreadDAO2.updateThread(mFileInfo.getUrl(), mFileInfo.getId(), threadInfo.getFinish());
                            return;
                        }

                        //写入文件
                        raf.write(buffer, 0, len);
                        //累加整个文件下载进度
                        mFinished += len;
                        //累加每个线程完成的进度
                        threadInfo.setFinish(threadInfo.getFinish() + len);
                        //每隔1秒刷新UI
                        if (System.currentTimeMillis() - time > 1000) {//减少UI负载
                            time = System.currentTimeMillis();
                            //把下载进度发送广播给Activity
                            intent.putExtra("id", mFileInfo.getId());
                            intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
                            mContext.sendBroadcast(intent);
                            Log.e(" mFinished==update==", mFinished * 100 / mFileInfo.getLength() + "");
                        }

                    }
                    //标识线程执行完毕
                    isFinished = true;
                    //检查下载任务是否完成
                    checkAllThreadFinished();
//                    //删除线程信息
//                    mThreadDAO2.deleteThread(mFileInfo.getUrl(), mFileInfo.getId());
                    is.close();
                }
                raf.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断所有线程是否都执行完毕
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;
        //编辑线程集合 判断是否执行完毕
        for (DownloadThread2 thread : mThradList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if (allFinished) {
            //删除线程信息
            mThreadDAO2.deleteThread(mFileInfo.getUrl());
            //发送广播给Activity下载结束
            Intent intent = new Intent(DownloadService2.ACTION_FINISHED);
            intent.putExtra("fileinfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }
    }

}

