package com.xuechuan.xcedu.base;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.ShowDialog;
import com.xuechuan.xcedu.utils.StringUtil;

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
public class BaseHttpServcie {
    private Context context;
    private String title;
    private String cont;
    private ShowDialog instance;
    private boolean isShow = false;
    private AlertDialog dialog;

    public  void setIsShowDialog(boolean show) {
        this.isShow = show;
    }
    public void setDialogContext(Context context, String title, String cont) {
        this.context = context;
        this.title = title;
        this.cont = cont;
        instance = ShowDialog.getInstance(context);
    }

    protected void sendOkGoGetTokenHttp(Context context, String url, String saffid, String time, String nonce, String signature, final StringCallBackView callBackView) {
        if (StringUtil.isEmpty(saffid)) {
            saffid = "0";
        }
        if (isShow) {
            dialog = instance.showDialog(title, cont);
        }
        String hear = context.getResources().getString(R.string.app_content_heat);
        url=hear.concat(url);
        OkGo.<String>get(url)
                .headers(DataMessageVo.STAFFID, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(DataMessageVo.TIMESTAMP, StringUtil.isEmpty(time) ? null : time)
                .headers(DataMessageVo.NONCE, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(DataMessageVo.SIGNATURE, StringUtil.isEmpty(signature) ? null : signature)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onError(response);
                    }
                });
    }

    protected void sendRequestPostHttp(Context context, String url, String saffid, String time, String nonce, String signature, RequestBody requestBody, final StringCallBackView callBackView) {
        if (StringUtil.isEmpty(saffid)) {
            saffid = "0";
        }
        if (isShow) {
            dialog = instance.showDialog(title, cont);
        }
        String hear = context.getResources().getString(R.string.app_content_heat);
        url=hear.concat(url);
        OkGo.<String>post(url)
                .headers(DataMessageVo.STAFFID, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(DataMessageVo.TIMESTAMP, StringUtil.isEmpty(time) ? null : time)
                .headers(DataMessageVo.NONCE, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(DataMessageVo.SIGNATURE, StringUtil.isEmpty(signature) ? null : signature)
                .upRequestBody(requestBody)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onError(response);
                    }
                });

    }

    protected void sendRequestGetHttp(Context context, String url, String saffid, String time, String nonce, String signature, final StringCallBackView callBackView) {
        if (StringUtil.isEmpty(saffid)) {
            saffid = "0";
        }
        if (isShow) {
            dialog = instance.showDialog(title, cont);
        }
        String hear = context.getResources().getString(R.string.app_content_heat);
        url=hear.concat(url);
        OkGo.<String>get(url)
                .headers(DataMessageVo.STAFFID, StringUtil.isEmpty(saffid) ? null : saffid)
                .headers(DataMessageVo.TIMESTAMP, StringUtil.isEmpty(time) ? null : time)
                .headers(DataMessageVo.NONCE, StringUtil.isEmpty(nonce) ? null : nonce)
                .headers(DataMessageVo.SIGNATURE, StringUtil.isEmpty(signature) ? null : signature)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (isShow) {
                            dialog.dismiss();
                        }
                        callBackView.onError(response);
                    }
                });
    }

}
