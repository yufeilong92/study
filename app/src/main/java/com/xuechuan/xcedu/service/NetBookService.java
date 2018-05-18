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
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.player.BaolIHttp.PolyvVlmsHelper;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
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
    private static final String BOOKID = "com.xuechuan.xcedu.service.extra.kid";
    private PolyvVlmsHelper helper;
    private List<ChaptersBeanVo> mDataList;
    final List<VideosBeanVo> vos = new ArrayList<>();
    private int mBitrate;
    List<PolyvVlmsHelper.CurriculumsDetail> lists;
    private String mBooKId;

    public NetBookService() {
        super("NetBookService");
    }

    /**
     * @param context
     * @param table   课程表
     * @param bitrate 码率
     * @param Kid     科目id
     */
    public static void startActionBaz(Context context, List table, int bitrate, String Kid) {
        Intent intent = new Intent(context, NetBookService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(TABLELIST, (Serializable) table);
        intent.putExtra(BITRATE, bitrate);
        intent.putExtra(BOOKID, Kid);
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
                mBooKId = intent.getStringExtra(BOOKID);
                handleActionBaz(mDataList, mBitrate, mBooKId);
            }
        }
    }

    /***
     * 视屏集合
     * @param mDataList
     * @param mBitrate 码率 1 流畅 2 高 3 超
     * @param mBooKId 科目id
     */
    private void handleActionBaz(List<ChaptersBeanVo> mDataList, int mBitrate, String mBooKId) {
        if (lists.size() > 0) {
            lists.clear();
        }
        if (vos.size() > 0) {
            vos.clear();
        }
        DownVideoDb db = new DownVideoDb();
        db.setKid(mBooKId);
        List<DownVideoVo> list = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            ChaptersBeanVo vo = mDataList.get(i);
            List<VideosBeanVo> videos = vo.getVideos();

            if (videos != null && !videos.isEmpty()) {
                for (int j = 0; j < videos.size(); j++) {
                    final VideosBeanVo beanVo = videos.get(j);
                    try {
                        addData(mBitrate, beanVo, list, mBitrate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.setDownlist(list);
        DbHelperDownAssist.getInstance().addDownItem(db);


    }

    private void addData(int mBitrate, VideosBeanVo beanVo, List<DownVideoVo> db, int bitrate) throws JSONException {
        DownVideoVo vo = new DownVideoVo();
        PolyvSDKUtil sdkUtil = new PolyvSDKUtil();
        PolyvVideoVO video = sdkUtil.loadVideoJSON2Video(beanVo.getVid());
        //总时长
        String duration = video.getDuration();
        //大小
        long type = video.getFileSizeMatchVideoType(mBitrate);
        vo.setDuration(duration);

        vo.setBitRate(String.valueOf(bitrate));

        vo.setFileSize(type);
        //视频id
        vo.setZid(String.valueOf(beanVo.getVideoid()));
        //篇id
        vo.setPid(String.valueOf(beanVo.getChapterid()));
        //保利视频id
        vo.setVid(beanVo.getVid());
        //视频名字
        vo.setTitle(beanVo.getVideoname());
        vo.setStatus("2");
        db.add(vo);
        Log.e("==视频信息==", bitrate + "\naddData:总时长 " + duration + "\n"
                + "总大小" + type + "\n"
                + beanVo.getVideoname() + "\n"
                + beanVo.getVid() + "\n");
    }


}
