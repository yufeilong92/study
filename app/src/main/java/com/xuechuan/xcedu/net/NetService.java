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

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net.view
 * @Description: 网络
 * @author: L-BackPacker
 * @date: 2018/5/13 15:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetService extends BaseHttpServcie {
    private Context mContext;
    private static NetService service;
    private ArrayList<GetParamVo> vos;

    public NetService(Context mContext) {
        this.mContext = mContext;
    }

    public static NetService getInstance(Context context) {
        if (service == null) {
            service = new NetService(context);
        }
        return service;
    }

    /***
     * 获取网课首页内容
     * @param view
     */
    public void requestClassAndproductsList(StringCallBackView view) {
        String url = getUrl(R.string.http_getclassandproducts);
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        addPage(listParamVo,1);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 根据课程编号获取课程详情
     *
     * @param classid
     * @param view
     */
    public void requestProductdetail(String classid, int page, StringCallBackView view) {
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
        paramVo1.setParam("classid");
        paramVo1.setValue(classid);
        addPage(listParamVo, page);
        listParamVo.add(paramVo1);
        String url = getUrl(R.string.http_getproductdetail);
        requestHttpServiceGet(mContext, url, listParamVo, true, view);
    }

    /**
     * 网课进度提交
     *
     * @param videoId  当前视频编号
     * @param classid  所属课程（产品）
     * @param progress 进度
     * @param view
     */
    public void SubmitViewProgres(String videoId, String classid, String progress, StringCallBackView view) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("videoid", videoId);
            obj.put("classid", classid);
            obj.put("progress", progress);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getUrl(R.string.http_playprogresspost);
        requestHttpServciePost(mContext, url, obj, true, view);

    }

    /**
     * 网课下载视频提交
     * @param videoid 当前视频编号
     * @param classid  所属课程（产品）
     * @param view
     */
    public void submitDownLoadVideo(String videoid,String classid,StringCallBackView view){
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("videoid", videoid);
            obj.put("classid", classid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(R.string.http_videodownloadpost);
        requestHttpServciePost(mContext,url,obj,true,view);

    }


    private String getUrl(int str) {
        return mContext.getResources().getString(str);
    }


}
