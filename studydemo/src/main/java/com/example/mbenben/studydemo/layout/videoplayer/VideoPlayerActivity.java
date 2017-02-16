package com.example.mbenben.studydemo.layout.videoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2017/1/23.
 */

public class VideoPlayerActivity extends AppCompatActivity{
    @BindView(R.id.viewPager) VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        videoView.setVideoURI(Uri.parse("http://www.ohonor.xyz/video_test.mp4"));
        MediaController mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }
}
