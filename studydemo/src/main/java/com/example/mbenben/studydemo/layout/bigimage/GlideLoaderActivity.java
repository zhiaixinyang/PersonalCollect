/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.mbenben.studydemo.layout.bigimage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.bigimage.bigimageviewer.BigImageViewer;
import com.example.mbenben.studydemo.layout.bigimage.bigimageviewer.indicator.ProgressPieIndicator;
import com.example.mbenben.studydemo.layout.bigimage.bigimageviewer.view.BigImageView;
import com.example.mbenben.studydemo.layout.bigimage.glide.GlideImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GlideLoaderActivity extends AppCompatActivity {
    @BindView(R.id.mBigImage) BigImageView bigImageView;
    @BindView(R.id.mBtnLoad) Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //此处要先于super调用，并且GlideImageLoader.with要传入全局的context
        BigImageViewer.initialize(GlideImageLoader.with(App.getInstance().getContext()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimage);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigImageView.setProgressIndicator(new ProgressPieIndicator());
                bigImageView.showImage(
                        Uri.parse("http://img1.imgtn.bdimg.com/it/u=1520386803,778399414&fm=21&gp=0.jpg"),
                        Uri.parse("http://youimg1.c-ctrip.com/target/tg/773/732/734/7ca19416b8cd423f8f6ef2d08366b7dc.jpg")
                );
            }
        });
    }
}
