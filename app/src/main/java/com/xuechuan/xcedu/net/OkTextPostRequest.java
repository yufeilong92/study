package com.xuechuan.xcedu.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.lzy.okgo.model.HttpParams;
import com.umeng.debug.log.D;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringSort;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.HttpInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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
public class OkTextPostRequest extends BaseHttpServcie {
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

    /**
     * 请求测试
     * @param context
     * @param param
     * @param callBackView
     */
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

    /***
     * 测试
     * @param context
     * @param param
     * @param callBackView
     */
    private void requestPost(Context context, String param, final StringCallBackView callBackView) {
        context.unregisterReceiver(receiver);
        String url = context.getResources().getString(R.string.app_content_post);

        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        JSONObject object = new JSONObject();
        try {
            object.put("Id", "10");
            object.put("Name", "安慕希");
            object.put("Count", "10");
            object.put("Price", "58.8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(parse, object.toString());
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(object);
        L.e(signature);
        sendRequestPost(url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, requestBody, callBackView);
    }

    protected void requestPost(Context context, String url, JSONObject param, final StringCallBackView callBackView) {
        context.unregisterReceiver(receiver);
        addParams(context, param);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        RequestBody requestBody = RequestBody.create(parse, param.toString());
        StringSort sort = new StringSort();
        String signature = sort.getOrderMd5Data(param);
        L.e(signature);

        sendRequestPost(url, infomVo.getStaffid(), infomVo.getTimeStamp(), infomVo.getNonce(), signature, requestBody, callBackView);
    }

    /***
     * 登陆请求
     * @param context
     * @param url
     * @param param
     * @param callBackView
     */
    protected void requestPostWithOutToken(Context context, String url, JSONObject param, final StringCallBackView callBackView) {
        String time = String.valueOf(new Date().getTime());
        String random8 = String.valueOf(Utils.getRandom8());
        addParams(context, param);
        MediaType parse = MediaType.parse(DataMessageVo.HTTPAPPLICAITON);
        RequestBody requestBody = RequestBody.create(parse, param.toString());
        sendRequestPost(url, null,time,random8,null,requestBody, callBackView);
    }

    /**
     * 添加参数
     * @param context
     * @param param
     */
    private void addParams(Context context, JSONObject param) {
        try {
            String newType = Utils.getNewType(context);
            param.put(DataMessageVo.HTTP_AC, newType);
            String versionName = Utils.getVersionName(context);
            param.put(DataMessageVo.HTTP_VERSION_NAME, versionName);
            int code = Utils.getVersionCode(context);
            param.put(DataMessageVo.HTTP_VERSION_CODE, String.valueOf(code));
            param.put(DataMessageVo.HTTP_DEVICE_PLATFORM, "android");
            String model = Utils.getSystemModel();
            param.put(DataMessageVo.HTTP_DEVICE_TYPE, model);
            String brand = Utils.getDeviceBrand();
            param.put(DataMessageVo.HTTP_DEVICE_BRAND, brand);
            String systemVersion = Utils.getSystemVersion();
            param.put(DataMessageVo.HTTP_OS_VERSION, systemVersion);
            String dp = Utils.getdp(context);
            param.put(DataMessageVo.HTTP_RESOLUTION, dp);
            String dpi = Utils.getdpi(context);
            param.put(DataMessageVo.HTTP_DPI, dpi);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
