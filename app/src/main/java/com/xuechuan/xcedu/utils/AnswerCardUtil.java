package com.xuechuan.xcedu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/26 16:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerCardUtil {
    /***
     *
     * @param context
     * @param iv  view
     * @param select  是否选中
     * @param status  那个被选中
     */
    @SuppressLint("NewApi")
    public static void setImgABCEDEBg(Context context, ImageView iv, boolean select, Status status) {
        switch (status) {
            case A:
                if (select) {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_a_s));
                } else {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_a_n));
                }
                break;
            case B:
                if (select) {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_b_s));
                } else {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_b_n));
                }
                break;
            case C:
                if (select) {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_c_s));
                } else {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_c_n));
                }
                break;
            case D:
                if (select) {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_d_s));
                } else {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_d_n));
                }
                break;
            case E:
                if (select) {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_e_s));
                } else {
                    iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_e_n));
                }
                break;
            default:
        }
    }

    /***
     *
     * @param context
     * @param iv  展示view
     * @param status  状态码
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRightOrErrorBg(Context context, ImageView iv, Status status) {
        switch (status) {
            case RIGHT:
                iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_right));
                break;
            case ERROR:
                iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_erro));
                break;
            case WARN:
                iv.setImageDrawable(context.getDrawable(R.drawable.ic_b_miss));
                break;
        }
    }

    /**
     * 获取题类型
     *
     * @param id
     * @return
     */
    public static String getTextType(int id) {
        switch (id) {
            case 2:
                return "单选";
            case 3:
                return "多选";
            case 4:
                return "问答题";
            default:
        }
        return "";
    }
}

