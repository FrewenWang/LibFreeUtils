package com.frewen.freeutils.utils;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertyUtils {
    private static final String TAG = "PropertyUtils";
    /** 播放控制消息系统属性参数:默认值 */
    public static final String PERSIST_SYS_EASYSCREEN_DEFAULT = "";
    /** 播放控制消息系统属性参数:第一版本值 */
    public static final String PERSIST_SYS_EASYSCREEN_V1 = "http,udp,v1";

    /**
     * 根据键值获取参数值.
     * 
     * @param key
     *            键值
     * @param defaultvalue
     *            默认值，键值无法查询到时使用该值
     * @return 键值查询到参数值
     */
    public static String getProperties(String key, String defaultvalue) {
        String Value = null;
        Class<?> SystemPropertiesClass = null;
        Method method = null;

        try {

            SystemPropertiesClass = Class.forName("android.os.SystemProperties");
            //Log.i(TAG, "SystemPropertiesClass:" + SystemPropertiesClass);

            Class<?> getType[] = new Class[2];
            getType[0] = String.class;
            getType[1] = String.class;
            method = SystemPropertiesClass.getMethod("get", getType);

            Object arglist[] = new Object[2];
            arglist[0] = key;
            arglist[1] = defaultvalue;

            Object receiver = new Object();

            Object returnvalue = method.invoke(receiver, arglist);
            //Log.w(TAG, "return value:" + returnvalue);
            if (receiver != null) {
                Value = (String) returnvalue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Can not find class");
        }
        return Value;
    }

    public static Map<String, String> getEasyscreenMap() {
        Map<String, String> easyscreen = new HashMap<String, String>();
        try {
            String prop = getProperties("persist.sys.easyscreen", PERSIST_SYS_EASYSCREEN_DEFAULT);
            String[] kvArray = prop.split(",");
            for (String kvStr : kvArray) {
                easyscreen.put(kvStr, kvStr);
            }
        } catch (Exception e) {
            Log.e(TAG, "getProperties persist.sys.easyscreen error.");
        }
        return easyscreen;
    }
}
