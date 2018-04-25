package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 评价/点赞
 * @author: L-BackPacker
 * @date: 2018/4/20 15:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class CurrencyService extends BaseHttpServcie {

    private Context mContext;
    private static CurrencyService service;

    public CurrencyService(Context mContext) {
        this.mContext = mContext;
    }

    public static CurrencyService getInstance(Context context) {
        if (service == null) {
            service = new CurrencyService(context);
        }
        return service;
    }

    /**
     * @param targentid    评论对象主题编号（文章，题库，视频）
     * @param comment      内容
     * @param commentid    对于二级评论时，评论对象编号
     * @param usetype      类型 article（文章） question video（视频）
     * @param callBackView
     */
    public void submitConmment(String targentid,
                               String comment, String commentid,
                               String usetype, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        JSONObject object = new JSONObject();
        UserBean vo = login.getData().getUser();
        try {

            object.put("staffid", vo.getId());
            object.put("targetid", targentid);
            object.put("comment", comment);
            object.put("commentid", commentid);
            object.put("usetype", usetype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_comment);
        requestHttpServciePost(mContext, url, object, true, callBackView);

    }

    /**
     * 点赞接口
     * @param targetid 赞主体编号
     * @param issupport 赞或取消赞 true 赞 false 取消赞
     * @param usetype  类型ac文章评论 a文章qc问题评论 vc视频评论

     * @param callBackView
     */
    public void subimtSpport(String targetid, String issupport, String usetype, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid",user.getId());
            obj.put("targetid",targetid);
            obj.put("issupport",issupport);
            obj.put("usetype",usetype);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_laud);
        requestHttpServciePost(mContext,url,obj,true,callBackView);
    }




}
