package com.example.mbenben.studydemo.layout.recyclerview.sticky.view.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MDove on 2017/9/19.
 */

public class NormalDecorration extends RecyclerView.ItemDecoration {

    private Paint paintDraw;
    private Paint paintDrawOver;

    public NormalDecorration() {
        paintDraw = new Paint();
        paintDraw.setColor(Color.BLACK);
        paintDraw.setStyle(Paint.Style.FILL);
        paintDraw.setAntiAlias(true);

        paintDrawOver = new Paint();
        paintDrawOver.setStyle(Paint.Style.FILL);
        paintDrawOver.setAntiAlias(true);
    }

    //可以实现类似绘制背景的效果，内容在上面
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //基于ItemOffsets方法移动的位置，然后在这个位置进行绘制，因为有canvas，我们可以随心所欲的画。
        int viewCount = parent.getChildCount();
        for (int i = 0; i < viewCount; i++) {
            View view = parent.getChildAt(i);
            float right = view.getRight();
            float left = right - DpUtils.dp2px(10);
            float top = view.getTop();
            float bottom = view.getBottom();
            Log.d("aaa",left+"!"+top+"!"+right+"!"+bottom);
            //渐变效果
            Shader shader = new LinearGradient(left, top, right, bottom, Color.parseColor("#E91E63"),
                    Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
            paintDraw.setShader(shader);

            c.drawRect(0, 0, right, bottom, paintDraw);
        }
    }

    //可以绘制在内容的上面，覆盖内容
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //绘制在Item上的效果
        int viewCount = parent.getChildCount();

        for (int i = 0; i < viewCount; i++) {
            View view = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(view);
            if (position % 2 == 0) {
                //ComposeShader() 在硬件加速下是不支持两个相同类型的 Shader 的，所以这里也需要关闭硬件加速才能看到效果。
                Bitmap bitmap1 = BitmapFactory.decodeResource(parent.getResources(), R.drawable.pintu);
                Bitmap bitmap2 = BitmapFactory.decodeResource(parent.getResources(), R.drawable.btm_lu);
                Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

//                paintDrawOver.setShader(new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER));
                paintDrawOver.setShader(shader1);
                c.drawCircle(view.getWidth() - DpUtils.dp2px(40), DpUtils.dp2px(40) + view.getTop(),
                        DpUtils.dp2px(30), paintDrawOver);
            }
        }
    }

    //实现类似padding的效果
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //只是让Item移动位置，移动后的颜色填充取决于我们的布局颜色
        outRect.bottom = DpUtils.dp2px(10);
        outRect.right = DpUtils.dp2px(10);
    }
}
