package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 我的个人
 * @author: L-BackPacker
 * @date: 2018/5/22 10:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MeService extends BaseHttpServcie {
    private Context mContext;
    private static MeService service;

    public MeService(Context mContext) {
        this.mContext = mContext;
    }

    public static MeService getInstance(Context context) {
        if (service == null)
            service = new MeService(context);
        return service;
    }

    /**
     * 兑换码兑换
     *
     * @param code
     */
    public void requestExchange( String code, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_exchangepost);
        requestHttpServciePost(mContext, url, object,
                true, view);

    }

    /**
     * sn码正版授权验证
     *
     * @param code
     */
    public void requestSncode( String code, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();

        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);

        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("code");
        paramVo1.setValue(code);
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_get_sncode);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);

    }

    /**
     * 用户建议提交接口
     *
     * @param content
     */
    public void submitAdvice( String content, String link, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject object = new JSONObject();
        try {
            object.put("staffid", user.getId());
            object.put("content", content);
            object.put("link", link);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_post_advice);
        requestHttpServciePost(mContext, url, object, true, view);

    }

    /**
     * 获取订单信息
     *
     * @param orderstate
     */
    public void requestOrder( String orderstate, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();

        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo);

        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("orderstate");
        paramVo1.setValue(orderstate);
        listParamVo.add(paramVo1);

        String url = getUrl(mContext, R.string.http_get_order);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);

    }

}
