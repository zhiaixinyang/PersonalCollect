package com.example.mbenben.studydemo.view.opengl.flag;

import java.nio.FloatBuffer;

/**
 * @see 常用参数枚举类
 * @author (虾米吃鱼肉干)
 *
 */
public interface MyEnum {

    /** 环境光 */
    FloatBuffer mAmbientLight = FloatBuffer.wrap(new float[] {//
	    0.1f, 0.1f, 0.1f, 1.0f, });
    /** 散射光 */
    FloatBuffer mDiffuseLight = FloatBuffer.wrap(new float[] {//
	    0.3f, 0.3f, 0.3f, 1.0f, });

    /** 白色 **/
    FloatBuffer mSpecularLight = FloatBuffer.wrap(new float[] {//
	    1.0f, 1.0f, 1.0f, 1.0f, });
    /** 红色光线 **/
    FloatBuffer mRedSpecularLight = FloatBuffer.wrap(new float[] {//
	    1.0f, 0.0f, 0.0f, 1.0f, });
    /** 蓝色光线 **/
    FloatBuffer mBlueSpecularLight = FloatBuffer.wrap(new float[] {//
	    0.0f, 0.0f, 1.0f, 1.0f, });
    /** 绿色光线 ***/
    FloatBuffer mGreenSpecularLight = FloatBuffer.wrap(new float[] {//
	    0.0f, 1.0f, 0.0f, 1.0f, });
    /** 黄色光线 **/
    FloatBuffer mYellowSpecularLight = FloatBuffer.wrap(new float[] {//
	    1.0f, 1.0f, 0.0f, 1.0f, });
    /** 紫色光线 **/
    FloatBuffer mZiSpecularLight = FloatBuffer.wrap(new float[] {//
	    1.0f, 0.0f, 1.0f, 1.0f, });

    /** 光的指向向量 指向Z轴而下的光源方向 */
    FloatBuffer mSpotDirection = FloatBuffer.wrap(new float[] { //
	    0.0f, 0.0f, -1.0f,// 
	    });

    /** 材质效果 default0 0.3f */
    FloatBuffer mMeterialdefault0 = FloatBuffer.wrap(new float[] { //
	    0.3f, 0.3f, 0.3f, 1.0f,// 材质的属性
	    });
    /** 材质效果 default0 0.7f */
    FloatBuffer mMeterialdefault1 = FloatBuffer.wrap(new float[] { //
	    0.7f, 0.7f, 0.7f, 1.0f,// 材质的属性
	    });
    /** 材质效果 default0 1.0f */
    FloatBuffer mMeterialdefault2 = FloatBuffer.wrap(new float[] { //
	    1.0f, 1.0f, 1.0f, 1.0f,// 材质的属性
	    });

    /** 材质效果 环境光 */
    FloatBuffer mMeterialAmbient = FloatBuffer.wrap(new float[] { //
	    0.3f, 0.3f, 0.3f, 1.0f,// 材质的属性
	    });
    /** 材质效果 散射光 */
    FloatBuffer mMeterialDiffuse = FloatBuffer.wrap(new float[] { //
	    0.71f, 0.7f, 0.7f, 0.5f,// 材质的属性
	    });
    /** 材质效果 镜面反射 */
    FloatBuffer mMeterialSpecular = FloatBuffer.wrap(new float[] { //
	    1.0f, 1.0f, 1.0f, 1.0f,// 材质的属性
	    });

    /** 自发光材质 */
    FloatBuffer mEmission = FloatBuffer.wrap(new float[] {//
	    0.1f, 0.1f, 0.1f, 1.0f,// 自发光
	    });

    /** 光源位置在正上方 */
    float[] LIGHT_POSITION_TOP = new float[] { 0.0f, 3.0f, 0.0f, 1.0f, };
    /** 光源位置在正下方 */
    float[] LIGHT_POSITION_BOTTOM = new float[] { 0.0f, -3.0f, -12.0f, 1.0f, };

    /** 光源位置在正左方 */
    float[] LIGHT_POSITION_LEFT = new float[] { 0.0f, -3.0f, -12.0f, 1.0f, };
    /** 光源位置在正右方 */
    float[] LIGHT_POSITION_RIGHT = new float[] { 0.0f, 3.0f, -12.0f, 1.0f, };

    /** 光源位置在正前方 */
    float[] LIGHT_POSITION_FRONT = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, };
    /** 光源位置在正后方 */
    float[] LIGHT_POSITION_BACK = new float[] { 0.0f, 0.0f, -10.0f, 1.0f, };

    /** 光源位置 在 右上角 + */
    float[] LIGHT_POSITION_TOP_RIGHT0 = new float[] { 1.7f, 1.45f, 0.0f, 1.0f, };
    /** 光源位置 在 左上角+ */
    float[] LIGHT_POSITION_TOP_LEFT0 = new float[] { -1.7f, 1.45f, 0.0f, 1.0f, };
    /** 光源位置 在 右下角 + */
    float[] LIGHT_POSITION_BOTTOM_RIGHT0 = new float[] { 1.7f, -1.45f, 0.0f,
	    1.0f, };
    /** 光源位置 在 左下角+ */
    float[] LIGHT_POSITION_BOTTOM_LEFT0 = new float[] { -1.7f, -1.45f, 0.0f,
	    1.0f, };

    /** 光源位置 在 右上角 - */
    float[] LIGHT_POSITION_TOP_RIGHT1 = new float[] { 3.0f, 3.0f, -10.0f, 1.0f, };
    /** 光源位置 在 左上角- */
    float[] LIGHT_POSITION_TOP_LEFT1 = new float[] { -3.0f, 3.0f, -10.0f, 1.0f, };
    /** 光源位置 在 右下角 - */
    float[] LIGHT_POSITION_BOTTOM_RIGHT1 = new float[] { 3.0f, -3.0f, -10.0f,
	    1.0f, };
    /** 光源位置 在 左下角- */
    float[] LIGHT_POSITION_BOTTOM_LEFT1 = new float[] { -3.0f, -3.0f, -10.0f,
	    1.0f, };

}