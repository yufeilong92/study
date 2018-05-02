package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.mvp.model.AnswerModel;
import com.xuechuan.xcedu.mvp.view.AnswerView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.presenter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 11:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerPresnter {
    AnswerModel model;
    AnswerView view;

    public AnswerPresnter(AnswerModel model, AnswerView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 评价
     * @param context
     * @param questionid

     */
    public void getEvaluateCotent(Context context, String questionid,int page) {
        if (StringUtil.isEmpty(questionid)) {
            return;
        }


        model.getEvalueContent(context, questionid,page, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.EvalueSuccess(result);
            }

            @Override
            public void error(String result) {
                view.EvalueError(result);
            }
        });

    }

    /**
     * 题干
     * @param context
     * @param id
     */
    public void getTextContent(Context context, String id) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        model.getTextContent(context, id, new RequestResulteView() {
            @Override
            public void success(String result) {
                L.e("success");
                view.TextSuccess(result);
            }

            @Override
            public void error(String result) {
                view.TextError(result);
            }
        });

    }

    /**
     * 题详情
     * @param content
     * @param id
     */
    public void getTextDetailContent(Context content, String id) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        model.getTextDetailContent(content, id, new RequestResulteView() {
            @Override
            public void success(String result) {
                view.TextDetailSuccess(result);
            }

            @Override
            public void error(String result) {
                view.TextDetailError(result);
            }
        });
    }
}
