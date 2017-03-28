package com.example.mbenben.studydemo.basenote.opencamera;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenCameraActivity extends AppCompatActivity {
    @BindView(R.id.btn_open_camera) Button btnOpenCamera;
    @BindView(R.id.btn_open_album) Button btnOpenAlbum;

    @BindView(R.id.iv_photo) ImageView ivPhoto;

    private Uri imageUri;

    public final static int OPEN_CAMERA=123;
    public final static int OPEN_ALBUM=124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);
        ButterKnife.bind(this);

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(getExternalCacheDir(),"test.jpg");

                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri=FileProvider.getUriForFile(App.getInstance().getContext(),
                            "com.example.mbenben.studydemo.mytest",outputImage);
                }

                imageUri=Uri.fromFile(outputImage);

                Intent openCamera=new Intent("android.media.action.IMAGE_CAPTURE");
                openCamera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(openCamera,OPEN_CAMERA);
            }
        });

        btnOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlnum();
            }
        });
    }

    private void openAlnum() {
        Intent openAlbum=new Intent("android.intent.action.GET_CONTENT");
        openAlbum.setType("image/*");
        startActivityForResult(openAlbum,OPEN_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case OPEN_CAMERA:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bp= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        ivPhoto.setImageBitmap(bp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case OPEN_ALBUM:
                if (resultCode==RESULT_OK){
                    //4.4以上的处理方式
                    if (Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath );
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(App.getInstance().getContext(),uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if (imagePath!=null){
            Bitmap bp=BitmapFactory.decodeFile(imagePath);
            ivPhoto.setImageBitmap(bp);
        }else{
            ToastUtil.toastShort("照获取失败");
        }
    }
}
