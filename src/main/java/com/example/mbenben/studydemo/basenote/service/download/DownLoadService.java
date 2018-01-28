package com.example.mbenben.studydemo.basenote.service.download;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.utils.SDCardUtils;
import com.example.mbenben.studydemo.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class DownLoadService extends Service {
    public static final String ACTION_START="action_start";
    public static final String ACTION_STOP="action_stop";
    public static final String ACTION_UPDATE="action_update";

    public static final String DOWNLOAD_PATH= SDCardUtils.getSDCardPath()+"/shuxin/";
    private final int MSG_INIT=0;
    private DownLoadTask downLoadTask;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_INIT:
                    FileBean fileBean= (FileBean) msg.obj;
                    ToastUtils.showShort("开始后台下载");
                    downLoadTask=new DownLoadTask(DownLoadService.this,fileBean);
                    downLoadTask.download();
                    break;
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())){
            FileBean fileBean= (FileBean) intent.getSerializableExtra("intent_file");
            //启动下载
            new DownThread(fileBean).start();
        }else if (ACTION_STOP.equals(intent.getAction())){
            //停止下载
            FileBean fileBean= (FileBean) intent.getSerializableExtra("intent_file");
            if (downLoadTask!=null){
                downLoadTask.isPause=true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class DownThread extends Thread{
        private FileBean fileBean;
        public DownThread(FileBean fileBean){
            this.fileBean=fileBean;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raFile=null;
            try {
                URL url=new URL(fileBean.getUrl());
                conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int fileLength=-1;
                if (conn.getResponseCode()==200){
                    fileLength=conn.getContentLength();
                }
                if (fileLength<-1){
                    //获取文件长度失败
                    ToastUtils.showShort("获取文件内容失败。");
                    return;
                }
                //创建本地存放文件夹
                File dir=new File(DOWNLOAD_PATH);
                if (!dir.exists()){
                    dir.mkdir();
                }
                File file=new File(dir,fileBean.getFileName());
                //用于断点续传的File，第二个参数代表可读，可写，可删除
                raFile=new RandomAccessFile(file,"rwd");
                //设置文件长度
                raFile.setLength(fileLength);
                fileBean.setLength(fileLength);
                handler.obtainMessage(MSG_INIT,fileBean).sendToTarget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect();
                    raFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
