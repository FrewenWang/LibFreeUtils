package com.frewen.freeutils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * 
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2015-6-17]
 * @since [产品/模块版本]
 */
public class FreeAppInfo {

    private static final String TAG = FreeAppInfo.class.getSimpleName();;

    private FreeAppInfo() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用的VerCode
     * 
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取应用VerName
     * 
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        //Log.d(TAG, "local version:" + verName);
        return verName;
    }

    /**
     * 获取当前应用的包名
     * 
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        String packageName = "";
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        //Log.d(TAG, "packageName===" + packageName);
        return packageName;

    }

    /**
     * 获取当前应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * 判断应用的是不是开发版 VersionCode倒数第二位如果是偶数就是正式版，否则是开发版（这个根据不同人的自己的安排）
     * 
     * @param VersionCode
     * @return
     */
    public static boolean isDevVersion(int VersionCode) {
        String versionCode = Integer.toString(VersionCode);// 把VersionCode转换成字符串
        Character flag = versionCode.charAt(versionCode.length() - 2);// 拿到倒数第二个字符
        return Integer.valueOf(flag).intValue() % 2 != 0;// 转换回来判断奇偶，奇数为true正式版，偶数为false开发板
    }

}
