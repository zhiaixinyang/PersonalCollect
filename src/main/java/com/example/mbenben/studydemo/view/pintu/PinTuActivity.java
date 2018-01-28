package com.example.mbenben.studydemo.view.pintu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2016/12/21.
 */

public class PinTuActivity extends AppCompatActivity {
    private GridLayout mainLayout;
    private Bitmap bitmap;
    private int pintuWidth;
    private ImageView emptyImageView;
    private ImageView[][] allImageView=new ImageView[3][5];
    int viewXA,viewYA,initY,viewYB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pintu);
        mainLayout= (GridLayout) findViewById(R.id.mainLayout);

        initPinTu();
    }

    int dx,dy,ux,uy;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dx= (int) event.getX();
                dy= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                ux= (int) event.getX();
                uy= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (ux-dx>100){
                    //往右滑动
                    right();
                }else if(ux-dx<-100){
                    //往左划
                    left();
                }else if(uy-dy>100){
                    //往下滑
                    down();
                }else if(uy-dy<-100){
                    //往上滑动
                    up();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void up() {
        PinTuTag tag = (PinTuTag) emptyImageView.getTag();
        int i=tag.getI();
        int j=tag.getJ();
        //i等于0说明空白ImageView在最上边
        if (i==0){
        }else{
            tag.setI(i-1);
            tag.setJ(j);
            emptyImageView.setTag(tag);
            ImageView imageView=allImageView[i-1][j];
            PinTuTag tag1 = (PinTuTag) imageView.getTag();
            tag1.setI(i);
            tag1.setJ(j);
            imageView.setTag(tag1);
            ValueAnimator valueAnimator=ObjectAnimator.ofFloat(imageView,"translationY",imageView.getY()-imageView.getTop(),imageView.getY()-imageView.getTop()+pintuWidth+3);
            ValueAnimator valueAnimator_=ObjectAnimator.ofFloat(emptyImageView,"translationY",emptyImageView.getY()-emptyImageView.getTop(),emptyImageView.getY()-emptyImageView.getTop()-pintuWidth+3);
            valueAnimator.setDuration(500);
            valueAnimator_.setDuration(500);
            valueAnimator.start();
            valueAnimator_.start();
            allImageView[i-1][j]=emptyImageView;
            allImageView[i][j]=imageView;
        }
    }

    private void down() {
        PinTuTag tag = (PinTuTag) emptyImageView.getTag();
        int i=tag.getI();
        int j=tag.getJ();
        if (i>=2){
        }else{
            tag.setI(i+1);
            tag.setJ(j);
            emptyImageView.setTag(tag);
            ImageView imageView=allImageView[i+1][j];
            PinTuTag tag1 = (PinTuTag) imageView.getTag();
            tag1.setI(i);
            tag1.setJ(j);
            imageView.setTag(tag1);
            ValueAnimator valueAnimator=ObjectAnimator.ofFloat(imageView,"translationY",imageView.getY()-imageView.getTop(),imageView.getY()-imageView.getTop()-pintuWidth-3);
            ValueAnimator valueAnimator_=ObjectAnimator.ofFloat(emptyImageView,"translationY",emptyImageView.getY()-emptyImageView.getTop(),emptyImageView.getY()-emptyImageView.getTop()+pintuWidth-3);
            valueAnimator.setDuration(500);
            valueAnimator_.setDuration(500);
            valueAnimator.start();
            valueAnimator_.start();
            allImageView[i+1][j]=emptyImageView;
            allImageView[i][j]=imageView;
        }
    }

    private void left() {
        PinTuTag tag = (PinTuTag) emptyImageView.getTag();
        int i=tag.getI();
        int j=tag.getJ();
        if (j==0){
        }else{
            tag.setI(i);
            tag.setJ(j-1);
            emptyImageView.setTag(tag);
            ImageView imageView=allImageView[i][j-1];
            PinTuTag tag1 = (PinTuTag) imageView.getTag();
            tag1.setI(i);
            tag1.setJ(j);
            imageView.setTag(tag1);
            ValueAnimator valueAnimator=ObjectAnimator.ofFloat(imageView,"translationX",imageView.getX()-imageView.getLeft(),imageView.getX()-imageView.getLeft()+pintuWidth+3);
            ValueAnimator valueAnimator_=ObjectAnimator.ofFloat(emptyImageView,"translationX",emptyImageView.getX()-emptyImageView.getLeft(),emptyImageView.getX()-emptyImageView.getLeft()-pintuWidth+3);
            valueAnimator.setDuration(500);
            valueAnimator_.setDuration(500);
            valueAnimator.start();
            valueAnimator_.start();
            allImageView[i][j-1]=emptyImageView;
            allImageView[i][j]=imageView;
        }
    }

    private void right() {
        PinTuTag tag = (PinTuTag) emptyImageView.getTag();
        int i=tag.getI();
        int j=tag.getJ();
        if (j==4){
        }else{
            tag.setI(i);
            tag.setJ(j+1);
            emptyImageView.setTag(tag);
            ImageView imageView=allImageView[i][j+1];
            PinTuTag tag1 = (PinTuTag) imageView.getTag();
            tag1.setI(i);
            tag1.setJ(j);
            imageView.setTag(tag1);
            ValueAnimator valueAnimator=ObjectAnimator.ofFloat(imageView,"translationX",imageView.getX()-imageView.getLeft(),imageView.getX()-imageView.getLeft()-pintuWidth-3);
            ValueAnimator valueAnimator_=ObjectAnimator.ofFloat(emptyImageView,"translationX",emptyImageView.getX()-emptyImageView.getLeft(),emptyImageView.getX()-emptyImageView.getLeft()+pintuWidth-3);
            valueAnimator.setDuration(500);
            valueAnimator_.setDuration(500);
            valueAnimator.start();
            valueAnimator_.start();
            allImageView[i][j+1]=emptyImageView;
            allImageView[i][j]=imageView;
        }
    }

    private void initPinTu() {
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.pintu);
        pintuWidth=bitmap.getWidth()/5;
        for (int i=0;i<3;i++){
            for(int j=0;j<5;j++){
                ImageView imageView=new ImageView(this);
                Bitmap myBitmap=Bitmap.createBitmap(bitmap,j*pintuWidth,i*pintuWidth,pintuWidth,pintuWidth);
                imageView.setImageBitmap(myBitmap);
                imageView.setPadding(2,2,2,2);
                PinTuTag pintuTag=new PinTuTag();
                pintuTag.setI(i);
                pintuTag.setJ(j);
                imageView.setTag(pintuTag);
                allImageView[i][j]=imageView;
            }
        }
        allImageView[2][4].setImageBitmap(null);
        emptyImageView=allImageView[2][4];
        for (int i=0;i<3;i++){
            for (int j=0;j<5;j++){
                mainLayout.addView(allImageView[i][j]);
            }
        }
    }

    private class PinTuTag{
        private int i;
        private int j;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }
}
