package com.xuechuan.xcedu.base;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 网络请求
 * @author: L-BackPacker
 * @date: 2018/4/10 14:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BaseHttp {
    private String mStaffId = "staffid";
    private String mTimeStamp = "timeStamp";
    private String mNonce = "nonce";
    private String mSignature = "signature";

    protected void sendOkGoGetToken(String url, String saffid, String time, String nonce, String signature, final StringCallBackView callBackView) {
        OkGo.<String>get(url)
                .headers(mStaffId, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(mTimeStamp, StringUtil.isEmpty(time) ? null : time)
                .headers(mNonce, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(mSignature, StringUtil.isEmpty(signature) ? null : signature)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBackView.onError(response);
                    }
                });
    }

    protected void sendRequestPost(String url, String saffid, String time, String nonce, String signature, RequestBody requestBody, final StringCallBackView callBackView) {

        OkGo.<String>post(url)
                .headers(mStaffId, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(mTimeStamp, StringUtil.isEmpty(time) ? null : time)
                .headers(mNonce, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(mSignature, StringUtil.isEmpty(signature) ? null : signature)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBackView.onError(response);
                    }
                });

    }

    protected void sendRequestGet(String url, String saffid, String time, String nonce, String signature, final StringCallBackView callBackView) {
        OkGo.<String>get(url)
                .headers(mStaffId, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(mTimeStamp, StringUtil.isEmpty(time) ? null : time)
                .headers(mNonce, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(mSignature, StringUtil.isEmpty(signature) ? null : signature)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        L.e(response.body().toString());
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBackView.onError(response);
                    }
                });
    }
}
