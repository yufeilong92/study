package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StatFs;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.xuechuan.xcedu.Event.NetDownEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.DownNetBookAdapter;
import com.xuechuan.xcedu.adapter.DownloadListViewAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.DownInfomSelectVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDowningActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 正在下载界面
 * @author: L-BackPacker
 * @date: 2018/5/20 16:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/20
 */
public class NetBookDowningActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvNetDowningMake;
    private TextView mTvNetDowningDo;
    private TextView mTvDowning;
    //    private RecyclerView mRlvNetDowningList;
    private ListView mRlvNetDowningList;
    private TextView mTvNetDowningStop;
    private CheckBox mChbNetDownAll;
    private Button mBtnNetDownDelect;
    private RelativeLayout mRlNetDownDelect;
    private Context mContext;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_downing);
        initView();
    }*/
    /**
     * 课目id
     */
    private static String KID = "KID";
    private String mKid;
    private DownVideoDb mDataBean;
    private List<DownInfomSelectVo> mDataSelectList;
    private DownNetBookAdapter mListAdapter;
    private DbHelperDownAssist mDao;

    public static Intent newInstance(Context context, String kid) {
        Intent intent = new Intent(context, NetBookDowningActivity.class);
        intent.putExtra(KID, kid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_downing);
        if (getIntent() != null) {
            mKid = getIntent().getStringExtra(KID);
        }
        mDao = DbHelperDownAssist.getInstance();
        initView();
        initData();
    }

    private void initData() {
        computeStorage();
        mDataBean = mDao.queryUserDownInfomWithKid(mKid);
        mDataSelectList = new ArrayList<>();
        for (DownVideoVo vo : mDataBean.getDownlist()) {
            DownInfomSelectVo selectVo = new DownInfomSelectVo();
            selectVo.setPid(vo.getPid());
            selectVo.setZid(vo.getZid());
            selectVo.setVid(vo.getVid());
            selectVo.setBitrate(vo.getBitRate());
            selectVo.setChbSelect(false);
            selectVo.setShowChb(false);
            selectVo.setShowPlay(true);
            selectVo.setPlaySelect(false);
            mDataSelectList.add(selectVo);
        }
        bindAdapter();
        mChbNetDownAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initShow(false, false, true, true);
                } else {
                    initShow(false, false, true, false);
                }
                mListAdapter.notifyDataSetChanged();
            }
        });


    }

    private void bindAdapter() {
//        DownloadListViewAdapter adapter = new DownloadListViewAdapter(mDataBean, mContext, mRlvNetDowningList);
        mListAdapter = new DownNetBookAdapter(mContext, mDataBean, mRlvNetDowningList, mDataSelectList);
        mRlvNetDowningList.setAdapter(mListAdapter);
        mListAdapter.setChbClickListener(new DownNetBookAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoVo db, boolean isCheck, int position) {
                if (mDataSelectList == null || mDataSelectList.isEmpty()) {
                    return;
                }
                if (isCheck) {
                    for (DownInfomSelectVo vo : mDataSelectList) {
                        vo.setChbSelect(true);
                    }
                } else {
                    for (DownInfomSelectVo vo : mDataSelectList) {
                        vo.setChbSelect(false);
                    }
                }
                if (mListAdapter != null)
                    mListAdapter.notifyDataSetChanged();
            }
        });


    }

    /**
     * 计算剩余内存
     */
    private void computeStorage() {
        long externalSize = 0;
        if (Utils.hasSDCard()) {
            File downloadDir = PolyvSDKClient.getInstance().getDownloadDir();
            StatFs statfs = new StatFs(downloadDir.getPath());
            long blocSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();
            externalSize = availaBlock * blocSize;
        } else {
            externalSize = Utils.getSystemAvailableSize();
        }
        String s = Utils.convertFileSizeB(externalSize);
        mTvDowning.setText(s);
    }

    private void initView() {
        mTvNetDowningMake = (TextView) findViewById(R.id.tv_net_downing_make);
        mTvNetDowningMake.setOnClickListener(this);
        mTvNetDowningDo = (TextView) findViewById(R.id.tv_net_downing_do);
        mTvDowning = (TextView) findViewById(R.id.tv_downing);
//        mRlvNetDowningList = (RecyclerView) findViewById(R.id.rlv_net_downing_list);
        mRlvNetDowningList = (ListView) findViewById(R.id.rlv_net_downing_list);
        mTvNetDowningStop = (TextView) findViewById(R.id.tv_net_downing_stop);
        mTvNetDowningStop.setOnClickListener(this);
        mChbNetDownAll = (CheckBox) findViewById(R.id.chb_net_down_all);
        mBtnNetDownDelect = (Button) findViewById(R.id.btn_net_down_delect);
        mRlNetDownDelect = (RelativeLayout) findViewById(R.id.rl_net_down_delect);
        mContext = this;
        mBtnNetDownDelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_delect://删除
                //选中的vid 集合
                for (int i = 0; i < mDataSelectList.size(); i++) {
                    DownInfomSelectVo selectVo = mDataSelectList.get(i);
                    if (selectVo.isChbSelect()) {
                        mDao.delectItem(mDataBean.getKid(), selectVo.getPid(), selectVo.getZid());
                        delectVideo(selectVo.getVid(), selectVo.getBitrate());
                    }
                }
                initData();
                mListAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_net_downing_make://编辑
                String str = getTextStr(mTvNetDowningMake);
                if (str.equals(getString(R.string.edit))) {
                    mTvNetDowningMake.setText(R.string.complete);
                    mTvNetDowningStop.setVisibility(View.GONE);
                    mRlNetDownDelect.setVisibility(View.VISIBLE);
                    if (mDataSelectList != null && !mDataSelectList.isEmpty())
                        initShow(false, false, true, false);
                } else {
                    mTvNetDowningStop.setVisibility(View.VISIBLE);
                    mRlNetDownDelect.setVisibility(View.GONE);
                    mTvNetDowningMake.setText(R.string.edit);
                    if (mDataSelectList != null && !mDataSelectList.isEmpty()) {
                        initShow(true, false, false, false);
                    }
                }
                mListAdapter.notifyDataSetChanged();

                break;
            case R.id.tv_net_downing_stop://开始下载
                String start = getTextStr(mTvNetDowningStop);
                if (start.equals(getString(R.string.start_down))) {//开始下载
                    mTvNetDowningStop.setText(R.string.stopdown);
                    mListAdapter.downloadAll();
                } else {//暂停下载
                    mTvNetDowningStop.setText(R.string.start_down);
                    mListAdapter.pauseAll();
                }
                break;
        }
    }

    private void delectVideo(String vid, String bitrate) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String vid = strings[0];
                String bitrates = strings[1];
                PolyvDownloader downloader = PolyvDownloaderManager.clearPolyvDownload(vid, Integer.parseInt(bitrates));
                downloader.deleteVideo();
                return null;
            }
        };
        asyncTask.execute(vid, bitrate);
    }

    /**
     * @param paly       是否展示播放按钮
     * @param selectplay 是否选中
     * @param chb        是否展示选择
     * @param selelctchb 是否选中
     */
    private void initShow(boolean paly, boolean selectplay, boolean chb, boolean selelctchb) {
        for (DownInfomSelectVo vo : mDataSelectList) {
            vo.setShowPlay(paly);
            vo.setPlaySelect(selectplay);
            vo.setShowChb(chb);
            vo.setChbSelect(selelctchb);
        }
    }
}
