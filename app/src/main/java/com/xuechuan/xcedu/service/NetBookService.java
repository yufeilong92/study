package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.PolyvSDKUtil;
import com.easefun.polyvsdk.Video;
import com.easefun.polyvsdk.vo.PolyvVideoVO;
import com.easefun.polyvsdk.vo.b;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.player.BaolIHttp.PolyvVlmsHelper;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookService
 * @Package com.xuechuan.xcedu.service
 * @Description: 请求保利视频集合
 * @author: L-BackPacker
 * @date: 2018/5/17 15:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/17
 */
public class NetBookService extends IntentService {
    private static final String ACTION_BAZ = "com.xuechuan.xcedu.service.action.BAZ";
    private static final String TABLELIST = "com.xuechuan.xcedu.service.extra.listbook";
    private static final String BITRATE = "com.xuechuan.xcedu.service.extra.BITRATE";
    private PolyvVlmsHelper helper;
    private List<ChaptersBeanVo> mDataList;
    final List<VideosBeanVo> vos = new ArrayList<>();
    private int mBitrate;
    List<PolyvVlmsHelper.CurriculumsDetail> lists;

    public NetBookService() {
        super("NetBookService");
    }

    public static void startActionBaz(Context context, List table, int bitrate) {
        Intent intent = new Intent(context, NetBookService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(TABLELIST, (Serializable) table);
        intent.putExtra(BITRATE, bitrate);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new PolyvVlmsHelper();
        lists = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BAZ.equals(action)) {
                mDataList = (List<ChaptersBeanVo>) intent.getSerializableExtra(TABLELIST);
                mBitrate = intent.getIntExtra(TABLELIST, 3);
                handleActionBaz(mDataList, mBitrate);
            }
        }
    }

    /***
     * 视屏集合
     * @param mDataList
     * @param mBitrate 码率 1 流畅 2 高 3 超
     */
    private void handleActionBaz(List<ChaptersBeanVo> mDataList, int mBitrate) {
        Log.e("========", "handleActionBaz: " + mDataList.size() + "///" + mBitrate);
        if (lists.size() > 0) {
            lists.clear();
        }
        if (vos.size() > 0) {
            vos.clear();
        }
        int a=0;
        for (int i = 0; i < mDataList.size(); i++) {
            ChaptersBeanVo vo = mDataList.get(i);
            List<VideosBeanVo> videos = vo.getVideos();
            if (videos != null && !videos.isEmpty()) {
                for (int j = 0; j < videos.size(); j++) {
                    final VideosBeanVo beanVo = videos.get(j);
                    ++a;
                    PolyvSDKUtil sdkUtil = new PolyvSDKUtil();
                    try {
                        PolyvVideoVO video = sdkUtil.loadVideoJSON2Video(beanVo.getVid());
                        int dfNum = video.getDfNum();
                        double ratio = video.getRatio();
                        Log.e("yfl", a+"handleActionBaz: " +ratio);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        bookResult.fail(vos);
//        bookResult.netBookInfomSuccess(lists);
    }

    public RequestNetBookResult bookResult;

    public static interface RequestNetBookResult {
        public void netBookInfomSuccess(List<PolyvVlmsHelper.CurriculumsDetail> curriculumsDetails);

        public void fail(List<VideosBeanVo> vos);
    }

    public void setBookResult(RequestNetBookResult bookResult) {
        this.bookResult = bookResult;
    }
}
