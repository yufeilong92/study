package com.xuechuan.xcedu.net;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 12:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class RegisterService extends OkTextPostRequest {
    public static RegisterService getInstance() {
        return new RegisterService();
    }

    public void getRequestCode(Context context, String telphone, StringCallBackView backView) {

    }


}
