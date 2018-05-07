package com.xuechuan.xcedu.mvp.view;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.view
 * @Description:  题干
 * @author: L-BackPacker
 * @date: 2018/5/2 11:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface AnswerView {
    public void TextSuccess(String con);

    public void TextError(String con);

    public void TextDetailSuccess(String con);

    public void TextDetailError(String con);

    public void SumbitCollectSuccess(String con);

    public void SumbitCollectError(String con);

    public void DoResultSuccess(String con);
    public void DoResultError(String con);



}
