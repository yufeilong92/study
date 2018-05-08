package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description:  资讯详情页
 * @author: L-BackPacker
 * @date: 2018/5/8 15:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface SpecalTabModel {

   public void requestTagList(Context context, RequestResulteView view);
   public void requestTagConte(Context context,String tagid,RequestResulteView view);

}
