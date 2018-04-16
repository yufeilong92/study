package com.xuechuan.xcedu.utils;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.xuechuan.xcedu.ui.RegisterActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 倒计时
 * @author: L-BackPacker
 * @date: 2018/4/16 10:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CountdownUtil {
    int recLen = 0;
    Handler handler = new Handler();
    private static CountdownUtil util;
    private Button button;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            if (recLen > 59) {
                button.setText("重新发送");
                button.setEnabled(true);
                recLen = 0;
                return;
            }
            button.setText("" + recLen);
            handler.postDelayed(this, 1000);
        }
    };

    public void startTime(final Button button) {
       this.button=button;
       handler.postDelayed(runnable,1000);
    }
    public static CountdownUtil getInstance() {
        if (util == null)
            util = new CountdownUtil();
        return util;
    }

    public void stop() {
        handler.postDelayed(null,1000);
    }
}
