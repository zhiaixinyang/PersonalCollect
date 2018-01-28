package com.example.mbenben.studydemo.basenote.service.download;

import android.content.Context;
import android.content.Intent;

import com.example.mbenben.studydemo.basenote.db.ThreadDAO;
import com.example.mbenben.studydemo.basenote.db.ThreadDAOImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class DownLoadTask {
    private FileBean fileBean;
    private Context context;
    private ThreadDAO threadDAO;

    public boolean isPause;

    private int finishedLength;

    public DownLoadTask(Context context,FileBean fileBean) {
        this.fileBean = fileBean;
        this.context = context;
        threadDAO = new ThreadDAOImpl(context);
    }

    public void download(){
        List<ThreadBean> list=threadDAO.getThreadList(fileBean.getUrl());
        ThreadBean threadBean=null;
        if (list.size()==0) {
            //第一次下载
            threadBean = new ThreadBean(0, fileBean.getUrl(), 0, fileBean.getLength(), 0);
        }else{
            threadBean=list.get(0);
        }
        new DownLoadThread(threadBean).start();
    }

    class DownLoadThread extends Thread {
        private ThreadBean threadBean;

        public DownLoadThread(ThreadBean threadBean) {
            this.threadBean = threadBean;
        }

        @Override
        public void run() {
            //向数据库插入线程信息
            if (threadDAO.isExists(threadBean.getUrl(), threadBean.getId())) {
                threadDAO.insertThread(threadBean);
            }
            HttpURLConnection conn = null;
            InputStream inputStream=null;
            RandomAccessFile raFile=null;
            try {
                URL url = new URL(threadBean.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                //设置下载开始位置
                int start = threadBean.getStartLength() + threadBean.getFinshedLength();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadBean.getEndLength());
                //设置文件存放位置
                File file = new File(DownLoadService.DOWNLOAD_PATH, fileBean.getFileName());
                raFile = new RandomAccessFile(file, "rwd");
                //设置从什么地方开始存内容。（跳过参数前面的位置）
                raFile.seek(start);


                finishedLength += threadBean.getFinshedLength();
                Intent update = new Intent(DownLoadService.ACTION_UPDATE);
                //开始下载（部分下载返回码为206）
                if (conn.getResponseCode() == 206) {
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    int currTime= (int) System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        raFile.write(buffer, 0, len);
                        finishedLength += len;
                        //把下载进度以广播的形式发送出去
                        if (System.currentTimeMillis()-currTime>200) {
                            //每隔0.2秒发送一次广播
                            currTime= (int) System.currentTimeMillis();
                            update.putExtra("thread_finished_length", finishedLength);
                            context.sendBroadcast(update);
                        }
                        //暂停时保存进度至数据库
                        if (isPause){
                            threadDAO.updataThread(threadBean.getUrl(),
                                    threadBean.getId(),
                                    threadBean.getFinshedLength());
                            return;
                        }
                    }
                    //下载完成后，删除数据库中线程信息
                    threadDAO.deleteThread(threadBean.getUrl(),threadBean.getId());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect();
                    inputStream.close();
                    raFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
