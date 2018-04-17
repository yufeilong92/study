package com.xuechuan.xcedu.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.net.RequestToken;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringSort;
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
public class OkTextGetRequest extends BaseHttpServcie {

    private final HttpInfomVo infomVo;
    private static OkTextGetRequest okTextGetRequest;
    private BroadcastReceiver receiver;

    public OkTextGetRequest() {
        infomVo = MyAppliction.getInstance().getHttpInfomInstance();

    }

    public static OkTextGetRequest getInstance() {
        if (okTextGetRequest == null)
            okTextGetRequest = new OkTextGetRequest();
        return okTextGetRequest;
    }

    public void sendRequestGet(Context context, final String param, final StringCallBackView callBackView) {
        if (infomVo.getToken() == null) {
            RequestToken.getInstance().requestToken(context, DataMessageVo.ACITON, null);
        }
        //解决延迟问题
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataMessageVo.ACITON);
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
        String url = context.getResources().getString(R.string.app_content_get_text);
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(param);
        L.e(signature);
        sendRequestGetHttp(context,url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, callBackView);
    }


}
