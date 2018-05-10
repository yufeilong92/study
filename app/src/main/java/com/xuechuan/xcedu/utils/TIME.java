package com.xuechuan.xcedu.utils;

import com.xuechuan.xcedu.mvp.view.TimeShowView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyCountUtil.java
 * @Package com.xuechuan.xcedu.utils
 * @Description: 倒计时工具
 * @author: YFL
 * @date: 2018/5/9 23:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/9 星期三
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TIME extends AdvancedCountdownTimer {
    private TimeShowView showView;
    private static TIME TIME;

    public void setTimwShowView(TimeShowView showView) {
        this.showView = showView;
    }

    public static TIME getInstancess(String hourtiem, String minTime, String secondTime) {
        long hour = Long.parseLong(hourtiem);
        long minute = Long.parseLong(minTime);
        long second = Long.parseLong(secondTime);
        long time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
        if (TIME == null)
            TIME = new TIME(time, 1000);
        return TIME;
    }

    public TIME(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    //更新剩余时间
    String a = null;
    private String contentime;

    @Override
    public void onTick(long millisUntilFinished, int percent) {
        long myhour = (millisUntilFinished / 1000) / 3600;
        long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
        long mysecond = millisUntilFinished / 1000 - myhour * 3600
                - myminute * 60;
        if (mysecond < 10) {
            a = "0" + mysecond;
            if (myminute < 10) {
                contentime = "0" + myhour + ":" + "0" + myminute + ":" + a;
            } else
                contentime = "0" + myhour + ":" + myminute + ":" + a;
        } else {
            if (myminute < 10) {
                contentime = "0" + myhour + ":" + "0" + myminute + ":" + mysecond;
            } else {
                contentime = "0" + myhour + ":" + myminute + ":" + mysecond;
            }
        }
        showView.CurrentTime(contentime);
    }

    @Override
    public void onFinish() {
        showView.TimeFinish();
    }
}
