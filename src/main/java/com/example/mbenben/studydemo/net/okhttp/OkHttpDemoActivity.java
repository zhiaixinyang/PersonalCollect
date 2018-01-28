package com.example.mbenben.studydemo.net.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.FileUtils;
import com.example.mbenben.studydemo.utils.StringUtils;
import com.example.mbenben.studydemo.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MBENBEN on 2016/12/17.
 */

public class OkHttpDemoActivity extends AppCompatActivity{

    private OkHttpClient okHttpClient;
    private Request request;
    private Call call;
    private String pathName = Environment.getExternalStorageDirectory() + "/test1.png";
    private String postImagePath="http://101.200.56.5:8080/strutstest/fileUpload";
    private String getImageURL="http://www.ohonor.xyz/image/TestImage.png";
    private String postStringPath="http://www.ohonor.xyz/strutstest/postString";
    private String postForm="http://www.ohonor.xyz/test/servlet/SendServlet";
    private String string;
    private Bitmap bmp;
    private String imagePath;
    @BindView(R.id.tv_load_sum) TextView tvLoadSum;
    @BindView(R.id.tv_content) TextView tvContent;
    @BindView(R.id.et_password) EditText etPassWord;
    @BindView(R.id.et_username) EditText etUserName;
    @BindView(R.id.imageView) ImageView imageView;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                tvContent.setText(msg.getData().getString("test"));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp);
        ButterKnife.bind(this);
        okHttpClient=new OkHttpClient();
    }

    public void postForm(View view){
        /**
         * 这里需要使用FormBody的构建者模式进行构建请求体。
         * 这是add方法所传入的key，是和后台协商决定的，也就是固定写法。
         */
        final RequestBody requestBody=new FormBody.Builder()
                .add("username",etUserName.getText().toString())
                .add("password",etPassWord.getText().toString())
                .build();
        request=new Request.Builder()
                .url(postForm)
                .post(requestBody)
                .build();
        call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tvContent.setText("服务器返回数据："+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    public void postString(View view){
        AccountBean accountBean=new AccountBean();
        accountBean.setUsername(etUserName.getText().toString());
        accountBean.setPassword(etPassWord.getText().toString());
        request=new Request.Builder()
                .url(postStringPath)
                .post(RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),new Gson().toJson(accountBean)))
                .build();
        call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                OkHttpDemoActivity.this.string = response.body().string();
                OkHttpDemoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(string);
                    }
                });
//                Message message=new Message();
//                message.what=1;
//                Bundle bundle=new Bundle();
//                bundle.putString("test",response.body().string());
//                message.setData(bundle);
//                handler.sendMessage(message);
            }
        });
    }
    public void postFile(View view){
        if (StringUtils.isEmpty(imagePath)){
            ToastUtils.showShort("请先上传图片。");
        }else {
            File file = new File(imagePath);
            request = new Request.Builder()
                    .url(postImagePath)
                    .post(RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file))
                    .build();
            call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    final String error = e.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvContent.setText(error);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String string = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvContent.setText(string);
                        }
                    });
                }
            });
        }
    }
    public  void openImage(View view){
        Intent openImage=new Intent(Intent.ACTION_PICK,null);
        openImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(openImage, 6566);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK&&requestCode==6566){
            if (data!=null){
                Uri selectImageUri=data.getData();
                imagePath = FileUtils.getPathUrlFromUri(this,selectImageUri);
                bmp= FileUtils.getBitmap(imagePath);
                imageView.setImageBitmap(bmp);
            }
        }
    }

    public void getImage(View view){
        request=new Request.Builder()
                .url(getImageURL)
                .get()
                .build();
        call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is=response.body().byteStream();
                final File file=new File(Environment.getExternalStorageDirectory(),"test1.png");
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b=new byte[1024];
                int len=0;
                //总进度
                final long allLoadSum=response.body().contentLength();
                long loadSum=0;

                while ((len=is.read(b))!=-1) {
                    fos.write(b, 0, len);
                    loadSum += len;
                    final long finalLoadSum = loadSum;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLoadSum.setText(finalLoadSum + " / " + allLoadSum);
                        }
                    });
                }
                fos.flush();
                fos.close();
                is.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(BitmapFactory.decodeFile(pathName));
                    }
                });
            }
        });
    }

}
