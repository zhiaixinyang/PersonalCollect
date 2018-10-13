package com.example.mbenben.studydemo.net.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.net.retrofit.model.RetrofitApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by MDove on 2017/1/9.
 */
public class RetrofitPostActiviy extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassWord;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_retrofitpost);
        ButterKnife.bind(this);
    }

    public void postRetrofit(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        String userName = etUsername.getText().toString();
        String passWord = etPassWord.getText().toString();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<ResponseBody> call = retrofitApi.postRetrofit(userName, passWord);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(response.message());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
