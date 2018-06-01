package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.EvalueInterfaceContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 20:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueInterfacePresenter implements EvalueInterfaceContract.Presenter {
    EvalueInterfaceContract.Model model;
    EvalueInterfaceContract.View view;

    @Override
    public void initModelView(EvalueInterfaceContract.Model model, EvalueInterfaceContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestEvalueTwo(Context context, int page, String commentid, String type) {
        model.requestEvalueTwo(context, page, commentid, type, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueTwoSuc(result);
            }

            @Override
            public void error(String result) {
                view.EvalueTwoErro(result);
            }
        });
    }

    @Override
    public void requestEvalueTwoMore(Context context, int page, String commentid, String type) {
        model.requestEvalueTwo(context, page, commentid, type, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueTwoSucMore(result);
            }

            @Override
            public void error(String result) {
                view.EvalueTwoSucMore(result);
            }
        });
    }
}
