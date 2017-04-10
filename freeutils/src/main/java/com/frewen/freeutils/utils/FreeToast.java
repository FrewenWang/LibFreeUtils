package com.frewen.freeutils.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast信息打印控制类
 * 兼容可连续弹出Toast或者非连续弹出Toast的问题。
 */
public class FreeToast {

    public static Toast mToast;

    /**
     * 打印自定义时长Toast调试信息
     *
     * @param context 上下文
     * @param msg     打印的消息
     * @param time    显示时长
     */
    public static void showToast(Context context, CharSequence msg, int time) {
        showSingleMsg(context, msg, time);

    }

    public static void showToast(Context context, int resId, int time) {
        showSingleMsg(context, resId, time);
    }

    /**
     * 打印Toast调试信息
     *
     * @param context 上下文对象
     * @param msg     调试信息
     */
    public static void showShort(Context context, String msg) {
        showSingleMsg(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, int Resid) {
        showSingleMsg(context, Resid, Toast.LENGTH_SHORT);
    }

    /**
     * 打印Toast调试信息
     *
     * @param context 上下文对象
     * @param msg     调试信息
     */
    public static void showLong(Context context, String msg) {
        showSingleMsg(context, msg, Toast.LENGTH_LONG);
    }

    public static void Long(Context context, int Rid) {
        showSingleMsg(context, Rid, Toast.LENGTH_LONG);
    }

    /**
     * 显示自定义位置的Toast
     *
     * @param context
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @param msg
     */
    public static void showCustomPositionToast(Context context, int gravity, int xOffset, int yOffset, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        // 第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        // 第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        // 第三个参数：同的第二个参数道理一样
        // 如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        toast.setGravity(gravity, xOffset, yOffset);
        // 屏幕居中显示，X轴和Y轴偏移量都是0
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示带图片的自定义位置的Toast
     *
     * @param context
     * @param msg
     * @param resId
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showImgToast(Context context, String msg, int resId, int gravity, int xOffset, int yOffset) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(gravity, xOffset, yOffset);
        // 创建图片视图对象
        ImageView imageView = new ImageView(context);
        // 设置图片
        imageView.setImageResource(resId);
        // 获得toast的布局
        LinearLayout toastView = (LinearLayout) toast.getView();
        // 设置此布局为横向的
        toastView.setOrientation(LinearLayout.VERTICAL);
        // 将ImageView在加入到此布局中的第一个位置
        toastView.addView(imageView, 0);
        toast.show();
    }

    /**
     * 多次调用Toast，并不弹出Toast，而只是更改内容显示
     * @param context
     * @param msg
     * @param time
     */
    private static void showSingleMsg(Context context, CharSequence msg, int time) {
        if (null == mToast) {
            mToast = Toast.makeText(context, msg, time);
        } else {
            mToast.setText(msg);
            mToast.setDuration(time);
        }
        mToast.show();
    }
    /**
     * 多次调用Toast，并不弹出Toast，而只是更改内容显示
     * @param context
     * @param resId
     * @param time
     */
    private static void showSingleMsg(Context context, int resId, int time) {
        showSingleMsg(context, context.getResources().getText(resId).toString(), time);
    }

    /**
     * 调用可连续打印Toast的方法。连续调用会连续弹出Toast通知
     * @param context
     * @param resId
     * @param duration
     * @return
     */
    public static Toast showRepeateToast(Context context,int resId, int duration) {
        return showRepeateToast(context,context.getResources().getText(resId).toString(), duration);
    }

    public static Toast showRepeateToast(Context context, String text, int duration) {
        return Toast.makeText(context, text, duration);
    }

}
