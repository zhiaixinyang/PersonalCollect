package com.example.mbenben.studydemo.layout.fragmenttabhost.server;

import android.util.Log;

import com.example.mbenben.studydemo.layout.fragmenttabhost.bean.WebConfig;
import com.example.mbenben.studydemo.utils.StreamToolkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MBENBEN on 2016/9/30.
 */
public class TestHttpServer {

    private final WebConfig webConfig;
    private final ExecutorService threadpool;
    private ServerSocket webSocket;

    public TestHttpServer(WebConfig webConfig){
        this.webConfig=webConfig;
        //此线程池不会在线程结束后立即结束线程。
        threadpool=Executors.newCachedThreadPool();
    }

    private boolean isEnable;

    public void startAsync(){
        isEnable=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }
    public void stopAsync(){
        if (!isEnable){
        }
        isEnable=false;
        try {

            webSocket.close();
            webSocket=null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doProcSync() {
        try {
            InetSocketAddress isa=new InetSocketAddress(webConfig.getPort());
            webSocket=new ServerSocket();
            webSocket.bind(isa);
            while (isEnable){
                final Socket remotePeer=webSocket.accept();
                threadpool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("aaa","远程连接信息："+remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            InputStream is=remotePeer.getInputStream();
            String headerline=null;
            while ((headerline= StreamToolkit.readLine(is))!=null){
                if (headerline.equals("\r\n")){
                    break;
                }
                Log.d("aaa","headerline:"+headerline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
