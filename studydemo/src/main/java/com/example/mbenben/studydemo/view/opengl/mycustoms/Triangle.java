package com.example.mbenben.studydemo.view.opengl.mycustoms;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 18/1/15.
 */

public class Triangle {
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;

    private float[] vertices={
            0.0f,1.0f,0.0f,
            -1.0f,-1.0f,0.0f,
            1.0f,-1.0f,0.0f
    };
    private byte[] indices={0,1,2};

    public Triangle(){

        ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer=vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
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
