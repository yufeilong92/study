package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.xuechuan.xcedu.mvp.model.EvalueModel;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/3 15:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvaluePresenter {
    private EvalueModel model;
    private EvalueView view;

    public EvaluePresenter(EvalueModel model, EvalueView view) {
        this.model = model;
        this.view = view;
    }

    public void submitContent(Context context, String targetid, String comment, String commentid, String usetype) {
        model.SubmitContent(context, targetid, comment, commentid, usetype, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.submitEvalueSuccess(result);
            }
            @Override
            public void error(String result) {
                view.submitEvalueError(result);
            }
        });
    }
    public void requestEvalueContent(Context contex, String questionid, String commentid) {
        if (StringUtil.isEmpty(questionid)) {
            return;
        }
        model.reqeustEvalueContent(contex, questionid, commentid, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.GetEvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.GetEvalueError(result);
            }
        });
    }
}
