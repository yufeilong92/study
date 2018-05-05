package com.xuechuan.xcedu.utils;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 倒计时
 * @author: L-BackPacker
 * @date: 2018/5/5 15:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyCount extends AdvancedCountdownTimer {
    String timeContent;

    public MyCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    //更新剩余时间
    String a = null;

    @Override
    public void onTick(long millisUntilFinished, int percent) {
        long myhour = (millisUntilFinished / 1000) / 3600;
        long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
        long mysecond = millisUntilFinished / 1000 - myhour * 3600
                - myminute * 60;
        if (mysecond < 10) {
            a = "0" + mysecond;
            if (myminute < 10) {
                timeContent = "0" + myhour + ":" + "0" + myminute + ":" + a;
            } else
                timeContent = "0" + myhour + ":" + myminute + ":" + a;
        } else {
            if (myminute < 10) {
                timeContent = "0" + myhour + ":" + "0" + myminute + ":" + mysecond;
            } else {
                timeContent = "0" + myhour + ":" + myminute + ":" + mysecond;
            }

        }
    }

    public String setStartTime(String hour1,String minute1,String second1,int percent) {
        long hour = Long.parseLong(hour1);
        long minute = Long.parseLong(minute1);
        long second = Long.parseLong(second1);
        long time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.

        onTick(time, percent);
        return timeContent;
    }

    @Override
    public void onFinish() {

    }
}
