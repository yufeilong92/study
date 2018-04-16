package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 17:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class WeiXinLoginSercvice extends OkTextPostRequest {

    private static WeiXinLoginSercvice sercvice;

    public static WeiXinLoginSercvice getInstance() {
        if (sercvice == null)
            sercvice = new WeiXinLoginSercvice();
        return sercvice;
    }

    public void requestWeiCode(Context context, String code, StringCallBackView backView) {
        String hear = context.getResources().getString(R.string.app_content_heat);
        String weixin = context.getResources().getString(R.string.http_url_weixin);
        String url = hear + weixin;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestPostWithOutToken(context, url, jsonObject, backView);

    }
}
