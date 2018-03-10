package com.example.mbenben.studydemo.net.retrofit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;
import com.example.mbenben.studydemo.net.retrofit.presenter.RetrofitPresenter;
import com.example.mbenben.studydemo.net.retrofit.presenter.RetrofitPresenterImpl;
import com.example.mbenben.studydemo.net.retrofit.view.RetrofitView;
import com.example.mbenben.studydemo.utils.FileUtils;
import com.example.mbenben.studydemo.utils.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/4.
 */

public class RetrofitActivity extends AppCompatActivity implements RetrofitView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    @BindView(R.id.srf_main) SwipeRefreshLayout srfMain;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.iv_image) ImageView ivImage;

    private RetrofitPresenter retrofitPresenter;

    private Bitmap bitmap;
    private String imgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initView();
        retrofitPresenter.getRetrofitDatas();
    }

    private void initView() {
        ButterKnife.bind(this);
        srfMain.setOnRefreshListener(this);
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        retrofitPresenter=new RetrofitPresenterImpl(this);
    }

    @Override
    public void initRetrofitDatas(List<RetrofitBean> datas) {
        rlvMain.setAdapter(new RetrofitAdapter(this,datas));
    }

    @Override
    public void initError(String error) {
        ToastUtils.showShort("加载数据失败:"+error);
    }

    @Override
    public void postSuccess(String success) {
        srfMain.setRefreshing(false);
        ToastUtils.showShort(success);
    }

    @Override
    public void postError(String error) {
        srfMain.setRefreshing(false);
        ToastUtils.showShort("上传失败："+error.toString());
    }

    @Override
    public void refreshLoading() {
        srfMain.setRefreshing(true);
    }

    @Override
    public void refreshSuccess() {
        srfMain.setRefreshing(false);
    }

    @Override
    public void postLoading() {
        srfMain.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        retrofitPresenter.getRetrofitDatas();
    }

    public void postForm(View view){
        if (!etName.getText().toString().isEmpty()&&
                !etContent.getText().toString().isEmpty()){
            RetrofitBean retrofitBean=new RetrofitBean();
            retrofitBean.setName(etName.getText().toString());
            retrofitBean.setContent(etContent.getText().toString());
            retrofitPresenter.postRetrofitDatas(retrofitBean,
                    new File(imgUrl));
        }
    }
    public void openImage(View view){
        Intent openImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openImage,001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==001){
            if (data!=null){
                Uri uri=data.getData();
                imgUrl= FileUtils.getPathUrlFromUri(this,uri);
                bitmap = BitmapFactory.decodeFile(imgUrl);
                ivImage.setImageBitmap(bitmap);
            }
        }
    }

    public void openPost(View view){
        Intent openPost=new Intent(this,RetrofitPostActiviy.class);
        startActivity(openPost);
    }
}
