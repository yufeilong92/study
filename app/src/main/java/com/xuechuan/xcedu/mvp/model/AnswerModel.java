package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 11:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface AnswerModel {
    public void getEvalueContent(Context context, String questionId,int page,RequestResulteView  view);
    public void getTextContent(Context context, String id, RequestResulteView  view);
    public void getTextDetailContent(Context context,String id,RequestResulteView view);
}
