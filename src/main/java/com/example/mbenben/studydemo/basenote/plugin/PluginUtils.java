package com.example.mbenben.studydemo.basenote.plugin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by MBENBEN on 2017/6/14.
 *
 * 原项目博客地址：http://blog.csdn.net/u010687392/article/details/47121729?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
 */

public class PluginUtils {
    private List<HashMap<String,String>> datas;
    public static final String apkDir = Environment.getExternalStorageDirectory().getPath()+File.separator+"plugin";

    //拷贝apk文件至sd卡plugin目录下
    public static void copyApkFile(Context context, String apkName) {
        File file = new File(apkDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File apk = new File(apkDir + File.separator + apkName);
        try {
            if(apk.exists()){
                return;
            }
            FileOutputStream fos = new FileOutputStream(apk);
            InputStream is = context.getResources().getAssets().open(apkName);
            BufferedInputStream bis = new BufferedInputStream(is);
            int len = -1;
            byte[] by = new byte[1024];
            while ((len = bis.read(by)) != -1) {
                fos.write(by, 0, len);
                fos.flush();
            }
            fos.close();
            is.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载apk获得内部资源
     * @param apkDir apk目录
     * @param apkName apk名字,带.apk
     * @throws Exception
     */
    private void dynamicLoadApk(Context context,String apkDir, String apkName, String apkPackageName) throws Exception {
        File optimizedDirectoryFile = context.getDir("dex", Context.MODE_PRIVATE);//在应用安装目录下创建一个名为app_dex文件夹目录,如果已经存在则不创建
        //参数：1、包含dex的apk文件或jar文件的路径，2、apk、jar解压缩生成dex存储的目录，3、本地library库目录，一般为null，4、父ClassLoader
        DexClassLoader dexClassLoader = new DexClassLoader(apkDir+File.separator+apkName, optimizedDirectoryFile.getPath(), null, ClassLoader.getSystemClassLoader());
        Class<?> clazz = dexClassLoader.loadClass(apkPackageName + ".R$mipmap");//通过使用apk自己的类加载器，反射出R类中相应的内部类进而获取我们需要的资源id
        Field field = clazz.getDeclaredField("one");//得到名为one的这张图片字段
        int resId = field.getInt(R.id.class);//得到图片id
        Resources mResources = getPluginResources(context,apkName);//得到插件apk中的Resource
        if (mResources != null) {
            //通过插件apk中的Resource得到resId对应的资源
        }
    }

    /**
     * @param apkName
     * @return 得到对应插件的Resource对象
     */
    private Resources getPluginResources(Context context,String apkName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);//反射调用方法addAssetPath(String path)
            //第二个参数是apk的路径：Environment.getExternalStorageDirectory().getPath()+File.separator+"plugin"+File.separator+"apkplugin.apk"
            addAssetPath.invoke(assetManager, apkDir+File.separator+apkName);//将未安装的Apk文件的添加进AssetManager中，第二个参数为apk文件的路径带apk名
            Resources superRes = context.getResources();
            Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            return mResources;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<HashMap<String,String>> searchAllPlugin(Context context,String apkDir){
        List<HashMap<String,String>> lists = new ArrayList<>();
        List<String> apkName = new ArrayList<>();

        File dir = new File(apkDir);
        if(dir.isDirectory()){
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".apk");
                }
            };
            //过滤掉其它文件，只留apk结尾的
            File[] apks =dir.listFiles(filter);
            for (int i = 0; i < apks.length; i++) {
                File temp = apks[i];
                apkName.add(temp.getName());//存储apk名称

                String[] info = getUninstallApkInfo(context,apkDir+File.separator+temp.getName());
                HashMap<String,String> map = new HashMap<>();
                map.put("label",info[0]);
                map.put("pkgName",info[1]);
                lists.add(map);
                map = null;
            }
        }
        return lists;
    }
    /**
     * 列出应用中的可用插件,由于只是示例演示，该demo就简单的把apk插件放在assets目录下，然后运行时候拷贝到sd卡的plugin目录下
     */
    private void showCanEnabledPlugin(Context context,List<HashMap<String,String>> datas) {
        if(datas==null||datas.isEmpty()){
            Toast.makeText(context, "没有找到，请先下载插件！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取未安装apk的信息
     * @param context
     * @param archiveFilePath apk文件的path
     * @return
     */
    private static String[] getUninstallApkInfo(Context context, String archiveFilePath) {
        String[] info = new String[2];
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            String versionName = pkgInfo.versionName;//版本号
            Drawable icon = pm.getApplicationIcon(appInfo);//图标
            String appName = pm.getApplicationLabel(appInfo).toString();//app名称
            String pkgName = appInfo.packageName;//包名
            info[0] = appName;
            info[1] = pkgName;
        }
        return info;
    }
}
