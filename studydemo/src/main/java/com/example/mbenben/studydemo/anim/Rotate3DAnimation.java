package com.example.mbenben.studydemo.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by MBENBEN on 2016/12/8.
 */

public class Rotate3DAnimation extends Animation {
    private final float fromDegrees;
    private final float toDefrees;
    private final float centerX;
    private final float centerY;
    private final float depthZ;
    private final boolean reverse;
    private Camera camera;

    public Rotate3DAnimation(float fromDegrees,float toDefrees,float centerX,
                             float centerY,float depthZ,boolean reverse){
        this.fromDegrees=fromDegrees;
        this.toDefrees=toDefrees;
        this.centerX=centerX;
        this.centerY=centerY;
        this.depthZ=depthZ;
        this.reverse=reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera=new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float _fromDegress=fromDegrees;
        float degress=_fromDegress+((toDefrees-_fromDegress))*interpolatedTime;
        final float _centerX=centerX;
        final float _centerY=centerY;
        final Camera _camera=camera;
        final Matrix matrix=t.getMatrix();

        camera.save();
        if (reverse){
            _camera.translate(0,0,depthZ*interpolatedTime);
        }else{
            camera.translate(0,0,depthZ*(1-interpolatedTime));
        }
        _camera.rotateY(degress);
        _camera.getMatrix(matrix);
        _camera.restore();

        matrix.preTranslate(-_centerX,-_centerY);
        matrix.postTranslate(_centerX,centerY);
    }
}
