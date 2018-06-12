package com.xuechuan.xcedu.Event;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.Event
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/12 8:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RefreshPlayIconEvent {
    public String vid;
    public int index;

    public RefreshPlayIconEvent(String vid, int index) {
        this.vid = vid;
        this.index = index;
    }

    public String getVid() {
        return vid;
    }

    public int getIndex() {
        return index;
    }
}
