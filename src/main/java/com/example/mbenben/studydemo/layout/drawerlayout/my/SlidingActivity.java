package com.example.mbenben.studydemo.layout.drawerlayout.my;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;
import com.example.mbenben.studydemo.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/4/3.
 */

//一种很原始的侧滑解决方案，不具备太大多的实用价值
public class SlidingActivity extends AppCompatActivity implements View.OnTouchListener{
    @BindView(R.id.menu) LinearLayout menu;
    @BindView(R.id.content) LinearLayout content;

    private int widthScreen;
    private LinearLayout.LayoutParams menuParams;
    private LinearLayout.LayoutParams contentParams;

    private int menuPadding =DpUtils.dp2px(50);

    /**
     * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
     */
    private int leftEdge;
    /**
     * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
     */
    private int rightEdge = 0;

    /**
     * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
     */
    public static final int SNAP_VELOCITY = 200;
    /**
     * 用于计算手指滑动的速度。
     */
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        widthScreen= ScreenUtils.getScreenWidth();

        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        contentParams= (LinearLayout.LayoutParams) content.getLayoutParams();

        menuParams.width=widthScreen-menuPadding;

        leftEdge=-menuParams.width;
        // menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
        menuParams.leftMargin = leftEdge;
        // 将content的宽度设置为屏幕宽度
        contentParams.width=widthScreen;

        content.setOnTouchListener(this);
    }

    private float move,down,up;
    private boolean isMenuVisible;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                down=event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                move=event.getRawX();
                int distance= (int) (move-down);
                if (isMenuVisible){
                    menuParams.leftMargin = distance;
                }else{
                    menuParams.leftMargin=leftEdge+distance;
                }

                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                up = event.getRawX();
                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else {
                        scrollToMenu();
                    }
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
     *
     * @return 当前手势想显示content返回true，否则返回false。
     */
    private boolean wantToShowContent() {
        return up - down < 0 && isMenuVisible;
    }

    /**
     * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
     *
     * @return 当前手势想显示menu返回true，否则返回false。
     */
    private boolean wantToShowMenu() {
        return up - down > 0 && !isMenuVisible;
    }

    /**
     * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该滚动将menu展示出来。
     *
     * @return 如果应该滚动将menu展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToMenu() {
        return up - down > widthScreen / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
     * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
     *
     * @return 如果应该滚动将content展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToContent() {
        return down - up + menuPadding > widthScreen / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 将屏幕滚动到menu界面，滚动速度设定为30.
     */
    private void scrollToMenu() {
        new ScrollTask().execute(30);
    }

    /**
     * 将屏幕滚动到content界面，滚动速度设定为-30.
     */
    private void scrollToContent() {
        new ScrollTask().execute(-30);
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *            content界面的滑动事件
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }



    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
                sleep(20);
            }
            if (speed[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            menuParams.leftMargin = leftMargin[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            menuParams.leftMargin = leftMargin;
            menu.setLayoutParams(menuParams);
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis
     *            指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
