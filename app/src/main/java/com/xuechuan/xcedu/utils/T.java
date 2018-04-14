package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 土司优化
 * @author: L-BackPacker
 * @date: 2018/4/10 10:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class T {
    private static Toast toast;
    public static void showToast(Context context, String str){
        if (str==null){
            str="";
        }
        if (toast==null){
            toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        }else {
            toast.setText(str);
        }
        toast.show();
    }
}
