package com.frewen.freeutils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.frewen.freeutils.config.FreeConfig;


/**
 * 首选项配置文件工具类
 * 
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2015-12-13]
 * @since [产品/模块版本]
 */
public class FreeSP {

    private static SharedPreferences sharedPreferences;

    /**
     * 默认路径
     * 
     * @param context
     * @return
     */
    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(FreeConfig.SHARED_PATH, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 自定义路径的SharedPreference构造函数
     * 
     * @param context
     * @param path
     * @return
     */
    public static SharedPreferences getDefaultSharedPreferences(Context context, String path) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(path, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void putInt(Context context, String key, int value) {
        sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String key) {
        sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String key, String value) {
        sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(Context context, String key) {
        sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putLong(Context context, String key, long value) {
        sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(key, defValue);
    }

    public static void remove(Context context, String key) {
        sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }

    //**************************自定义路径的存取方法--Begin*************************************
    public static void putInt(Context context, String path, String key, int value) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String path, String key) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String path, String key, String value) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(Context context, String path, String key) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        return sharedPreferences.getString(key, null);
    }

    public static void putBoolean(Context context, String path, String key, boolean value) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(Context context, String path, String key, boolean defValue) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putLong(Context context, String path, String key, long value) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static long getLong(Context context, String path, String key, long defValue) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        return sharedPreferences.getLong(key, defValue);
    }

    public static void remove(Context context, String path, String key) {
        sharedPreferences = getDefaultSharedPreferences(context, path);
        Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }

    //**************************自定义路径的存取方法--End*************************************
}
