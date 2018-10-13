package com.example.mbenben.studydemo.basenote.proxy.hook;

import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by MDove on 2018/8/19.
 */

public class ServiceManager {
    private static Method sGetServiceMethod;
    private static Map<String, IBinder> sCacheService;
    private static Class sServiceManager;

    static {
        try {
            sServiceManager = Class.forName("android.os.ServiceManager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IBinder getService(String serviceName) {
        if (sServiceManager == null) {
            return null;
        }

        if (sGetServiceMethod == null) {
            try {
                sGetServiceMethod = sServiceManager.getDeclaredMethod("getService", String.class);
                sGetServiceMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if (sGetServiceMethod != null) {
            try {
                return (IBinder) sGetServiceMethod.invoke(null, serviceName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void setService(String serviceName, IBinder service) {
        if (sServiceManager == null) {
            return;
        }

        if (sCacheService == null) {
            try {
                Field sCache = sServiceManager.getDeclaredField("sCache");
                sCache.setAccessible(true);
                sCacheService = (Map<String, IBinder>) sCache.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sCacheService.remove(serviceName);
        sCacheService.put(serviceName, service);
    }
}
