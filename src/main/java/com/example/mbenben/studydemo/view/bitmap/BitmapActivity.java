package com.example.mbenben.studydemo.view.bitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.jbox.JBoxDemoActivity;

/**
 * Created by MDove on 18/1/25.
 */

public class BitmapActivity extends BaseActivity {
    private ImageView iv, iv2;

    private static final String ACTION_EXTRA = "action_extra";
    private String mTitle;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, BitmapActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = TextUtils.isEmpty(mTitle) ? getIntent().getStringExtra(ACTION_EXTRA) : mTitle;
        setTitle(mTitle);

        setContentView(R.layout.activity_bitmap);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);
        //创建一个400像素*400像素，ARGB各占8位的Bitmap
        final Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        //给bitmap进行设置像素内容
        for (int i = 0; i < width / 2; i++) {
            //左上块
            for (int j = 0; j < height / 2; j++) {
                //为我们的特定像素，设置颜色
                bitmap.setPixel(i, j, Color.RED);
            }
            //左下块
            for (int j = height / 2; j < height; j++) {
                bitmap.setPixel(i, j, Color.BLUE);
            }
        }
        for (int i = width / 2; i < width; i++) {
            //右上块
            for (int j = 0; j < height / 2; j++) {
                bitmap.setPixel(i, j, Color.YELLOW);
            }
            //右下块
            for (int j = height / 2; j < height; j++) {
                bitmap.setPixel(i, j, Color.GREEN);
            }
        }
        iv.setImageBitmap(bitmap);

        //获取对应坐标的像素内容
        final int content = bitmap.getPixel(100, 100);
        //获取每个像素对应的像素内容
        final int[] contents = new int[width * height];

        /**
         * 各参数的意思：
         * 1：用什么样的数组承接各个像素内容
         * 2：从数组的哪个下标开始存放各个像素内容
         * 3：一维数组里，设置多少长度来承接Bitmap的一行像素内容（必须大于Bitmap的宽）
         *    （读取多少个像素才能换行）
         *    例：bitmap.getPixels(contents, 0,   800,   0, 0, width, height);
         *       读800像素，才换行，但是我们真正像素只有400，因此数组余下的400，用0（黑色）填充
         *       也就变成了正常颜色和黑色相交的Bitmap
         * 4：要读取Bitmap像素的第一个坐标的x
         * 5：要读取Bitmap像素的第一个坐标的y
         * 6：要读多宽的像素
         * 7：要读多少行的像素
         *
         * 调用此方法之中，我们可以在数组中得到Bitmap中对应像素坐标下的内容
         */
        bitmap.getPixels(contents, 0, width, 0, 0, width, height);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arr = new int[width * height];
                //将arr全部内容置为黑色
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = Color.BLACK;
                }

                /**
                 * 调用了此方法之后，arr，从width * 100 + 100位置开始，内容将置为Bitamp中（0，0）到（width/2,height/2）的像素内容
                 * width * 100 + 100做起点，其实如果把想象成2D平面左边，就相当于起点是（100，100）
                 * */
                bitmap.getPixels(arr, width * 100 + 100, width, 0, 0, width / 2, height / 2);

                //绘制全新的像素
                bitmap.setPixels(arr, 0, width, 0, 0, width, height);
                iv.setImageBitmap(bitmap);
            }
        });

        /**
         * 知道这些有什么用呢？我们可以做多图拼接的效果
         * 比如我们要把这四个安卓小机器人拼成一个
         */
        Bitmap rootB1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int rootBWidth1 = rootB1.getWidth();
        int rootBHeight1 = rootB1.getHeight();
        int[] realArr1 = new int[rootBHeight1 * rootBWidth1 * 4];
        //拿到rootB的像素内容填充到realArr，也就是左上角
        rootB1.getPixels(realArr1, 0, rootBWidth1 * 2, 0, 0, rootBWidth1, rootBHeight1);
        //右上角
        rootB1.getPixels(realArr1, rootBWidth1, rootBWidth1 * 2, 0, 0, rootBWidth1, rootBHeight1);
        //左下角
        rootB1.getPixels(realArr1, rootBWidth1 * 2 * rootBHeight1, rootBWidth1 * 2, 0, 0, rootBWidth1, rootBHeight1);
        //右下角
        rootB1.getPixels(realArr1, rootBWidth1 * 2 * rootBHeight1 + rootBWidth1, rootBWidth1 * 2, 0, 0, rootBWidth1, rootBHeight1);

        Bitmap realB1 = Bitmap.createBitmap(realArr1, 0, rootBWidth1 * 2, rootBWidth1 * 2, rootBHeight1 * 2, Bitmap.Config.ARGB_8888);
        iv.setImageBitmap(realB1);
        iv.setOnClickListener(null);

        //y轴缩小一倍（说白了就行，缩短一倍的换行宽度，这样就会有等距的像素没办法绘制，也就相当于缩小了一倍）
        Bitmap rootB2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int rootBWidth2 = rootB2.getWidth();
        int rootBHeight2 = rootB2.getHeight();
        int[] realArr2 = new int[rootBHeight2 * rootBWidth2 * 4];
        rootB2.getPixels(realArr2, 0, rootBWidth2, 0, 0, rootBWidth2, rootBHeight2);
        rootB2.getPixels(realArr2, rootBWidth2, rootBWidth2 , 0, 0, rootBWidth2, rootBHeight2);
        rootB2.getPixels(realArr2, rootBWidth2  * rootBHeight2, rootBWidth2 , 0, 0, rootBWidth2, rootBHeight2);
        rootB2.getPixels(realArr2, rootBWidth2  * rootBHeight2 + rootBWidth2, rootBWidth2 , 0, 0, rootBWidth2, rootBHeight2);

        Bitmap realB2 = Bitmap.createBitmap(realArr2, 0, rootBWidth2 * 2, rootBWidth2 * 2, rootBHeight2 * 2, Bitmap.Config.ARGB_8888);
        iv2.setImageBitmap(realB2);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
