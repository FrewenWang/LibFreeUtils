/*
 * 文件名称：FreeKeyBoard.java
 * 版    权：Frewen.Wong Copyright 2015,  All rights reserved
 * 描    述：<概要描述>
 * 创 建 人：Frewen.W  QQ:61511225
 * 创建时间：2016-2-15
 * 
 * 修改记录：1.<修改时间>  <修改人>  <修改描述>
 *           2.<修改时间>  <修改人>  <修改描述>
 */
package com.frewen.freeutils.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 
 * @author Frewen.W QQ:61511225
 * @version [版本号, 2016-2-15]
 * @since [产品/模块版本]
 */
public class FreeKeyBoard {

    /**
     * 打开软键盘
     * 
     * @param mContext
     * @param editText
     */
    public static void openKeybord(Context mContext, EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * 
     * @param mContext
     * @param editText
     */
    public static void closeKeybord(Context mContext, EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
