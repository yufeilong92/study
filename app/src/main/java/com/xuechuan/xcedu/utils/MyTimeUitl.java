package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.xcedu.mvp.view.TimeShowView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/11 14:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyTimeUitl {
    private Context context;
    private long curTime = 0;
    private boolean isPause = false;
    private TextView mTimeView;
    private long MAX_TIME = 0;
    private static MyTimeUitl timeUitl;
    private TimeShowView TimeShow;

    public MyTimeUitl(Context context) {
        this.context = context;
    }

    public static MyTimeUitl getInstance(Context context) {
        if (timeUitl == null)
            timeUitl = new MyTimeUitl(context);
        return timeUitl;
    }

    public void start(TextView tv, long hour, long min, long ss, TimeShowView view) {
        this.mTimeView = tv;
        this.TimeShow = view;
        MAX_TIME = getTime(hour, min, ss);
        restart();
    }

    public void restart() {
        curTime = MAX_TIME;
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 1000);
    }

    private long getTime(long hour, long min, long ss) {
        if (hour == 0 && min == 0 && ss == 0)
            return 0;
        return hour * 60 * 60 * 1000 + min * 60 * 1000 + ss * 1000;
    }

    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            curTime -= 1000;
            mTimeView.setText(TimeTools.getCountTimeByLong(curTime));
            if (curTime > 0) {
                mHandler.postDelayed(this, 1000);
            } else {
                TimeShow.TimeFinish();
//                Toast.makeText(context, "运行结束", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void cancel() {
        mTimeView.setText("00:00:00");
        mHandler.removeCallbacks(runnable);
    }

    public void pause() {
        if (!isPause) {
            mHandler.removeCallbacks(runnable);
        }
    }

    public void resume() {
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 1000);
    }
}
