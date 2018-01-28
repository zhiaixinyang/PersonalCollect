package com.example.mbenben.studydemo.view.opengl.custom;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 18/1/15.
 */

public class Triangle {
    /**
     * OpenGL在底层的实现是C语言，与Java默认的数据存储字节顺序可能不同，即大端小端问题。
     * 因此，为了保险起见，在将数据传递给OpenGL之前，我们需要指明使用本机的存储顺序。
     */
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;

    private float[] vertices={
            0.0f,1.0f,0.0f,
            -1.0f,-1.0f,0.0f,
            1.0f,-1.0f,0.0f
    };
    private byte[] indices={0,1,2};

    public Triangle(){
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
        //以本机字节顺序来修改此缓冲区的字节顺序
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer=vbb.asFloatBuffer();
        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        vertexBuffer.put(vertices);
        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
        vertexBuffer.position(0);

        indexBuffer= ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_BYTE,indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
