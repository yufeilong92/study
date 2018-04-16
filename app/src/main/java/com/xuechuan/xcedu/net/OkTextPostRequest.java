package com.xuechuan.xcedu.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.lzy.okgo.model.HttpParams;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.vo.HttpInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/12 15:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OkTextPostRequest  extends BaseHttpServcie {
    public String ACITON = "com.xuechaun.OkTextPostRequest";

    private final HttpInfomVo infomVo;
    private static OkTextPostRequest okTextPostRequest;
    private BroadcastReceiver receiver;

    public OkTextPostRequest() {
        infomVo = MyAppliction.getHttpInfomInstance();

    }

    public static OkTextPostRequest getInstance() {
        if (okTextPostRequest == null)
            okTextPostRequest = new OkTextPostRequest();
        return okTextPostRequest;
    }

    public void sendRequestPost(Context context, final String param, final StringCallBackView callBackView) {
        if (infomVo.getToken() == null) {
            RequestToken.getInstance().requestToken(context, ACITON, null);
        }
        //解决延迟问题
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACITON);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                requestPost(context, param, callBackView);
            }
        };
        context.registerReceiver(receiver, intentFilter);
    }

    private void requestPost(Context context, String param, final StringCallBackView callBackView) {
        context.unregisterReceiver(receiver);
        String url = context.getResources().getString(R.string.app_content_post);

        MediaType parse = MediaType.parse("application/json");
        JSONObject object = new JSONObject();
        try {
            object.put("Id", "10");
            object.put("Name", "安慕希");
            object.put("Count", "10");
            object.put("Price", "58.8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpParams httpParams = new HttpParams();
        httpParams.put("Id", "10");
        httpParams.put("Name", "安慕希");
        httpParams.put("Count", "10");
        httpParams.put("Price", "58.8");
        RequestBody requestBody = RequestBody.create(parse, object.toString());
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(object);
        L.e(signature);
        sendRequestPost(url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, requestBody , callBackView);
    }

}
