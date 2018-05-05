package com.xuechuan.xcedu.vo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 答题卡eventbus
 * @author: L-BackPacker
 * @date: 2018/5/5 10:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerResultEvent {

    private List<TitleNumberVo.DatasBean> mTextDetial;

    public AnswerResultEvent(List<TitleNumberVo.DatasBean> mTextDetial) {
        this.mTextDetial = mTextDetial;
    }

    public List<TitleNumberVo.DatasBean> getmTextDetial() {
        return mTextDetial;
    }
}
