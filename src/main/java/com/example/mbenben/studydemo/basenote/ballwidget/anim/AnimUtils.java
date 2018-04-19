package com.example.mbenben.studydemo.basenote.ballwidget.anim;

/**
 * @author wangwei on 2017/11/30.
 *         wangwei@jiandaola.com
 */
public class AnimUtils {

    /**
     * Rotate point P around center point C.
     *
     * @param pX             x coordinate of point P
     * @param pY             y coordinate of point P
     * @param cX             x coordinate of point C
     * @param cY             y coordinate of point C
     * @param angleInDegrees rotation angle in degrees
     * @return new x coordinate
     */
    public static float rotateX(float pX, float pY, float cX, float cY, float angleInDegrees) {
        double angle = Math.toRadians(angleInDegrees);
        return (float) (Math.cos(angle) * (pX - cX) - Math.sin(angle) * (pY - cY) + cX);
    }

    /**
     * Rotate point P around center point C.
     *
     * @param pX             x coordinate of point P
     * @param pY             y coordinate of point P
     * @param cX             x coordinate of point C
     * @param cY             y coordinate of point C
     * @param angleInDegrees rotation angle in degrees
     * @return new y coordinate
     */
    public static float rotateY(float pX, float pY, float cX, float cY, float angleInDegrees) {
        double angle = Math.toRadians(angleInDegrees);
        return (float) (Math.sin(angle) * (pX - cX) + Math.cos(angle) * (pY - cY) + cY);
    }
}
