package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 提交用户信息
 * @author: L-BackPacker
 * @date: 2018/5/25 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PersionModel implements PersionContract.Model {


    @Override
    public void submitPersionInfom(Context context, String nickname, int gender, String birthday, String province, String city, final RequestResulteView view) {
        MeService service = new MeService(context);
        service.submitChangememberinfo(nickname, gender, birthday, province, city, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }
            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });
    }

    @Override
    public void submitPersionHear(Context context, String path, RequestResulteView view) {

    }
}
