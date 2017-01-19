package com.example.mbenben.studydemo.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/12.
 */

public class ValueAnimActivity extends AppCompatActivity {
    private ValueAnimator valueAnimator,valueAnimator0;
    private AnimatorSet animatorSet;
    @BindView(R.id.iv_image) ImageView ivImage;
    @BindView(R.id.red) TextView red;
    @BindView(R.id.black) TextView black;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valueanim);
        ButterKnife.bind(this);
    }

    //旋转
    public void rotation(View view){
        /**
         * 我们通过ofFloat的静态方法（当然也有其他的静态方法）
         * 第一个参数是动画作用的View
         * 第二个是动画方式，比如这里写的“rotationX”：以X为中心旋转
         * 接下来的参数是动画进行的属性值
         * 这里的意思是0度到360度，也可以继续往后写，那么就会继续执行对应的动画
         */
        valueAnimator =ObjectAnimator.ofFloat(ivImage, "rotationX", 0.0f, 360.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }
    //平移
    public void translation(View view){
        valueAnimator =ObjectAnimator.ofFloat(ivImage, "translationX", 0.0f, 360.0f);
        valueAnimator.setDuration(2000);
        //动画额外重复次数
        valueAnimator.setRepeatCount(2);
        /**
         * repeatMode 动画重复类型（至少要有setRepeatCount()）
         * ValueAnimator.RESTART：动画将从每个新循环的开始开始
         * ValueAnimator.REVERSE：表示动画在每次迭代时将反转方向
         */
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }
    //透明度(使用ValueAnimator实现)
    public void alpha(View view){

        valueAnimator=ValueAnimator.ofFloat( 0.0f,1.0f ,0.0f);
        valueAnimator.setTarget(ivImage);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivImage.setAlpha(animation.getAnimatedFraction());
            }
        });
    }
    //尺寸动画
    public void scale(View view){
        valueAnimator =ObjectAnimator.ofFloat(ivImage,"scaleX",1.0f, 1.5f,1.0f);
        valueAnimator.setDuration(1500);
        valueAnimator.start();
    }
    //综合动画
    public void allAnim(View view){
        animatorSet=new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ivImage, "scaleX", 1.0f, 1.5f,1.0f),
                ObjectAnimator.ofFloat(ivImage, "alpha", 0.0f, 1.0f,0.0f,1.0f),
                ObjectAnimator.ofFloat(ivImage, "translationX", 0.0f, 360.0f,0.0f),
                ObjectAnimator.ofFloat(ivImage, "rotationX", 0.0f, 360.0f)
        );
        animatorSet.setDuration(1500);
        animatorSet.start();
    }
    //估值器：抛物线效果
    public void evaluator(View view){
        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(10000);
        //如果不设置，默认也是线性插值器，也就是均匀变化
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setObjectValues(new PointF(0, 100));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>(){
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue){
                /**
                 * fraction:0-1随插值器（Interpolator）变化而变化的值
                 * 默认是匀速变化
                 * x:水平方向的速度*时间（初始设置为100）；y：1/2*g*t*t（自由落体运动的距离）
                 * View的坐标（x，y）就是对应x，y对应速度下的距离
                 * PS：
                 */
                PointF point = new PointF();
                point.x = 100 * fraction * 10;
                //这里并不好给重力加速度赋值，因为正常单位是米，这里是px...所以这里就忽略吧
                point.y = 0.5f * 98f * (fraction * 10) * (fraction * 10);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //此处得到的就是估值器中获取的Point
                PointF point = (PointF) animation.getAnimatedValue();
                ivImage.setTranslationX(point.x);
                ivImage.setTranslationY(point.y);
            }
        });
    }
    //插值器理解
    public void interpolator(View view){
        valueAnimator=ObjectAnimator.ofFloat(red,"translationX",0.0f,500.0f);
        valueAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input*input;
            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator.start();
        valueAnimator0=ObjectAnimator.ofFloat(black,"translationX",0.0f,500.0f);
        valueAnimator0.setInterpolator(new LinearInterpolator());
        valueAnimator0.setDuration(1500);
        valueAnimator0.start();
    }
}
