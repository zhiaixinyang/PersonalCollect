package com.example.mbenben.studydemo.view.opengl.flag;

import java.io.InputStream;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.mbenben.studydemo.R;

/**
 * 
 * @author (虾米吃鱼肉干)
 *
 */
public class FlagActivity extends Activity {

    GLSurfaceView mGLSurfaceView;
    FlagRenderer mRender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	loadImages(this.getResources());
	mGLSurfaceView = new GLSurfaceView(this);
	mRender = new FlagRenderer(mImagesArray);
	mGLSurfaceView.setRenderer(mRender);

	setContentView(mGLSurfaceView);
    }

    static Bitmap[] mImagesArray;

    public static void loadImages(Resources r) {
	mImagesArray = new Bitmap[2];
	mImagesArray[0] = readBitmap(r, R.mipmap.flag);
	// images[0] = BitmapFactory.decodeResource(r, R.drawable.flag);
	mImagesArray[1] = readBitmap(r, R.mipmap.icon5);
    }

    /**
     * @param r
     * @param resId
     * @return
     */
    public static Bitmap readBitmap(Resources r, int resId) {
	BitmapFactory.Options opt = new BitmapFactory.Options();
	opt.inPreferredConfig = Bitmap.Config.RGB_565;
	opt.inPurgeable = true;
	opt.inInputShareable = true;
	// 获取资源图片
	InputStream is = r.openRawResource(resId);
	return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {

	    mRender.onKeyUp(keyCode, event);
	}

	return super.onKeyDown(keyCode, event);
    }

}