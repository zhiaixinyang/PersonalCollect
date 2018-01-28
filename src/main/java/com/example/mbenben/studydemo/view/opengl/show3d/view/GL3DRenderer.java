package com.example.mbenben.studydemo.view.opengl.show3d.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.mbenben.studydemo.view.opengl.show3d.model.Model3D;
import com.example.mbenben.studydemo.view.opengl.show3d.model.MyPoint;
import com.example.mbenben.studydemo.view.opengl.show3d.utils.STLReader;
import com.example.mbenben.studydemo.view.opengl.show3d.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by MDove on 18/1/26.
 */

public class GL3DRenderer implements GLSurfaceView.Renderer {
    private Context mContext;
    private Model3D model3D;
    private MyPoint mCenterPoint;
    private MyPoint eye = new MyPoint(0, 0, -3);
    //控制模型的初始位置
    private MyPoint up = new MyPoint(0, -1, 0);
    private MyPoint center = new MyPoint(0, 0, 0);
    private float mScalef = 1;
    private float mDegree = 0;

    //光源设置()
    private float[] ambient = {0.9f, 0.9f, 0.9f, 1.0f};//环境光颜色（光源没有照到的地方）
    private float[] diffuse = {0.5f, 0.5f, 0.5f, 1.0f};//漫反射（镜面反射四周的反射光）
    private float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};//镜面反射（被光源直接照射到并反射的光）
    private float[] lightPosition = {0.5f, 0.5f, 0.5f, 0.0f};

    //材质设置(材质的色彩不同，极大的影响我们最终看到的效果)
    float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f};
    float[] materialDiff = {0.0f, 0.0f, 1.0f, 1.0f};//漫反射设置蓝色
    float[] materialSpec = {1.0f, 0.5f, 0.0f, 1.0f};

    private List<Model3D> models = new ArrayList<>();

    public GL3DRenderer(Context context) {
        mContext = context;
        try {
            model3D = new STLReader().parserBinStlInAssets(context, "my3d.stl");

            STLReader reader = new STLReader();
            for (int i = 1; i <= 6; i++) {
                Model3D model = reader.parserStlWithTextureInAssets(context, "chuwang/" + i);
                models.add(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotate(float degree) {
        mDegree = degree;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        // 清除屏幕和深度缓存
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//
//        gl.glLoadIdentity();// 重置当前的模型观察矩阵
//
//        //眼睛对着原点看
//        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
//                center.y, center.z, up.x, up.y, up.z);
//
//        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
//        gl.glRotatef(mDegree, 0, 1, 0);
//
//        //将模型放缩到View刚好装下
//        gl.glScalef(mScalef, mScalef, mScalef);
//        //把模型移动到原点
//        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y,
//                -mCenterPoint.z);
//
//
//        //允许给每个顶点设置法向量
//        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//        // 允许设置顶点
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        // 允许设置颜色
//
//        //设置法向量数据源
//        gl.glNormalPointer(GL10.GL_FLOAT, 0, model3D.getVnormBuffer());
//        // 设置三角形顶点数据源
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model3D.getVertBuffer());
//
//        // 绘制三角形
//        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model3D.getFacetCount() * 3);
//
//        // 取消顶点设置
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        // 取消法向量设置
//        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);


        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();// 重置当前的模型观察矩阵

        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);

        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
        gl.glRotatef(mDegree, 0, 1, 0);

        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y,
                -mCenterPoint.z);


        //===================begin==============================//
        for (Model3D model : models) {
            //开启贴纹理功能
            gl.glEnable(GL10.GL_TEXTURE_2D);
            //根据ID绑定对应的纹理
            gl.glBindTexture(GL10.GL_TEXTURE_2D, model.getTextureIds()[0]);
            //启用相关功能
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            //开始绘制
            gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, model.getTextureBuffer());

            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount() * 3);

            //关闭当前模型贴纹理，即将纹理id设置为0
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

            //关闭对应的功能
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(width, height)指定了视口的大小
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影矩阵
        gl.glLoadIdentity(); // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度缓存
//        gl.glClearDepthf(1.0f); // 设置深度缓存值
//        gl.glDepthFunc(GL10.GL_LEQUAL); // 设置深度缓存比较函数
//        gl.glShadeModel(GL10.GL_SMOOTH);// 设置阴影模式GL_SMOOTH
//        float r = model3D.getR();
//        //r是半径，不是直径，因此用0.5/r可以算出放缩比例
//        mScalef = 0.5f / r;
//        mCenterPoint = model3D.getCentrePoint();


        gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度缓存
        gl.glClearColor(0f, 0f, 0f, 0f);// 设置深度缓存值
        gl.glDepthFunc(GL10.GL_LEQUAL); // 设置深度缓存比较函数
        gl.glShadeModel(GL10.GL_SMOOTH);// 设置阴影模式GL_SMOOTH

        //开启光源
        openLight(gl);
        enableMaterial(gl);

        //初始化相关数据
        initConfigData(gl);
    }

    private void initConfigData(GL10 gl) {
        float r = Util.getR(models);
        mScalef = 0.5f / r;
        mCenterPoint = Util.getCenter(models);

        //为每个模型绑定纹理
        for (Model3D model : models) {
            loadTexture(gl, model, true);
        }
    }

    private void loadTexture(GL10 gl, Model3D model, boolean isAssets) {
        Bitmap bitmap = null;
        try {
            // 打开图片资源
            if (isAssets) {//如果是从assets中读取
                bitmap = BitmapFactory.decodeStream(mContext.getAssets().open(model.getPictureName()));
            } else {//否则就是从SD卡里面读取
                bitmap = BitmapFactory.decodeFile(model.getPictureName());
            }
            // 生成一个纹理对象，并将其ID保存到成员变量 texture 中
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            model.setTextureIds(textures);

            // 将生成的空纹理绑定到当前2D纹理通道
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

            // 设置2D纹理通道当前绑定的纹理的属性
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);

            // 将bitmap应用到2D纹理通道当前绑定的纹理中
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (bitmap != null)
                bitmap.recycle();
        }
    }

    public void openLight(GL10 gl) {
        //启用光源
        gl.glEnable(GL10.GL_LIGHTING);
        //启用0号光源（GL_LIGHTING）
        gl.glEnable(GL10.GL_LIGHT0);
        /**
         * 各参数含义：
         *     1、几号光源（0号是白色，其他默认是黑色）
         *     2、光源参数，比如GL10.GL_AMBIENT代指环境光
         *     3、对应的光的颜色（A,R,G,B）
         */
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, Util.floatToBuffer(ambient));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, Util.floatToBuffer(diffuse));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, Util.floatToBuffer(specular));
        //设置光源位置，（x,y,z,w）如果w为0表示平行光
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, Util.floatToBuffer(lightPosition));
    }

    public void enableMaterial(GL10 gl) {
        //材料对环境光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, Util.floatToBuffer(materialAmb));
        //散射光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, Util.floatToBuffer(materialDiff));
        //镜面光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, Util.floatToBuffer(materialSpec));
    }

    public void setScale(float scale) {
        mScalef = scale;
    }
}