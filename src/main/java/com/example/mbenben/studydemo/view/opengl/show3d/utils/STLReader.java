package com.example.mbenben.studydemo.view.opengl.show3d.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.mbenben.studydemo.view.opengl.show3d.model.Model3D;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by MDove on 18/1/26.
 */

public class STLReader {
    private StlLoadListener stlLoadListener;

    public Model3D parserBinStlInSDCard(String path)
            throws IOException {

        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        return parserBinStl(fis);
    }

    public Model3D parserBinStlInAssets(Context context, String fileName)
            throws IOException {

        InputStream is = context.getAssets().open(fileName);
        return parserBinStl(is);
    }

    //解析二进制的Stl文件
    public Model3D parserBinStl(InputStream in) throws IOException {
        if (stlLoadListener != null)
            stlLoadListener.onstart();
        Model3D model3D = new Model3D();
        //前面80字节是文件头，用于存贮文件名；
        in.skip(80);

        //紧接着用 4 个字节的整数来描述模型的三角面片个数
        byte[] bytes = new byte[4];
        in.read(bytes);// 读取三角面片个数
        int facetCount = Util.byte4ToInt(bytes, 0);
        model3D.setFacetCount(facetCount);
        if (facetCount == 0) {
            in.close();
            return model3D;
        }

        // 每个三角面片占用固定的50个字节
        byte[] facetBytes = new byte[50 * facetCount];
        // 将所有的三角面片读取到字节数组
        in.read(facetBytes);
        //数据读取完毕后，可以把输入流关闭
        in.close();


        parseModel(model3D, facetBytes);


        if (stlLoadListener != null)
            stlLoadListener.onFinished();
        return model3D;
    }

    /**
     * 解析模型数据，包括顶点数据、法向量数据、所占空间范围等
     */
    private void parseModel(Model3D model3D, byte[] facetBytes) {
        int facetCount = model3D.getFacetCount();
        /**
         *  每个三角面片占用固定的50个字节,50字节当中：
         *  三角片的法向量：（1个向量相当于一个点）*（3维/点）*（4字节浮点数/维）=12字节
         *  三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维）=36字节
         *  最后2个字节用来描述三角面片的属性信息
         * **/
        // 保存所有顶点坐标信息,一个三角形3个顶点，一个顶点3个坐标轴
        float[] verts = new float[facetCount * 3 * 3];
        // 保存所有三角面对应的法向量位置，
        // 一个三角面对应一个法向量，一个法向量有3个点
        // 而绘制模型时，是针对需要每个顶点对应的法向量，因此存储长度需要*3
        // 又同一个三角面的三个顶点的法向量是相同的，
        // 因此后面写入法向量数据的时候，只需连续写入3个相同的法向量即可
        float[] vnorms = new float[facetCount * 3 * 3];
        //保存所有三角面的属性信息
        short[] remarks = new short[facetCount];

        int stlOffset = 0;
        try {
            for (int i = 0; i < facetCount; i++) {
                if (stlLoadListener != null) {
                    stlLoadListener.onLoading(i, facetCount);
                }
                for (int j = 0; j < 4; j++) {
                    float x = Util.byte4ToFloat(facetBytes, stlOffset);
                    float y = Util.byte4ToFloat(facetBytes, stlOffset + 4);
                    float z = Util.byte4ToFloat(facetBytes, stlOffset + 8);
                    stlOffset += 12;

                    if (j == 0) {//法向量
                        vnorms[i * 9] = x;
                        vnorms[i * 9 + 1] = y;
                        vnorms[i * 9 + 2] = z;
                        vnorms[i * 9 + 3] = x;
                        vnorms[i * 9 + 4] = y;
                        vnorms[i * 9 + 5] = z;
                        vnorms[i * 9 + 6] = x;
                        vnorms[i * 9 + 7] = y;
                        vnorms[i * 9 + 8] = z;
                    } else {//三个顶点
                        verts[i * 9 + (j - 1) * 3] = x;
                        verts[i * 9 + (j - 1) * 3 + 1] = y;
                        verts[i * 9 + (j - 1) * 3 + 2] = z;

                        //记录模型中三个坐标轴方向的最大最小值
                        if (i == 0 && j == 1) {
                            model3D.minX = model3D.maxX = x;
                            model3D.minY = model3D.maxY = y;
                            model3D.minZ = model3D.maxZ = z;
                        } else {
                            model3D.minX = Math.min(model3D.minX, x);
                            model3D.minY = Math.min(model3D.minY, y);
                            model3D.minZ = Math.min(model3D.minZ, z);
                            model3D.maxX = Math.max(model3D.maxX, x);
                            model3D.maxY = Math.max(model3D.maxY, y);
                            model3D.maxZ = Math.max(model3D.maxZ, z);
                        }
                    }
                }
                short r = Util.byte2ToShort(facetBytes, stlOffset);
                stlOffset = stlOffset + 2;
                remarks[i] = r;
            }
        } catch (Exception e) {
            if (stlLoadListener != null) {
                stlLoadListener.onFailure(e);
            } else {
                e.printStackTrace();
            }
        }
        //将读取的数据设置到Model对象中
        model3D.setVerts(verts);
        model3D.setVnorms(vnorms);
        model3D.setRemarks(remarks);

    }

    public static interface StlLoadListener {
        void onstart();

        void onLoading(int cur, int total);

        void onFinished();

        void onFailure(Exception e);
    }

    private void parseTexture(Model3D model, byte[] textureBytes) {
        int facetCount = model.getFacetCount();
        // 三角面个数有三个顶点，一个顶点对应纹理二维坐标
        float[] textures = new float[facetCount * 3 * 2];
        int textureOffset = 0;
        for (int i = 0; i < facetCount * 3; i++) {
            //第i个顶点对应的纹理坐标
            //tx和ty的取值范围为[0,1],表示的坐标位置是在纹理图片上的对应比例
            float tx = Util.byte4ToFloat(textureBytes, textureOffset);
            float ty = Util.byte4ToFloat(textureBytes, textureOffset + 4);

            textures[i * 2] = tx;
            //我们的pxy文件原点是在左下角，因此需要用1减去y坐标值
            textures[i * 2 + 1] = 1 - ty;

            textureOffset += 8;
        }
        model.setTextures(textures);
    }

    public Model3D parseStlWithTexture(InputStream stlInput, InputStream textureInput) throws IOException {
        Model3D model = parseBinStl(stlInput);
        int facetCount = model.getFacetCount();
        // 三角面片有3个顶点，一个顶点有2个坐标轴数据，每个坐标轴数据是float类型（4字节）
        byte[] textureBytes = new byte[facetCount * 3 * 2 * 4];
        textureInput.read(textureBytes);// 将所有纹理坐标读出来
        parseTexture(model, textureBytes);
        return model;
    }

    public Model3D parseBinStl(InputStream in) throws IOException {
        if (stlLoadListener != null)
            stlLoadListener.onstart();
        Model3D model = new Model3D();
        //前面80字节是文件头，用于存贮文件名；
        in.skip(80);

        //紧接着用 4 个字节的整数来描述模型的三角面片个数
        byte[] bytes = new byte[4];
        in.read(bytes);// 读取三角面片个数
        int facetCount = Util.byte4ToInt(bytes, 0);
        model.setFacetCount(facetCount);
        if (facetCount == 0) {
            in.close();
            return model;
        }

        // 每个三角面片占用固定的50个字节
        byte[] facetBytes = new byte[50 * facetCount];
        // 将所有的三角面片读取到字节数组
        in.read(facetBytes);
        //数据读取完毕后，可以把输入流关闭
        in.close();


        parseModel(model, facetBytes);

        if (stlLoadListener != null)
            stlLoadListener.onFinished();
        return model;
    }

    public Model3D parseBinStlInAssets(Context context, String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        return parseBinStl(is);
    }

    public Model3D parserStlWithTextureInAssets(Context context, String name) throws IOException {
        AssetManager am = context.getAssets();
        InputStream stlInput = am.open(name + ".stl");
        InputStream textureInput = am.open(name + ".pxy");
        Model3D model = parseStlWithTexture(stlInput, textureInput);
        model.setPictureName(name + ".jpg");
        return model;
    }
}
