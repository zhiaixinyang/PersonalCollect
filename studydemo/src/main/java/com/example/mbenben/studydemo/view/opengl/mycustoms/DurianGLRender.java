package com.example.mbenben.studydemo.view.opengl.mycustoms;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 18/1/15.
 */

public class DurianGLRender implements GLSurfaceView.Renderer {

    private Context mContext;

    private Triangle triangle;
    private Square square;

    private Triangle ctriangle;
    private Square csquare;

    public DurianGLRender(Context context){
        mContext=context;

        triangle=new Triangle();
        square=new Square();

        //ctriangle=new org.durian.durianopengl1.draw2d.color.Triangle();
        //csquare=new org.durian.durianopengl1.draw2d.color.Square();

    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glDisable(GL10.GL_DITHER);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(height==0){
            height=1;
        }
        float aspect=(float)width/height;

        gl.glViewport(0,0,width,height);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl,45,aspect,0.1f,100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glTranslatef(-3.0f,0.0f,-6.0f);
        triangle.draw(gl);

        gl.glTranslatef(3.0f,0.0f,1.0f);
        square.draw(gl);

        gl.glTranslatef(-5.0f,0.0f,-6.0f);
        //ctriangle.draw(gl);

        gl.glTranslatef(5.0f,0.0f,1.0f);
        //csquare.draw(gl);

    }

}
