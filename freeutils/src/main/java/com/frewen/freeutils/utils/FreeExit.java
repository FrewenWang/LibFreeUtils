package com.frewen.freeutils.utils;

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class FreeExit {
    private static final String TAG = FreeExit.class.getSimpleName();
    // 定义一个activity列表
    private List<FragmentActivity> mList = new ArrayList<FragmentActivity>();
    // 顶一一个类的实例
    private static FreeExit instance;
    
    // 私有构造方法 不允许创建类的实例
    private FreeExit() {
        
    }
    
    /** 
     * 单例模式 
     * @return 
     */
    public static FreeExit getInstance() {
        if (null == instance) {
            instance = new FreeExit();
        }
        return instance;
    }
    
    /** 
     * 如果activity已经 destory了 就移除 
     * @param activity 
     */
    public void removeActivity(FragmentActivity activity) {
        mList.remove(activity);
    }
    
    /** 
     * 添加ativity 
     * @param activity 
     */
    public void addActivity(FragmentActivity activity) {
        mList.add(activity);
    }
    
    /** 
     * 遍历  结束activity  并且退出 
     */
    public void exit() {
        try {
            for (FragmentActivity activity : mList) {
                if (activity != null)
                    FreeLog.d(TAG, activity.getPackageName() + activity.getLocalClassName() + "is exited");
                activity.finish();
            }
            
        } catch (Exception e) {
            FreeLog.e(TAG, "activity exit error===" + e.getMessage());
            e.printStackTrace();
        } finally {
            FreeLog.i(TAG, "finally called");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
    
}
