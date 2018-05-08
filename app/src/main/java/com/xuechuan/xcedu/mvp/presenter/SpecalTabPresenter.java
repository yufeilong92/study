package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.SpecalTabModel;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.mvp.view.SpecalTabView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 资讯详情页
 * @author: L-BackPacker
 * @date: 2018/5/8 15:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecalTabPresenter {
    private SpecalTabView view;
    private SpecalTabModel model;

    public SpecalTabPresenter(SpecalTabView view, SpecalTabModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * 请求tag 集合
     *
     * @param context
     */
    public void reqeustTagList(Context context) {
        model.requestTagList(context, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SpecalTagSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SpecalTagError(result);
            }
        });
    }

    public void requestTagCont(Context context, String tagid) {
        if (StringUtil.isEmpty(tagid))
            return;
        model.requestTagConte(context, tagid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SpecalTagConSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SpecalTagConError(result);
            }
        });
    }
}
