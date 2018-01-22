package com.example.mbenben.studydemo.view.opengl.flag;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;

/**
 * @see Renderer
 * @author (虾米吃鱼肉干)
 *
 */
public class FlagRenderer implements Renderer {

    // private static final boolean back = false;
    // private static final boolean front = true;
    // private boolean backOrFront;

    public FlagRenderer() {
	super();
	mUtil = new MyUtil();
    }

    public FlagRenderer(Bitmap[] images) {
	super();
	mUtil = new MyUtil();
	this.mImages = images;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

	// 旋转参数
	// if (backOrFront) {
	// util.rot--;
	// if (util.rot == -45) {
	// backOrFront = back;
	// }
	// } else {
	// util.rot++;
	// if (util.rot == 45) {
	// backOrFront = front;
	// }
	// }

	// 清除屏幕和深度缓存
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	this.draw(gl, mUtil.mTextures[0], mUtil.mBase, true);
	this.draw(gl, mUtil.mTextures[0], mUtil.mBase, false);
	this.draw(gl, mUtil.mBase);

	gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	mUtil.changePointsAndNormals();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
	float ratio = (float) width / height;
	// 设置opengl场景的大小
	gl.glViewport(0, 0, width, height);
	// 设置投影矩阵
	gl.glMatrixMode(GL10.GL_PROJECTION);
	// 重置当前的模型观察矩阵
	gl.glLoadIdentity();
	// 设置视口的大小
	gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	// 选择模型观察矩阵
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	// 重置当前的模型观察矩阵
	gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	// 关闭图形抖动，否则灯光照射后没有光斑效果
	gl.glDisable(GL10.GL_DITHER);

	// 黑色背景
	gl.glClearColor(0, 0, 0, 0);
	// 精细的透视修正
	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

	// 设置只显示单面，背面不进行贴图以节省资源
	gl.glEnable(GL10.GL_CULL_FACE);
	gl.glFrontFace(GL10.GL_CW);// 顺时针为正面(GL10.GL_CCW顺时针，GL10.GL_CW逆时针)
	// gl.glCullFace(GL10.GL_BACK);

	// 启用阴影平滑
	gl.glShadeModel(GL10.GL_SMOOTH);
	// 启用深度测试
	gl.glEnable(GL10.GL_DEPTH_TEST);

	// 清除深度测试
	gl.glClearDepthf(1.0f);
	// 深度测试的类型
	gl.glDepthFunc(GL10.GL_LEQUAL);

	// 设置混合渲染
	mUtil.setBlend(gl);

	// 设置纹理后，设置材质以及光效后看不出效果
	// 设置材质
	mUtil.setMaterails(gl);
	// 设置灯光效果
	mUtil.setLight(gl);

	// 初始化纹理
	mUtil.generateTexture(gl, mImages);
	// 精细的透视修正
	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	// util.openTexture(gl);// 开启纹理贴图功能

	gl.glLoadIdentity();
    }

    public void draw(GL10 gl, int id, float[] base, boolean isok) {

	if (isok) {
	    mUtil.openTexture(gl);// 开启纹理贴图功能
	    if (key) {
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
	    } else {
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	    }
	}

	if (true) {

	    gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
	}

	gl.glLoadIdentity();
	// 将中心位置移动到指定的位置
	gl.glTranslatef(base[0], base[1], base[2]);

	// 设置旋转
	gl.glRotatef(mUtil.rot, 1.0f, 0.0f, 0.0f);
	// gl.glRotatef(util.rot, 0, -1.0f, 0.0f);
	// gl.glRotatef(util.rot, 0, 0.0f, -1.0f);

	gl.glRotatef(30.0f, -1.0f, 0.0f, 0.0f);
	// gl.glRotatef(40.0f, 0.0f, 0.0f, -1.0f);
	// gl.glRotatef(40.0f, 0.0f, -1.0f, 0.0f);
	// gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);

	// 允许设置顶点
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mUtil.mVertexBuffer);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mUtil.mTexCoordsBuffer);
	gl.glNormalPointer(3, GL10.GL_FLOAT, mUtil.mNormalsBuffer);

	// gl.glColor4f(0.3f, 0.3f, 0.3f, 0.3f);// 设置图形颜色

	gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, (MyUtil.NUMBER - 1)
		* (MyUtil.NUMBER - 1) * 4, GL10.GL_UNSIGNED_SHORT,
		mUtil.mOrderBuffer);

	gl.glFinish();

	if (isok) {// 绘制完成关闭纹理映射

	    mUtil.closeTexture(gl);
	}
    }

    /**
     * @see 绘制背景图形
     * @param gl
     */
    void draw(GL10 gl, float base[]) {
	mUtil.openTexture(gl);// 开启纹理贴图功能
	gl.glBindTexture(GL10.GL_TEXTURE_2D, mUtil.mTextures[1]);
	gl.glLoadIdentity();
	// 将中心位置移动到指定的位置
	gl.glTranslatef(base[0], base[1], base[2] - 2.0f);
	// 旋转
	// gl.glRotatef(util.rot, 1.0f, 0.0f, 0.0f);
	// gl.glRotatef(180.0f, 0, -1.0f, 0.0f);
	// gl.glRotatef(40.0f, -1.0f, 0.0f, 0.0f);
	// gl.glRotatef(40.0f, 0.0f, 1.0f, 0.0f);

	// 允许设置顶点
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	gl.glEnable(GL10.GL_NORMALIZE);
	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, backgroundVers);
	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, backgroundCoords);

	// gl.glColor4f(0.3f, 0.3f, 0.3f, 1.0f);// 设置图形颜色

	// gl.glColor4f(0.0f, 1.0f, 1.0f, 0.5f);

	// gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);// 画四边形的方法1
	gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE,
		backgroundOrder);// 画四边形的方法2

	gl.glDisable(GL10.GL_NORMALIZE);
	gl.glFinish();
	mUtil.closeTexture(gl);
    }

    // 开启关闭混合功能
    public boolean onKeyUp(int keyCode, KeyEvent event) {
	key = !key;
	return false;
    }

    // 背景图形的顶点坐标
    FloatBuffer backgroundVers = FloatBuffer.wrap(new float[] {//
	    -1.2f, 1.2f, 0, // 0
		    1.2f, 1.2f, 0,// 1
		    1.2f, -1.2f, 0,// 2
		    -1.2f, -1.2f, 0,// 3
	    });
    // 背景图形的纹理坐标
    FloatBuffer backgroundCoords = FloatBuffer.wrap(new float[] {//
	    0.05f, 0.95f, // 3
		    0.95f, 0.95f, // 2
		    0.95f, 0.05f,// 1
		    0.05f, 0.05f, // 0

	    });
    // 绑定顺序
    ByteBuffer backgroundOrder = ByteBuffer.wrap(new byte[] { 0, 1, 3, 2, });

    boolean key = true;
    int rot;

    MyUtil mUtil;
    Bitmap[] mImages;
}
