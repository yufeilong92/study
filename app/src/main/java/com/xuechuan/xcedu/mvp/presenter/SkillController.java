package com.xuechuan.xcedu.mvp.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.mvp.view.SkillView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.controller
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/26 11:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SkillController implements SkillView {
    public Context context;
    private final BankService bankService;

    public SkillController(Context context) {
        this.context = context;
        bankService = new BankService(context);
    }

    @Override
    public String requestOrderData(String id) {
        bankService.setIsShowDialog(true);
        bankService.setDialogContext(context,null,"加载数据中...");
        bankService.requestChapterList(id, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                L.w(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {

            }
        });
        return null;
    }
}
