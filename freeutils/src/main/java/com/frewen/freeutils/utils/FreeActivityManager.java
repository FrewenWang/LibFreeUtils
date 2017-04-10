package com.frewen.freeutils.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class FreeActivityManager {

    private static final String TAG = FreeActivityManager.class.getSimpleName();

    private List<Activity> mActivities = new ArrayList<Activity>();


    private FreeActivityManager() {

    }

    /**
     * 单例模式，私有静态类，加载类的时候实例化FreeActivityManager对象
     */
    private static class ActivityManagerHolder {
        static private FreeActivityManager instance = new FreeActivityManager();
    }


    public static FreeActivityManager getInstance() {

        return ActivityManagerHolder.instance;
    }


    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }


    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public List<Activity> getActivityList() {
        return mActivities;
    }


    public void logout() {
        for (Activity activity : mActivities) {
            if (!activity.getLocalClassName().contains("LoginActivity")) {
                activity.finish();
            }
        }
    }


    public void exitApp() {
        try {
            for (Activity activity : mActivities) {
                if (activity != null)
                    FreeLog.i(TAG, activity.getLocalClassName() + "is exited");
                activity.finish();
            }

        } catch (Exception e) {
            FreeLog.e(TAG, "Msg:activity exit error:" + e.getMessage());
            e.printStackTrace();
        } finally {
            FreeLog.i(TAG, "Msg:finally called kill the Process");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

}
