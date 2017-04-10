package com.frewen.freeutils.utils;

import android.app.Dialog;

import java.util.ArrayList;
import java.util.List;

public class FreeDialogManager {

    private static final String TAG = FreeDialogManager.class.getSimpleName();
    // 定义一个activity列表
    private List<Dialog> mDialogs = new ArrayList<Dialog>();

    /** 私有构造函数 */
    private FreeDialogManager() {

    }

    private static class FreeDialogManagerHoler {
        private static FreeDialogManager mIntance = new FreeDialogManager();
    }

    public static FreeDialogManager getInstance() {
        return FreeDialogManagerHoler.mIntance;
    }

    /** 队列加入Dialog
     * 
     * @param dialog */
    public void addDialog(Dialog dialog) {
        mDialogs.add(dialog);
    }

    public void removeDialog(Dialog dialog) {
        mDialogs.remove(dialog);
    }

    public void clearDialogs() {
        //增强for循环遍历Dialogs 然后执行dismiss方法
        for (Dialog dialog : mDialogs) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }
}
