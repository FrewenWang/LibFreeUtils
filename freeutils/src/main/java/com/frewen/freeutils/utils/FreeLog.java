package com.frewen.freeutils.utils;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * 日志打印工具类
 *
 * @author Frewen.W
 * @version [版本号, 2015-3-20]
 * @since [产品/模块版本]
 */
public class FreeLog {
    /**
     * verbose开关.
     */
    public static boolean V_Debug = true;
    /**
     * debug开关.
     */
    public static boolean D_Debug = true;
    /**
     * info开关.
     */
    public static boolean I_Debug = true;
    /**
     * warn开关.
     */
    public static boolean W_Debug = true;
    /**
     * error开关.
     */
    public static boolean E_Debug = true;

    /**
     * 起始执行时间.
     */
    public static long startLogTimeInMillis = 0;

    //*********************Verbose的Log打印--Begin*****************************
    public static void v(String TAG, String msg) {
        if (V_Debug)
            Log.v(TAG, msg);
    }

    public static void v(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        v(tag, message);
    }

    public static void v(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        v(tag, message);
    }

    public static void v(String TAG, String title, String msg) {

        v(TAG, title + ":" + msg);
    }

    //*********************Debug的Log打印--Begin*****************************
    public static void d(String TAG, String msg) {
        d(true, TAG, msg);
    }

    public static void d(boolean debugFlag, String TAG, String msg) {
        if (D_Debug && debugFlag)
            Log.d(TAG, msg);
    }

    public static void d(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        d(tag, message);
    }

    public static void d(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        d(tag, message);
    }

    public static void d(String TAG, String title, String msg) {
        d(TAG, title + ":" + msg);
    }

    public static void d(String TAG, String format, Object... args) {
        d(TAG, buildMessage(format, args));
    }

    //*********************Info的Log打印--Begin*****************************
    public static void i(String TAG, String msg) {
        i(true, TAG, msg);
    }

    public static void i(boolean debugFlag, String TAG, String msg) {
        if (I_Debug && debugFlag)
            Log.i(TAG, msg);
    }

    public static void i(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        i(tag, message);
    }

    public static void i(boolean debugFlag, Context context, String message) {
        String tag = context.getClass().getSimpleName();
        i(debugFlag, tag, message);
    }

    public static void i(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        i(tag, message);
    }

    public static void i(boolean debugFlag, Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        i(debugFlag, tag, message);
    }

    public static void i(String TAG, String title, String msg) {
        i(TAG, title + ":" + msg);
    }

    public static void i(String TAG, String format, Object... args) {
        i(TAG, buildMessage(format, args));
    }

    //*********************Warn的Log打印--Begin*****************************
    public static void w(String TAG, String msg) {
        w(true, TAG, msg);
    }

    public static void w(boolean debugFlag, String TAG, String msg) {
        if (W_Debug && debugFlag)
            Log.i(TAG, msg);
    }

    public static void w(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        w(tag, message);
    }

    public static void w(boolean debugFlag, Context context, String message) {
        String tag = context.getClass().getSimpleName();
        w(debugFlag, tag, message);
    }

    public static void w(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        w(tag, message);
    }

    public static void w(String TAG, String title, String msg) {
        w(TAG, title + ":" + msg);
    }

    public static void w(String TAG, String format, Object... args) {
        w(TAG, buildMessage(format, args));
    }

    //*********************Error的Log打印--Begin*****************************
    public static void e(String TAG, String msg) {
        e(true, TAG, msg);
    }

    public static void e(boolean debugFlag, String TAG, String msg) {
        if (E_Debug && debugFlag)
            Log.i(TAG, msg);
    }

    public static void e(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        e(tag, message);
    }

    public static void e(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        e(tag, message);
    }

    public static void e(String TAG, String title, String msg) {
        e(TAG, title + ":" + msg);
    }

    public static void e(String TAG, String format, Object... args) {
        e(TAG, buildMessage(format, args));
    }

    /**
     * 描述：记录当前时间毫秒.
     */
    public static void prepareLog(String tag) {
        Calendar current = Calendar.getInstance();
        startLogTimeInMillis = current.getTimeInMillis();
        Log.d(tag, "日志计时开始：" + startLogTimeInMillis);
    }

    /**
     * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
     *
     * @param tag       标记
     * @param message   描述
     * @param printTime 是否打印时间
     */
    public static void d(String tag, String message, boolean printTime) {
        Calendar current = Calendar.getInstance();
        long endLogTimeInMillis = current.getTimeInMillis();
        Log.d(tag, message + ":" + (endLogTimeInMillis - startLogTimeInMillis) + "ms");
    }

    /**
     * format日志
     *
     * @param format
     * @param args
     * @return
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(FreeLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }

    /**
     * 获取异常所在行，当我们出现异常的时候，可以将异常所在行数打印出来。
     *
     * @param exception
     * @return
     */
    public static int getExceptionLine(final Exception exception) {
        int lineNumber = 0;
        final StackTraceElement[] stackTraceElement = exception.getStackTrace();
        if (stackTraceElement != null && stackTraceElement.length > 0) {
            lineNumber = stackTraceElement[0].getLineNumber();
        }
        return lineNumber;
    }

    /**
     * 设置日志的开关
     *
     * @param e
     */
    public static void setVerbose(boolean d, boolean i, boolean e) {
        D_Debug = d;
        I_Debug = i;
        E_Debug = e;
    }

    /**
     * 打开所有日志，默认全打开
     */
    public static void openAll() {
        D_Debug = true;
        I_Debug = true;
        E_Debug = true;
    }

    /**
     * 关闭所有日志
     */
    public static void closeAll() {
        D_Debug = false;
        I_Debug = false;
        E_Debug = false;
    }
}
