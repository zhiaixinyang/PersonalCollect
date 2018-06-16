package com.example.mbenben.studydemo.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import com.example.mbenben.studydemo.utils.FileUtils;
import com.example.mbenben.studydemo.utils.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by MDove on 2018/6/16.
 */
@SuppressWarnings("unused")
public class NativeLibraryLoader {
    private static final String TAG = "LibraryLoaderHelper";

    private static final String LIB_DIR = "lib_so";

    /**
     * avoid load one lib more than once
     */
    private static List<String> loadedLibNames = new ArrayList<>();

    /**
     * Try to load a native library
     *
     * @return true :library loaded
     * false : library not load
     */
    public static synchronized boolean loadLibrarySafely(Context context, String library) {
        if (loadedLibNames.contains(library)) {
            return true;
        }
        final File libFile = getWorkaroundLibFile(context, library);
        try {
            // normal load lib
            System.loadLibrary(library);
            loadedLibNames.add(library);
            return true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "loadLibrarySafety normal way error ", e);
            // load from apk file
            if (!libFile.exists() && !unpackLibrariesOnce(context, library)) {
                Log.d(TAG, "loadLibrarySafety unpackLibrariesOnce fail ");
                throw e;
            }
            try {
                System.load(libFile.getAbsolutePath());
                loadedLibNames.add(library);
                return true;
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "loadLibrarySafety in apk fail ", e2);
                throw e2;
            }
        }
    }

    /**
     * Returns the directory for holding extracted native libraries.
     * It may create the directory if it doesn't exist.
     *
     * @return the directory file object
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File getWorkaroundLibDir(Context context) {
        File file = new File(context.getFilesDir(), LIB_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File getWorkaroundLibFile(Context context, String library) {
        String libName = System.mapLibraryName(library);
        return new File(getWorkaroundLibDir(context), libName);
    }

    /**
     * Unpack native libraries from the APK file. The method is supposed to
     * be called only once. It deletes existing files in unpacked directory
     * before unpacking.
     *
     * @return true when unpacking was successful, false when failed or called
     * more than once.
     */
    @SuppressWarnings("deprecation")
    private static boolean unpackLibrariesOnce(Context context, String libName) {
        File libDir = getWorkaroundLibDir(context);
        deleteDirectorySync(libDir);
        ZipFile file;
        InputStream is = null;
        FileOutputStream os = null;

        try {
            ApplicationInfo appInfo = context.getApplicationInfo();
            file = new ZipFile(new File(appInfo.sourceDir), ZipFile.OPEN_READ);
            String jniNameInApk = "lib/" + Build.CPU_ABI + "/" +
                    System.mapLibraryName(libName);

            ZipEntry entry = file.getEntry(jniNameInApk);
            if (entry == null) {
                Log.e(TAG, appInfo.sourceDir + " doesn't have file " + jniNameInApk);
                final int lineCharIndex = Build.CPU_ABI.indexOf('-');
                jniNameInApk =
                        "lib/"
                                + Build.CPU_ABI.substring(0,
                                lineCharIndex > 0 ? lineCharIndex : Build.CPU_ABI.length()) + "/"
                                + System.mapLibraryName(libName);

                entry = file.getEntry(jniNameInApk);
                if (entry == null) {
                    Log.e(TAG, appInfo.sourceDir + " doesn't have file " + jniNameInApk);
                    file.close();
                    deleteDirectorySync(libDir);
                    return false;
                }
            }

            File outputFile = getWorkaroundLibFile(context, libName);
            Log.i(TAG, "Extracting native libraries into " + outputFile.getAbsolutePath());

            if (!outputFile.createNewFile()) {
                return false;
            }

            is = file.getInputStream(entry);
            os = new FileOutputStream(outputFile);
            IOUtils.copyLarge(is, os);
            // Change permission to rwx-rx-rx
            FileUtils.setPermissions(outputFile.getAbsolutePath(), 0x755);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Failed to unpack native libraries", e);
            deleteDirectorySync(libDir);
            return false;
        } catch (NoSuchMethodError e) {
            Log.e(TAG, "Failed to unpack native libraries", e);
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
    }

    private static void deleteDirectorySync(File dir) {
        try {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        Log.e(TAG, "Failed to remove " + file.getAbsolutePath());
                    }
                }
            }
            if (!dir.delete()) {
                Log.w(TAG, "Failed to remove " + dir.getAbsolutePath());
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to remove old libs, ", e);
        }
    }
}
