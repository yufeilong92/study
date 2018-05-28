package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/28 9:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface ChangerPawContract {
    interface Model {
        public void submitChangePaw(Context context, String old, String newpaw, RequestResulteView view);
    }

    interface View {
        public void ChangerPawSuccess(String com);

        public void ChangerPawError(String com);
    }

    interface Presenter {
        public void submitChangePaw(Context context, String old, String newpaw);
    }
}
