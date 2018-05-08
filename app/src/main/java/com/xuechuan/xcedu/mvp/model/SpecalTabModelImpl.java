package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.google.android.exoplayer.C;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 资讯详情页
 * @author: L-BackPacker
 * @date: 2018/5/8 15:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecalTabModelImpl implements SpecalTabModel {

    @Override
    public void requestTagList(Context context, final RequestResulteView view) {
        HomeService service = new HomeService(context);
        service.requestArticletagList(new StringCallBackView() {
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
    public void requestTagConte(Context context, String tagid, final RequestResulteView view) {
        HomeService service = new HomeService(context);
        service.requestArticleWithTagaList(tagid, new StringCallBackView() {
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
}
