package com.example.mbenben.studydemo.view.opengl.show3d.utils;

import com.example.mbenben.studydemo.view.opengl.show3d.model.Model3D;
import com.example.mbenben.studydemo.view.opengl.show3d.model.MyPoint;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by MDove on 18/1/26.
 */

public class Util {
    public static FloatBuffer floatToBuffer(float[] a) {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(a.length * 4);
        //数组排序用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(a);
        buffer.position(0);
        return buffer;
    }

    public static int byte4ToInt(byte[] bytes, int offset) {
        int b3 = bytes[offset + 3] & 0xFF;
        int b2 = bytes[offset + 2] & 0xFF;
        int b1 = bytes[offset + 1] & 0xFF;
        int b0 = bytes[offset + 0] & 0xFF;
        return (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
    }

    public static short byte2ToShort(byte[] bytes, int offset) {
        int b1 = bytes[offset + 1] & 0xFF;
        int b0 = bytes[offset + 0] & 0xFF;
        return (short) ((b1 << 8) | b0);
    }

    public static float byte4ToFloat(byte[] bytes, int offset) {
        return Float.intBitsToFloat(byte4ToInt(bytes, offset));
    }

    public static float getR(List<Model3D> models) {
        MyPoint minP = modelsBorder(models, true);
        MyPoint maxP = modelsBorder(models, false);
        float rx = maxP.x - minP.x;
        float ry = maxP.y - minP.y;
        float rz = maxP.z - minP.z;

        float r = (float) (Math.sqrt(rx * rx + ry * ry + rz * rz) / 2);

        return r;
    }

    private static MyPoint modelsBorder(List<Model3D> models, boolean isMin) {
        MyPoint p;
        if (isMin)
            p = new MyPoint(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        else
            p = new MyPoint(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

        for (Model3D model : models) {
            if (isMin) {//如果取的是最小的xyz
                if (model.minX < p.x)
                    p.x = model.minX;
                if (model.minY < p.y)
                    p.y = model.minY;
                if (model.minZ < p.z)
                    p.z = model.minZ;
            } else {//如果取的是最大的xyz
                if (model.maxX > p.x)
                    p.x = model.minX;
                if (model.maxY > p.y)
                    p.y = model.minY;
                if (model.maxZ > p.z)
                    p.z = model.minZ;
            }
        }

        return p;
    }

    public static MyPoint getCenter(List<Model3D> models) {
        MyPoint min = modelsBorder(models, true);
        MyPoint max = modelsBorder(models, false);
        float cx = min.x + (max.x - min.x) / 2;
        float cy = min.y + (max.y - min.y) / 2;
        float cz = min.z + (max.z - min.z) / 2;
        return new MyPoint(cx, cy, cz);
    }

}
