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
 * @Description: 登录接口
 * @author: L-BackPacker
 * @date: 2018/4/18 9:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class LoginService extends BaseHttpServcie {
    private Context mContext;
    private static LoginService service;
    public LoginService(Context mContext) {
        this.mContext = mContext;
    }
    public static LoginService getInstance(Context context) {
        if (service == null)
            service = new LoginService(context);
        return service;
    }
    public void requestlogin(String username,String password,StringCallBackView callBackView){
        String url = mContext.getResources().getString(R.string.http_login);
        JSONObject object = new JSONObject();
        try {
            object.put("username",username);
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestHttpServciePost(mContext,url,object,false,callBackView);
    }

    public void requestRefeshToken(String saffid,String token ,StringCallBackView callBackView){
        String url = mContext.getResources().getString(R.string.http_refresh_token);
        JSONObject object = new JSONObject();
        try {
            object.put("staffid",saffid);
            object.put("oldtoken",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestHttpServciePost(mContext,url,object,true,callBackView);
    }


    @Override
    public void setIsShowDialog(boolean show) {
        super.setIsShowDialog(show);
    }
    public void setDialogContext(String title, String cont) {
        super.setDialogContext(mContext, title, cont);
    }
}
