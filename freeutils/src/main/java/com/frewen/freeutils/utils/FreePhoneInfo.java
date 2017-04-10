package com.frewen.freeutils.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 获取终端相关信息
 *
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2015-7-6上午10:31:34]
 * @since [产品/模块版本]
 */
public class FreePhoneInfo {

    private TelephonyManager telephonyManager;

    static final Object sInstanceSync = new Object();
    private static FreePhoneInfo sInstance;

    private FreePhoneInfo(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static FreePhoneInfo getInstance(Context context) {
        // 保持线程同步
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                sInstance = new FreePhoneInfo(context);
            }
        }
        return sInstance;
    }

    /**
     * 返回设备的唯一标识符IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();// 返回当前移动终端的唯一标识,如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
        if (deviceId == null) {
            // 另一种方法获取设备的唯一标识，如果deviceId为空。
            deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
        return deviceId != null ? deviceId : UUID.randomUUID().toString();
    }

    /**
     * 获取当前的手机号码
     *
     * @param context
     * @return
     */
    public String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * 获取终端型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取终端的android版本
     *
     * @return
     */
    public static String getAndroidVersion() {
        return "Android version:" + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 终端分辨率信息
     *
     * @param context
     * @return
     */
    public static String getPhoneScreen(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        stringBuilder.append("screenInfo：");
        stringBuilder.append(screenWidth);
        stringBuilder.append("*");
        stringBuilder.append(screenHeight);
        return stringBuilder.toString();
    }

    /**
     * 获取本机的IP地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        String ipaddress = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //ipaddress = ipaddress + ";" + inetAddress.getHostAddress().toString();
                        ipaddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            FreeLog.e("WifiPreference IpAddress", ex.toString());
        }
        return ipaddress;
    }

    /**
     * 获取本机的Mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        String macAddress = "";
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();

        macAddress = wifiInfor.getMacAddress();

        return macAddress;
    }
}
