package com.example.mbenben.studydemo.net.httpurl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2017/1/8.
 */

public class POSTRlvActivity extends AppCompatActivity{
    private RecyclerView rlvMain;
    private List<Data> datas=new ArrayList<>();
    private String postURL="http://www.ohonor.xyz/retrofit";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postrlv);
        rlvMain= (RecyclerView) findViewById(R.id.rlv_main);
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        getDatasFromServer();
    }
    private void getDatasFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection connection=null;
                InputStream in=null;
                BufferedReader reader = null;
                try{
                    url = new URL(postURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    in =connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while((line = reader.readLine())!= null){
                        response.append(line);
                    }
                    //拿到服务器返回的Json格式，进行解析
                    parseJSONWithJSONObject(response.toString());
                } catch (Exception e ){
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                        in.close();
                        connection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0;i<jsonArray.length();i++) {
                //循环遍历把每个Data装到List<Data>中，这样Adapter的数据源就有了
                Data data=new Data();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                data.setName(name);
                String content = jsonObject.getString("content");
                data.setContent(content);
                String imgpath = jsonObject.getString("imgpath");
                Bitmap bitmap = getImageView(new URL(imgpath));
                data.setBitmap(bitmap);
                datas.add(data);
            }
            //数据后台线程遍历完，此时转至主线程更新UI（也是是更新RecyclerView）
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rlvMain.setAdapter(new MyAdapter(datas));
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap  getImageView(URL url) throws IOException {
        if(url!=null){
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //从connection中获取流
            InputStream in =connection.getInputStream();
            Bitmap bitmap= BitmapFactory.decodeStream(in);
            in.close();
            return  bitmap;
        }else{
            return null;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<Data> datas;
        private Context context;
        private LayoutInflater layoutInflater;

        public MyAdapter(List<Data> datas) {
            this.datas = datas;
            context=POSTRlvActivity.this;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(layoutInflater.inflate(R.layout.item_postrlv,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Data data =datas.get(position);
            holder.tvName.setText(data.getName());
            holder.tvContent.setText(data.getContent());
            holder.ivImage.setImageBitmap(data.getBitmap());
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tvName,tvContent;
            private ImageView ivImage;
            public ViewHolder(View itemView) {
                super(itemView);
                tvName= (TextView) itemView.findViewById(R.id.tv_name);
                tvContent= (TextView) itemView.findViewById(R.id.tv_content);
                ivImage= (ImageView) itemView.findViewById(R.id.iv_image);
            }
        }
    }
}
