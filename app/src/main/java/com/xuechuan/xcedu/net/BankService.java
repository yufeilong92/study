package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/20 16:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankService extends BaseHttpServcie {
    private Context mContext;
    private static BankService service;

    public BankService(Context mContext) {
        this.mContext = mContext;
    }

    public static BankService getInstance(Context context) {
        if (service == null) {
            service = new BankService(context);
        }
        return service;
    }

    /**
     * 获取科目编号章节列表
     *
     * @param couresid
     * @param callBackView
     */
    public void requestChapterList(String couresid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_chapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 根据科目获取所有练习题题号
     *
     * @param coruresid    科目编号
     * @param callBackView
     */
    public void requestCourseQuestionIdsList(String coruresid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(coruresid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_coursequestionids);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     *  题库首页获取错题和收藏数
     * @param courseid
     * @param callBackView
     */
    public void requestErrSetandFav(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_errsetandfav);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }
    public void requestChapterQuestionids(String chapterid,String usertype,StringCallBackView callBackView)
    {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("chapterid");
        paramVo.setValue(chapterid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("usetype");
        paramVo1.setValue(usertype);
        listParamVo.add(paramVo1);
    }


}
