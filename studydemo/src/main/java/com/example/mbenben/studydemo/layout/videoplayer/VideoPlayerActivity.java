package com.example.mbenben.studydemo.layout.videoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/23.
 */

public class VideoPlayerActivity extends AppCompatActivity{
    @BindView(R.id.videoView) VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        ButterKnife.bind(this);
        Uri uri = Uri.parse("http://www.ohonor.xyz/video/video_test.mp4");
        if (uri!=null) {
            videoView.setVideoURI(uri);
            MediaController mediaController=new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setMediaPlayer(videoView);
        }else{
            ToastUtils.showShort("服务器视频资源暂时被删除");
        }
    }
}
