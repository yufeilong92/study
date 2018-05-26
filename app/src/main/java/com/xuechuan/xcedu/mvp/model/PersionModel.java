package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/25 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PersionModel implements PersionContract.Model {


    @Override
    public void submitPersionInfom(Context context, String nickname, int gender, String birthday, String province, String city, RequestResulteView view) {


    }

    @Override
    public void submitPersionHear(Context context, String path, RequestResulteView view) {

    }
}
