package com.example.mbenben.studydemo.utils;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MDove on 2018/6/16.
 */
@SuppressWarnings("unused")
public class ReflectUtils {
    private final static String LOG_TAG = "Reflect";

    private final static Map<Class<?>, Class<?>> PRIMITIVE_MAP =
            new HashMap<>();

    static {
        PRIMITIVE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_MAP.put(Character.class, char.class);
        PRIMITIVE_MAP.put(Short.class, short.class);
        PRIMITIVE_MAP.put(Integer.class, int.class);
        PRIMITIVE_MAP.put(Float.class, float.class);
        PRIMITIVE_MAP.put(Long.class, long.class);
        PRIMITIVE_MAP.put(Double.class, double.class);
        PRIMITIVE_MAP.put(boolean.class, boolean.class);
        PRIMITIVE_MAP.put(byte.class, byte.class);
        PRIMITIVE_MAP.put(char.class, char.class);
        PRIMITIVE_MAP.put(short.class, short.class);
        PRIMITIVE_MAP.put(int.class, int.class);
        PRIMITIVE_MAP.put(float.class, float.class);
        PRIMITIVE_MAP.put(long.class, long.class);
        PRIMITIVE_MAP.put(double.class, double.class);
    }

    public static Field getDeclaredField(Object o, String fieldName) {
        Field field = null;
        Class<?> clazz = o.getClass();
        for (; clazz != Object.class && field == null; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        field.setAccessible(true);
        return field;
    }

    public static List<Field> getDeclaredFields(Object o) {
        Class<?> clazz = o.getClass();
        List<Field> result = new ArrayList<>();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] declaredFields = clazz.getDeclaredFields();
            result.addAll(Arrays.asList(declaredFields));
        }
        return result;
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public static <T> T getField(Object targetInstance, String fieldName) {
        try {
            return getFieldOrThrow(targetInstance, fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getFieldOrThrow(Object targetInstance, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = targetInstance.getClass();
        Field f = null;
        while (f == null) {
            try {
                f = cls.getDeclaredField(fieldName);
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
            if (cls == null) {
                throw new NoSuchFieldException();
            }
        }
        f.setAccessible(true);
        //noinspection unchecked
        return (T) f.get(targetInstance);
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public static void setField(Object targetInstance, String fieldName, Object val) {
        try {
            setFieldOrThrow(targetInstance, fieldName, val);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldOrThrow(Object targetInstance, String fieldName, Object val)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = targetInstance.getClass();
        Field f = null;
        while (f == null) {
            try {
                f = cls.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
            if (cls == null) {
                throw new NoSuchFieldException();
            }
        }
        f.setAccessible(true);
        f.set(targetInstance, val);
    }

    public static <T> T callMethod(Object targetInstance, String methodName,
                                   Object... args) {
        try {
            return callMethodOrThrow(targetInstance, methodName, args);
        } catch (Exception e) {
            Log.w(LOG_TAG, "Meet exception when call Method '" + methodName
                    + "' in " + targetInstance, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T callMethodOrThrow(Object targetInstance,
                                          String methodName, Object... args) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        final Class<?> clazz = targetInstance.getClass();

        Method method = getDeclaredMethod(clazz, methodName,
                getParameterTypes(args));
        return (T) method.invoke(targetInstance, getParameters(args));
    }

    public static <T> T callStaticMethod(String className, String methodName,
                                         Object... args) {
        try {
            Class<?> clazz = Class.forName(className);
            return callStaticMethodOrThrow(clazz, methodName, args);
        } catch (Exception e) {
            Log.w(LOG_TAG, "Meet exception when call Method '" + methodName
                    + "' in " + className, e);
            return null;
        }
    }

    private static Method getDeclaredMethod(final Class<?> clazz, String name,
                                            Class<?>... parameterTypes) throws NoSuchMethodException,
            SecurityException {
        Method[] methods = clazz.getDeclaredMethods();
        Method method = findMethodByName(methods, name, parameterTypes);
        if (method == null) {
            if (clazz.getSuperclass() != null) {
                // find in parent
                return getDeclaredMethod(clazz.getSuperclass(), name, parameterTypes);
            } else {
                throw new NoSuchMethodException();
            }
        }
        method.setAccessible(true);
        return method;
    }

    private static Method findMethodByName(Method[] list, String name,
                                           Class<?>[] parameterTypes) {
        if (name == null) {
            throw new NullPointerException("Method name must not be null.");
        }

        for (Method method : list) {
            if (method.getName().equals(name)
                    && compareClassLists(method.getParameterTypes(),
                    parameterTypes)) {
                return method;
            }
        }
        return null;
    }

    private static boolean compareClassLists(Class<?>[] a, Class<?>[] b) {
        if (a == null) {
            return (b == null) || (b.length == 0);
        }

        if (b == null) {
            return (a.length == 0);
        }

        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; ++i) {
            // if a[i] and b[i] is not same, return false
            if (!(a[i].isAssignableFrom(b[i])
                    || (PRIMITIVE_MAP.containsKey(a[i]) && PRIMITIVE_MAP.get(
                    a[i]).equals(PRIMITIVE_MAP.get(b[i]))))) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T callStaticMethodOrThrow(final String className,
                                                String methodName, Object... args) throws
            SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Method method = getDeclaredMethod(clazz, methodName,
                getParameterTypes(args));

        return (T) method.invoke(null, getParameters(args));
    }

    @SuppressWarnings("unchecked")
    public static <T> T callStaticMethodOrThrow(final Class<?> clazz,
                                                String methodName, Object... args) throws
            SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method method = getDeclaredMethod(clazz, methodName,
                getParameterTypes(args));

        return (T) method.invoke(null, getParameters(args));
    }

    public static <T> T newInstance(Class<?> clazz, Object... args) {
        try {
            return newInstanceOrThrow(clazz, args);
        } catch (Exception e) {
            Log.w(LOG_TAG,
                    "Meet exception when make instance as a "
                            + clazz.getSimpleName(), e);
            return null;
        }
    }

    public static <T> T newEmptyInstance(Class<?> clazz) {
        try {
            return newEmptyInstanceOrThrow(clazz);
        } catch (Exception e) {
            Log.w(LOG_TAG,
                    "Meet exception when make instance as a "
                            + clazz.getSimpleName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T newEmptyInstanceOrThrow(Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException,
            InstantiationException, ClassNotFoundException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new IllegalArgumentException("Can't get even one available constructor for " + clazz);
        }
        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);
        Class<?>[] types = constructor.getParameterTypes();
        if (types == null || types.length == 0) {
            return (T) constructor.newInstance();
        }
        Object[] params = new Object[types.length];
        for (int i = 0; i < types.length; ++i) {
            params[i] = getDefaultValue(types[i]);
        }
        return (T) constructor.newInstance(params);
    }

    private static Object getDefaultValue(Class<?> clazz) {
        if (int.class.equals(clazz) || Integer.class.equals(clazz)
                || byte.class.equals(clazz) || Byte.class.equals(clazz)
                || short.class.equals(clazz) || Short.class.equals(clazz)
                || long.class.equals(clazz) || Long.class.equals(clazz)
                || double.class.equals(clazz) || Double.class.equals(clazz)
                || float.class.equals(clazz) || Float.class.equals(clazz)) {
            return 0;
        } else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
            return false;
        } else if (char.class.equals(clazz) || Character.class.equals(clazz)) {
            return '\0';
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstanceOrThrow(Class<?> clazz, Object... args)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = clazz
                .getConstructor(getParameterTypes(args));
        return (T) constructor.newInstance(getParameters(args));
    }

    public static Object newInstance(String className, Object... args) {
        try {
            return newInstanceOrThrow(className, args);
        } catch (Exception e) {
            Log.w(LOG_TAG, "Meet exception when make instance as a "
                    + className, e);
            return null;
        }
    }

    public static Object newInstanceOrThrow(String className, Object... args)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        return newInstanceOrThrow(Class.forName(className), getParameters(args));
    }

    private static Class<?>[] getParameterTypes(Object... args) {
        Class<?>[] parameterTypes = null;

        if (args != null && args.length > 0) {
            parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                Object param = args[i];
                if (param != null && param instanceof JavaParam<?>) {
                    parameterTypes[i] = ((JavaParam<?>) param).clazz;
                } else {
                    parameterTypes[i] = param == null ? null : param.getClass();
                }
            }
        }
        return parameterTypes;
    }

    private static Object[] getParameters(Object... args) {
        Object[] parameters = null;

        if (args != null && args.length > 0) {
            parameters = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                Object param = args[i];
                if (param != null && param instanceof JavaParam<?>) {
                    parameters[i] = ((JavaParam<?>) param).obj;
                } else {
                    parameters[i] = param;
                }
            }
        }
        return parameters;
    }

    public static class JavaParam<T> {
        public final Class<? extends T> clazz;
        public final T obj;

        public JavaParam(Class<? extends T> clazz, T obj) {
            this.clazz = clazz;
            this.obj = obj;
        }
    }
}
