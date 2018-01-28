package com.example.mbenben.studydemo.basenote.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/3/20.
 */

public class IntentActivity extends AppCompatActivity{
    @BindView(R.id.iv_image) ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.bind(this);
    }

    public void toTwoIntent(View view){
        Intent toTwo=new Intent();
        toTwo.setAction("to_start_two_intent");
        toTwo.addCategory("to_start_two_intent_by_category");
        startActivity(toTwo);
    }
    public void openImage(View view){
        Intent openImage=new Intent();
        openImage.setAction(Intent.ACTION_PICK);
        openImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(openImage, 6566);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==6566){
                Uri uri = data.getData();
                String pathUrl = FileUtils.getPathUrlFromUri(App.getInstance().getContext(), uri);
                Bitmap bitmap = FileUtils.getBitmap(pathUrl);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
