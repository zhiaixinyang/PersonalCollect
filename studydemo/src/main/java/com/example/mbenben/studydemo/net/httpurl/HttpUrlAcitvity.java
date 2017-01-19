package com.example.mbenben.studydemo.net.httpurl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/12/23.
 */

public class HttpUrlAcitvity extends AppCompatActivity{
    @BindView(R.id.buidingname) EditText buidingname;
    @BindView(R.id.usernick) EditText usernick;;
    @BindView(R.id.username) EditText username;;
    @BindView(R.id.password) EditText password;;
    @BindView(R.id.content)TextView content;
    @BindView(R.id.imageView) ImageView imageView;
    private MyAsyncTask myAsyncTask;
    private String postPath="http://www.ohonor.xyz/test/servlet/SendServlet";
    private String imagePath="http://www.ohonor.xyz/image/TestImage.png";
    private String txtPath="http://www.ohonor.xyz/txt/test.txt";
    private String getJson="http://120.27.4.196:8080/test/servlet/ShowServlet";
    private String postAccountPath="http://101.200.56.5:8080/Projecttwo/enroll.action";


    private class MyAsyncTask extends AsyncTask<String,Integer,Bitmap>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection conn = null;
            try {
                URL url=new URL(params[0]);
                conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode()==200){
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (conn!=null){
                    conn.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            content.setText("图片请求完毕。");
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content.setText("开始请求图片：正在下载图片");
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = msg.getData().getString("response");
            content.setText(response);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpurl);
        ButterKnife.bind(this);
        myAsyncTask=new MyAsyncTask();
    }
    public void postForm(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUrlPostForm();
            }
        }).start();
    }
    public void getImage(View view){
        myAsyncTask.execute(imagePath);
    }

    private void httpUrlPostForm() {
        BufferedReader br=null;
        HttpURLConnection httpURLConnection=null;
        try {
            URL url=new URL(postPath);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            /**
             * conn.setDoInput(true); //允许输入流，即允许下载
             *
             * conn.setDoOutput(true); //允许输出流，即允许上传
             * 官方源码说要写这行代码，但是经测试不写传数据也正常。
             */
            httpURLConnection.setDoInput(true);
            StringBuffer sb=new StringBuffer("username="+username.getText().toString()+"&password="+password.getText().toString());
            byte[] bytes=sb.toString().getBytes();
            OutputStream ops=httpURLConnection.getOutputStream();
            ops.write(bytes);
            ops.flush();
            ops.close();
            if (httpURLConnection.getResponseCode()==200){
                InputStream inputStream = httpURLConnection.getInputStream();
                br=new BufferedReader(new InputStreamReader(inputStream));
                final StringBuilder sb_=new StringBuilder();
                String lines=null;
                while ((lines=br.readLine())!=null){
                    lines = new String(lines.getBytes(), "utf-8");
                    sb_.append(lines);
                }
                br.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content.setText(sb_.toString());
                    }
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (br!=null) {
                    br.close();
                }
                if (httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void getTxt(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getTxtFromServer();
            }
        }).start();
    }


    public void postAccount(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader br;
                try {
                    URL url = new URL(postAccountPath);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("username="+username.getText().toString()+"&password="+password.getText().toString()+"&buildingnum="+buidingname.getText().toString()+"&usernick="+usernick.getText().toString());


                    if(connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        br=new BufferedReader(new InputStreamReader(inputStream));
                        final StringBuilder sb_=new StringBuilder();
                        String lines=null;
                        while ((lines=br.readLine())!=null){
                            lines = new String(lines.getBytes(), "utf-8");
                            sb_.append(lines);
                        }
                        br.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                content.setText(sb_.toString());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void getTxtFromServer(){
        BufferedReader br=null;
        HttpURLConnection conn=null;
        try {
            URL url=new URL(txtPath);
            conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //必须大写
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode()==200){
                InputStream inputStream = conn.getInputStream();
                br=new BufferedReader(new InputStreamReader(inputStream));
                final StringBuilder sb_=new StringBuilder();
                String lines=null;
                while ((lines=br.readLine())!=null){
                    lines = new String(lines.getBytes(), "utf-8");
                    sb_.append(lines);
                }
                br.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content.setText(sb_.toString());
                    }
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (br!=null) {
                    br.close();
                }
                if (conn!=null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getJson(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br=null;
                HttpURLConnection conn=null;
                try {
                    URL url=new URL(getJson);
                    conn= (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    //必须大写
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    if (conn.getResponseCode()==200){
                        InputStream inputStream = conn.getInputStream();
                        br=new BufferedReader(new InputStreamReader(inputStream));
                        final StringBuilder sb_=new StringBuilder();
                        String lines=null;
                        while ((lines=br.readLine())!=null){
                            lines = new String(lines.getBytes(), "utf-8");
                            sb_.append(lines);
                        }
                        br.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                content.setText(sb_.toString());
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {

                    try {
                        if (br!=null) {
                            br.close();
                        }
                        if (conn!=null){
                            conn.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void postRecyclerView(View view){
        Intent postRlv=new Intent(this,POSTRlvActivity.class);
        startActivity(postRlv);
    }

}
