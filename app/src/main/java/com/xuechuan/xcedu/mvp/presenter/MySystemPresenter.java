package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.MySystemContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: 系统通知
 * @author: L-BackPacker
 * @date: 2018/5/30 17:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MySystemPresenter implements MySystemContract.Presenter {
    MySystemContract.Model model;
    MySystemContract.View view;

    @Override
    public void initModelView(MySystemContract.Model model, MySystemContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestSystemMsg(Context context, int page) {
        model.requestSystemMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SystemSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SystemErrorr(result);
            }
        });
    }

    @Override
    public void requestSystemMoreMsg(Context context, int page) {
        model.requestSystemMsg(context, page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.SystemMoreSuccess(result);
            }

            @Override
            public void error(String result) {
                view.SystemMoreErrorr(result);
            }
        });
    }

    @Override
    public void submitDelSystemMsg(Context context, List<Integer> ids) {
        model.submitDelSystemMsg(context, ids, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.DelSuccess(result);
            }

            @Override
            public void error(String result) {
                view.DelError(result);
            }
        });
    }
}
