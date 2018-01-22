package com.example.mbenben.studydemo.view.opengl.flag;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

/**
 * @see 绘制用到的通用方法
 * @author (虾米吃鱼肉干)
 *
 */
public class MyUtil {

    private final static float PI = (float) Math.PI;
    public static float DEPTH = -3.0f;// 深度
    public static int NUMBER = 40;// 网格数量Num*Num
    int[] mTextures;// 纹理标识数组
    float[] mBase = new float[3];// 原点偏移量

    private static float dx;// 单位x坐标
    private static float dy;// 单位y坐标
    private static float tx;// 单位纹理坐标
    private static float angle;// 单位角度

    private float mVertexsArray[][][] = new float[NUMBER][NUMBER][3];// 顶点坐标数组
    private float mTexCoordsArray[][][] = new float[NUMBER][NUMBER][2];// 纹理坐标数组
    private float mNormalsArray[][][] = new float[NUMBER][NUMBER][3];// 法向量坐标数组
    private short mOrderArray[] = new short[(NUMBER - 1) * (NUMBER - 1) * 4];// 绘制顺序数组

    FloatBuffer mVertexBuffer;// 顶点
    FloatBuffer mTexCoordsBuffer;// 纹理
    FloatBuffer mNormalsBuffer;// 法向量
    ShortBuffer mOrderBuffer;// 绘制顺序

    int rot = 0;// 旋转角度
    int k = 0;//
    float d;// 深度中间缓存

    /**
     * @see 初始化顶点信息、纹理信息、顶点法向量信息、顶点顺序信息
     */
    public MyUtil() {
	super();
	initInfo();
	generationPoints();
	getPointBuffer();
	getTexCoordsBuffer();
	getNormals();
	getNormalsBuffer();
	getOrder();
    }

    /**
     * @see 设置网格密度 n*n
     * @param n
     */
    public void setNum(int n) {

	NUMBER = n;
    }

    /**
     * @see 设置坐标深度值
     * @param d
     */
    public void setDepth(float d) {

	DEPTH = d;
    }

    /**
     * @see 初始化单位值
     */
    private void initInfo() {

	// 5 3.35

	dx = 3.0f / (NUMBER - 1);
	dy = 2.0f / (NUMBER - 1);
	tx = 1.0f / (NUMBER - 1);

	angle = ((2 * PI) / (NUMBER - 1));

	mBase[0] = 0;
	mBase[1] = 0;
	mBase[2] = DEPTH;

    }

    /**
     * @see 生成顶点坐标与纹理坐标
     */
    public void generationPoints() {
	for (int i = 0; i < NUMBER; i++) {
	    d = getDepth(i);

	    for (int j = 0; j < NUMBER; j++) {
		mVertexsArray[i][j][0] = i * dx - NUMBER * dx / 2;
		mVertexsArray[i][j][1] = NUMBER * dy / 2 - j * dy;
		mVertexsArray[i][j][2] = d;

		mTexCoordsArray[i][j][0] = i * tx;
		mTexCoordsArray[i][j][1] = (NUMBER - 1 - j) * tx;
	    }
	}
    }

    /**
     * @see 计算深度值根据正弦曲线规律
     * @param i
     * @return
     */
    public float getDepth(int i) {

	return (DEPTH + (float) Math.sin(2 * i * angle)) * 0.05f
		* Math.abs(DEPTH);
    }

    /**
     * @see 得到绘制顶点顺序buffer
     */
    private void getOrder() {

	for (int i = 0; i < NUMBER - 1; i++) {
	    for (int j = 0; j < NUMBER - 1; j++) {
		mOrderArray[(i * (NUMBER - 1) + j) * 4 + 0] = (short) (i
			* NUMBER + j);
		mOrderArray[(i * (NUMBER - 1) + j) * 4 + 1] = (short) ((i + 1)
			* NUMBER + j);
		mOrderArray[(i * (NUMBER - 1) + j) * 4 + 2] = (short) (i
			* NUMBER + j + 1);
		mOrderArray[(i * (NUMBER - 1) + j) * 4 + 3] = (short) ((i + 1)
			* NUMBER + j + 1);
	    }
	}

	mOrderBuffer = ShortBuffer.wrap(mOrderArray);
    }

    /**
     * @see 得到顶点buffer
     */
    private void getPointBuffer() {

	float vertex[] = new float[NUMBER * NUMBER * 3];

	for (int i = 0; i < NUMBER; i++) {
	    for (int j = 0; j < NUMBER; j++) {
		for (int k = 0; k < 3; k++) {
		    vertex[(i * NUMBER + j) * 3 + k] = mVertexsArray[i][j][k];
		}
	    }
	}

	mVertexBuffer = FloatBuffer.wrap(vertex);
    }

    /**
     * @see 得到纹理buffer
     */
    private void getTexCoordsBuffer() {

	float texCoord[] = new float[NUMBER * NUMBER * 2];

	for (int i = 0; i < NUMBER; i++) {
	    for (int j = 0; j < NUMBER; j++) {
		for (int k = 0; k < 2; k++) {
		    texCoord[(i * NUMBER + (NUMBER - 1 - j)) * 2 + k] = mTexCoordsArray[i][j][k];
		}
	    }
	}

	mTexCoordsBuffer = FloatBuffer.wrap(texCoord);
    }

    /**
     * @see 1得到法向量buffer
     */
    private void getNormalsBuffer() {

	float normal[] = new float[NUMBER * NUMBER * 3];

	for (int i = 0; i < NUMBER; i++) {
	    for (int j = 0; j < NUMBER; j++) {
		for (int k = 0; k < 3; k++) {
		    normal[(i * NUMBER + j) * 3 + k] = mNormalsArray[i][j][k];
		}
	    }
	}

	mNormalsBuffer = FloatBuffer.wrap(normal);
    }

    /**
     * @see 2计算出顶点法向量坐标
     */
    private void getNormals() {

	float[] nor = new float[3];// 三个顶点

	for (int i = 0; i < NUMBER; i++) {
	    nor = getVertexsNormal(i, 1);
	    for (int j = 0; j < NUMBER; j++) {
		mNormalsArray[i][j][0] = nor[0];
		mNormalsArray[i][j][1] = nor[1];
		mNormalsArray[i][j][2] = nor[2];
	    }
	}
    }

    /**
     * @see 3计算顶点法向量
     * @param i
     * @param j
     * @return
     */
    private float[] getVertexsNormal(int i, int j) {

	float[] nor1 = new float[3];
	float[] nor2 = new float[3];
	float[] nor3 = new float[3];
	float[] nor4 = new float[3];

	float[] temp = new float[] { 0, 0, -1.0f, };

	if (i - 1 < 0 || j - 1 < 0) {
	    nor1 = temp;
	} else {
	    nor1 = getNormalFrom3Vertexs(new float[] {
		    mVertexsArray[i][j - 1][0], mVertexsArray[i][j - 1][1],
		    mVertexsArray[i][j - 1][2] }, new float[] {
		    mVertexsArray[i - 1][j][0], mVertexsArray[i - 1][j][1],
		    mVertexsArray[i - 1][j][2] }, new float[] {
		    mVertexsArray[i][j][0], mVertexsArray[i][j][1],
		    mVertexsArray[i][j][2] });
	}

	if (j - 1 < 0 || i + 1 == NUMBER) {
	    nor2 = temp;
	} else {
	    nor2 = getNormalFrom3Vertexs(new float[] {
		    mVertexsArray[i + 1][j][0], mVertexsArray[i + 1][j][1],
		    mVertexsArray[i + 1][j][2] }, new float[] {
		    mVertexsArray[i][j - 1][0], mVertexsArray[i][j - 1][1],
		    mVertexsArray[i][j - 1][2] }, new float[] {
		    mVertexsArray[i][j][0], mVertexsArray[i][j][1],
		    mVertexsArray[i][j][2] });
	}

	if (i - 1 < 0 || j + 1 == NUMBER) {
	    nor3 = temp;
	} else {
	    nor3 = getNormalFrom3Vertexs(new float[] { mVertexsArray[i][j][0],
		    mVertexsArray[i][j][1], mVertexsArray[i][j][2] },
		    new float[] { mVertexsArray[i - 1][j][0],
			    mVertexsArray[i - 1][j][1],
			    mVertexsArray[i - 1][j][2] }, new float[] {
			    mVertexsArray[i][j + 1][0],
			    mVertexsArray[i][j + 1][1],
			    mVertexsArray[i][j + 1][2] });
	}

	if (i + 1 == NUMBER || j + 1 == NUMBER) {
	    nor4 = temp;
	} else {
	    nor4 = getNormalFrom3Vertexs(new float[] {
		    mVertexsArray[i + 1][j][0], mVertexsArray[i + 1][j][1],
		    mVertexsArray[i + 1][j][2] }, new float[] {
		    mVertexsArray[i][j][0], mVertexsArray[i][j][1],
		    mVertexsArray[i][j][2] }, new float[] {
		    mVertexsArray[i][j + 1][0], mVertexsArray[i][j + 1][1],
		    mVertexsArray[i][j + 1][2] });
	}

	return getNormalFrom4Normals(nor1, nor2, nor3, nor4);
    }

    /**
     * @see 4根据每个顶点相邻的4个三角形计算出该顶点的法向量
     * @param nor1
     * @param nor2
     * @param nor3
     * @param nor4
     * @return
     */
    private float[] getNormalFrom4Normals(float[] nor1, float[] nor2,
	    float[] nor3, float[] nor4) {

	float nor[] = new float[3];

	nor[0] = nor1[0] + nor2[0] + nor3[0] + nor4[0];
	nor[1] = nor1[1] + nor2[1] + nor3[1] + nor4[1];
	nor[2] = nor1[2] + nor2[2] + nor3[2] + nor4[2];

	return unitNormal(nor);
    }

    /**
     * @see 5通过三个顶点计算该三角形的平面法向量
     * @param v1
     * @param v2
     * @param v3
     */
    private float[] getNormalFrom3Vertexs(float[] v1, float[] v2, float[] v3) {

	float a0 = 0, a1 = 0, a2 = 0, b0 = 0, b1 = 0, b2 = 0;

	float nor[] = new float[3];

	a0 = v2[0] - v1[0];
	a1 = v2[1] - v1[1];
	a2 = v2[2] - v1[2];

	b0 = v3[0] - v2[0];
	b1 = v3[1] - v2[1];
	b2 = v3[2] - v2[2];

	nor[0] = a1 * b2 - a2 * b1;
	nor[1] = a2 * b0 - a0 * b2;
	nor[2] = a0 * b1 - b0 * b1;

	// System.out.println(nor[0] + "--" + nor[1] + "--" + nor[2]);

	return unitNormal(nor);
    }

    /**
     * @see 6单位化法向量坐标
     * @param nor
     * @return
     */
    private float[] unitNormal(float[] nor) {

	float r = (float) Math.sqrt(nor[0] * nor[0] + nor[1] * nor[1] + nor[2]
		* nor[2]);

	nor[0] /= r;
	nor[1] /= r;
	nor[2] /= r;

	return nor;
    }

    /**
     * @see 改变顶点信息以及顶点法向量信息
     */
    public void changePointsAndNormals() {

	float temp[] = new float[3];
	k++;
	for (int i = 0; i < NUMBER; i++) {
	    if (i == NUMBER - 1) {

		d = getDepth(i + k);
	    }

	    for (int j = 0; j < NUMBER; j++) {
		if (i == 0) {

		    temp[0] = mNormalsArray[i][j][0];
		    temp[1] = mNormalsArray[i][j][1];
		    temp[2] = mNormalsArray[i][j][2];
		}

		if (i + 1 != NUMBER) {

		    mVertexsArray[i][j][2] = mVertexsArray[i + 1][j][2];

		    mNormalsArray[i][j][0] = mNormalsArray[i + 1][j][0];
		    mNormalsArray[i][j][1] = mNormalsArray[i + 1][j][1];
		    mNormalsArray[i][j][2] = mNormalsArray[i + 1][j][2];

		} else {

		    mVertexsArray[i][j][2] = d;

		    mNormalsArray[i][j][0] = temp[0];
		    mNormalsArray[i][j][1] = temp[1];
		    mNormalsArray[i][j][2] = temp[2];

		}
	    }
	}

	getPointBuffer();
	getNormalsBuffer();
    }

    /**
     * @see 设置灯光效果
     * @param gl
     */
    public void setLight(GL10 gl) {

	// 正面
	this.setLight(gl, GL10.GL_LIGHT0, FloatBuffer
		.wrap(MyEnum.LIGHT_POSITION_FRONT), MyEnum.mAmbientLight,
		MyEnum.mDiffuseLight, MyEnum.mSpecularLight, null, 3.0f);
	// this.getSpotDirection(MyEnum.LIGHT_POSITION_FRONT, base)
	// 左上角
	this.setLight(gl, GL10.GL_LIGHT1, FloatBuffer
		.wrap(MyEnum.LIGHT_POSITION_TOP_LEFT0), null, null,
		MyEnum.mYellowSpecularLight, this.getSpotDirection(
			MyEnum.LIGHT_POSITION_TOP_LEFT0, mBase), 35.0f);

	// 右上角
	this.setLight(gl, GL10.GL_LIGHT2, FloatBuffer
		.wrap(MyEnum.LIGHT_POSITION_TOP_RIGHT0), null, null,
		MyEnum.mZiSpecularLight, this.getSpotDirection(
			MyEnum.LIGHT_POSITION_TOP_RIGHT0, mBase), 35.0f);

	// 左下角
	// this.setLight(gl, GL10.GL_LIGHT3, FloatBuffer
	// .wrap(MyEnum.LIGHT_POSITION_BOTTOM_LEFT0), null, null,
	// MyEnum.mGreenSpecularLight, this.getSpotDirection(
	// MyEnum.LIGHT_POSITION_BOTTOM_LEFT0, base), 35.0f);

	// 右下角
	// this.setLight(gl, GL10.GL_LIGHT4, FloatBuffer
	// .wrap(MyEnum.LIGHT_POSITION_BOTTOM_RIGHT0), null, null,
	// MyEnum.mBlueSpecularLight, this.getSpotDirection(
	// MyEnum.LIGHT_POSITION_BOTTOM_RIGHT0, base), 35.0f);

	// 开启灯光效果
	gl.glEnable(GL10.GL_LIGHTING);

    }

    /** @see 设置混合 */
    public void setBlend(GL10 gl) {

	gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
	// 设置混合渲染效果，会有半透明效果
	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
	// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_DST_ALPHA);
	// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	// gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

	gl.glEnable(GL10.GL_BLEND);// 开启混合渲染
	gl.glDisable(GL10.GL_DEPTH_TEST);// 关闭深度测试

	// 
	 gl.glEnable(GL10.GL_POINT_SMOOTH);
	 gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
	
	 gl.glEnable(GL10.GL_LINE_SMOOTH);
	 gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
	
	 gl.glEnable(GL10.GL_POLYGON_SMOOTH_HINT);
	 gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_FASTEST);

    }

    /**
     * @see 设置光源
     * @param gl
     * @param light第几个光源
     * @param lightPosition光源位置
     * @param mAmbientLight环境光
     * @param mDiffuseLight散射光
     * @param mSpecularLight聚光灯光
     * @param mSpotDirection聚光灯指向
     * @param spotCutoff聚光灯角度
     */
    private void setLight(GL10 gl, int light, FloatBuffer lightPosition,
	    FloatBuffer mAmbientLight, FloatBuffer mDiffuseLight,
	    FloatBuffer mSpecularLight, FloatBuffer mSpotDirection,
	    float spotCutoff) {

	gl.glEnable(light);// 启用光源
	gl.glLightfv(light, GL10.GL_POSITION, lightPosition);

	// 环境光
	if (mAmbientLight != null) {
	    gl.glLightfv(light, GL10.GL_AMBIENT, mAmbientLight);
	} else {
	    gl.glLightfv(light, GL10.GL_AMBIENT, MyEnum.mAmbientLight);
	}

	// 散射光
	if (mDiffuseLight != null) {
	    gl.glLightfv(light, GL10.GL_DIFFUSE, mDiffuseLight);
	} else {
	    gl.glLightfv(light, GL10.GL_DIFFUSE, MyEnum.mDiffuseLight);
	}

	// 聚光灯
	if (mSpecularLight != null) {
	    gl.glLightfv(light, GL10.GL_SPECULAR, mSpecularLight);
	} else {
	    gl.glLightfv(light, GL10.GL_SPECULAR, MyEnum.mSpecularLight);
	}

	// 聚光灯指向
	if (mSpotDirection != null) {
	    gl.glLightfv(light, GL10.GL_SPOT_DIRECTION, mSpotDirection);
	} else {
	    gl.glLightfv(light, GL10.GL_SPOT_DIRECTION, MyEnum.mSpotDirection);
	}

	// 聚光灯角度
	if (spotCutoff != 0) {
	    gl.glLightf(light, GL10.GL_SPOT_CUTOFF, spotCutoff);
	} else {
	    gl.glLightf(light, GL10.GL_SPOT_CUTOFF, 45.0f);
	}
    }

    /**
     * @see 设置材质属性
     * @param gl
     */
    public void setMaterails(GL10 gl) {
	// 允许使用彩色材质，不设置这句，图形会是灰色的
	// gl.glEnable(GL10.GL_COLOR_MATERIAL);

	// 环境光材质效果
	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
		MyEnum.mMeterialAmbient);

	// 散射光材质效果
	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
		MyEnum.mMeterialDiffuse);

	// 镜面反射材质效果
	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
		MyEnum.mMeterialSpecular);

	// 镜面反射光的强度
	gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);

	// 自发光效果
	gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_EMISSION, MyEnum.mEmission);

    }

    /** @see 得到光源指向方向,指向原点位置 */
    private FloatBuffer getSpotDirection(float[] lightPosition, float base[]) {
	float[] f = new float[lightPosition.length];

	for (int i = 0; i < lightPosition.length - 1; i++) {
	    f[i] = lightPosition[i] - base[i];
	}

	return FloatBuffer.wrap(f);
    }

    /**
     * @see 生成纹理
     * @param gl
     * @param images
     */
    public void generateTexture(GL10 gl, Bitmap[] images) {
	int n = images.length;
	IntBuffer intBuffer = IntBuffer.allocate(n);
	gl.glGenTextures(n, intBuffer);
	mTextures = intBuffer.array();

	for (int i = 0; i < n; i++) {
	    // 绑定纹理1
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextures[i]);

	    // GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, images[i], 0);

	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGB, images[i],
		    GL10.GL_UNSIGNED_SHORT_5_6_5, 0);

	    // 设置过滤滤波
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
		    GL10.GL_NEAREST);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
		    GL10.GL_NEAREST);

	    // gl.glTexParameterf(GL10.GL_TEXTURE_2D,
	    // GL10.GL_TEXTURE_MAG_FILTER,
	    // GL10.GL_LINEAR);
	    // gl.glTexParameterf(GL10.GL_TEXTURE_2D,
	    // GL10.GL_TEXTURE_MIN_FILTER,
	    // GL10.GL_LINEAR);

	    // 设置平铺与拉伸效果 GL10.GL_CLAMP_TO_EDGE拉伸，GL10.GL_REPEAT平铺
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
		    GL10.GL_CLAMP_TO_EDGE);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
		    GL10.GL_CLAMP_TO_EDGE);

	    gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
		    GL10.GL_REPLACE);//

	}

    }

    /** @see 开启纹理贴图功能 */
    public void openTexture(GL10 gl) {

	gl.glEnable(GL10.GL_TEXTURE_2D);
    }

    /** @see 关闭纹理贴图功能 */
    public void closeTexture(GL10 gl) {

	gl.glDisable(GL10.GL_TEXTURE_2D);
    }

}
