package com.xuechuan.xcedu.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttp;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.HttpInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/12 13:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OkTextGetRequest extends BaseHttp {
    public String ACITON = "com.xuechaun.action";

    private final HttpInfomVo infomVo;
    private static OkTextGetRequest okTextGetRequest;
    private BroadcastReceiver receiver;

    public OkTextGetRequest() {
        infomVo = MyAppliction.getHttpInfomInstance();

    }

    public static OkTextGetRequest getInstance() {
        if (okTextGetRequest == null)
            okTextGetRequest = new OkTextGetRequest();
        return okTextGetRequest;
    }

    public void sendRequestGet(Context context, final String param, final StringCallBackView callBackView) {
        if (infomVo.getToken() == null) {
            RequestToken.getInstance().requestToken(context, ACITON, null);
        }
        //解决延迟问题
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACITON);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                requestGet(context, param, callBackView);
            }
        };
        context.registerReceiver(receiver, intentFilter);
    }
    private void requestGet(Context context, String param, final StringCallBackView callBackView) {
        context.unregisterReceiver(receiver);
        String url = context.getResources().getString(R.string.app_content_get);
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(param);
        L.e(signature);
        sendRequestGet(url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, callBackView);
    }


}
