package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.download.util.PolyvDownloaderUtils;
import com.xuechuan.xcedu.Event.NetDownDoneEvent;
import com.xuechuan.xcedu.Event.NetDownEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.NetDownGoingAdapter;
import com.xuechuan.xcedu.adapter.NetDownOverAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.NetDownSelectVo;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDownActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 下载详情
 * @author: L-BackPacker
 * @date: 2018/5/16 11:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetBookDownActivity extends BaseActivity implements View.OnClickListener {


    private Context mContext;
    private TextView mTvNetBookDownMake;
    private RecyclerView mRlvNetLoadingGoing;
    private LinearLayout mLlNetLoadingGoing;
    private RecyclerView mRlvLoadingOver;
    private LinearLayout mLlLoadingOver;
    private CheckBox mChbNetBookDownAll;
    private Button mBtnNetBookDownDelect;
    private LinearLayout mLlNetDownAll;
    private NetDownOverAdapter mOverAdapter;
    private NetDownGoingAdapter mNoDoneAdapter;
    private List<NetDownSelectVo> mSelectDoneVos;
    private List<NetDownSelectVo> mSelectNOVos;
    private DbHelperDownAssist mDao;
    private AlertDialog mDelectdialog;
    private LinearLayout mLlNetBookDownAll;

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_down);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_down);
        initView();
        initData(false, false);
        initKong();
    }

    private void initKong() {
        mChbNetBookDownAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                            net.setSelect(true);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                            net.setSelect(true);
                        }
                    }

                } else {
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                            net.setSelect(false);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                            net.setSelect(false);
                        }
                    }
                }
                mNoDoneAdapter.notifyDataSetChanged();
                mOverAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData(boolean isShow, boolean isSelect) {
        computeStorage();
        mDao = DbHelperDownAssist.getInstance();
        //记录那些没有缓存完成的
        List<DownVideoDb> downVideoDbs = new ArrayList<>();
        List<DownVideoDb> dbs = mDao.queryUserDownInfom();
        for (int i = 0; i < dbs.size(); i++) {
            DownVideoDb db = dbs.get(i);
            List<DownVideoVo> downlist = db.getDownlist();
            if (downlist != null && !downlist.isEmpty()) {
                for (int j = 0; j < downlist.size(); j++) {
                    DownVideoVo vo = downlist.get(j);
                    if (!vo.getStatus().equals("0")) {
                        downVideoDbs.add(db);
                        break;
                    }
                }
            }
        }
        dbs.removeAll(downVideoDbs);
        if (dbs != null && !dbs.isEmpty()) {
            mSelectDoneVos = new ArrayList<>();
            for (int i = 0; i < dbs.size(); i++) {
                DownVideoDb db = dbs.get(i);
                NetDownSelectVo vo = new NetDownSelectVo();
                vo.setId(db.getKid());
                vo.setSelect(isSelect);
                vo.setShow(isShow);
                mSelectDoneVos.add(vo);
            }
            bindDoneViewData(dbs);
        }
        mSelectNOVos = new ArrayList<>();
        for (int i = 0; i < downVideoDbs.size(); i++) {
            DownVideoDb db = downVideoDbs.get(i);
            NetDownSelectVo vo = new NetDownSelectVo();
            vo.setId(db.getKid());
            vo.setSelect(isSelect);
            vo.setShow(isShow);
            mSelectNOVos.add(vo);
        }
        bindViewData(dbs, downVideoDbs);
    }

    /**
     * @param over 已完成
     * @param no   未完成
     */
    private void bindViewData(List<DownVideoDb> over, List<DownVideoDb> no) {
        bindDoneViewData(over);
        bindNoDoneViewData(no);
    }

    /**
     * 绑定完成的数据
     *
     * @param dbs
     */
    private void bindDoneViewData(List<DownVideoDb> dbs) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvLoadingOver.setLayoutManager(manager);
        mOverAdapter = new NetDownOverAdapter(mContext, dbs, mSelectDoneVos);
        mRlvLoadingOver.setAdapter(mOverAdapter);
        mOverAdapter.setClickListener(new NetDownOverAdapter.onItemClickListener() {
            @Override
            public void onClickListener(DownVideoDb db, int position) {
                Intent intent = NetBookDownInfonActivity.newInstance(mContext, db.getKid());
                startActivity(intent);
            }
        });
        mOverAdapter.setChbClickListener(new NetDownOverAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoDb db, boolean isCheck, int position) {
                if (mSelectDoneVos == null || mSelectDoneVos.isEmpty()) {
                    return;
                }
                for (NetDownSelectVo mSelectNOVo : mSelectDoneVos) {
                    if (mSelectNOVo.getId().equals(db.getKid())) {
                        mSelectNOVo.setSelect(isCheck);
                    }
                }
            }
        });

    }

    /**
     * 绑定未完成的数据
     *
     * @param downVideoDbs
     */
    private void bindNoDoneViewData(List<DownVideoDb> downVideoDbs) {
        GridLayoutManager manager = new GridLayoutManager(NetBookDownActivity.this, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvNetLoadingGoing.setLayoutManager(manager);
        mNoDoneAdapter = new NetDownGoingAdapter(mContext, downVideoDbs, mSelectNOVos);
        mRlvNetLoadingGoing.setAdapter(mNoDoneAdapter);
        mNoDoneAdapter.setClickListener(new NetDownGoingAdapter.onItemClickListener() {
            @Override
            public void onClickListener(DownVideoDb db, int position) {
                EventBus.getDefault().postSticky(new NetDownEvent(db));
                startActivity(new Intent(NetBookDownActivity.this, NetBookDowningActivity.class));
            }
        });
        mNoDoneAdapter.setChbClickListener(new NetDownGoingAdapter.onItemChbClickListener() {
            @Override
            public void onChecaListener(DownVideoDb db, boolean isCheck, int position) {
                if (mSelectNOVos == null || mSelectNOVos.isEmpty()) {
                    return;
                }
                for (NetDownSelectVo mSelectNOVo : mSelectNOVos) {
                    if (mSelectNOVo.getId().equals(db.getKid())) {
                        mSelectNOVo.setSelect(isCheck);
                    }
                }
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
    }

    private void initView() {
        mContext = this;
        mTvNetBookDownMake = (TextView) findViewById(R.id.tv_net_book_down_make);
        mTvNetBookDownMake.setOnClickListener(this);
        mRlvNetLoadingGoing = (RecyclerView) findViewById(R.id.rlv_net_loading_going);
        mRlvNetLoadingGoing.setOnClickListener(this);
        mLlNetLoadingGoing = (LinearLayout) findViewById(R.id.ll_net_loading_going);
        mLlNetLoadingGoing.setOnClickListener(this);
        mRlvLoadingOver = (RecyclerView) findViewById(R.id.rlv_loading_over);
        mRlvLoadingOver.setOnClickListener(this);
        mLlLoadingOver = (LinearLayout) findViewById(R.id.ll_loading_over);
        mLlLoadingOver.setOnClickListener(this);
        mChbNetBookDownAll = (CheckBox) findViewById(R.id.chb_net_book_down_all);
        mBtnNetBookDownDelect = (Button) findViewById(R.id.btn_net_book_down_delect);
        mBtnNetBookDownDelect.setOnClickListener(this);
        mLlNetDownAll = (LinearLayout) findViewById(R.id.ll_net_book_down_all);
        mLlNetDownAll.setOnClickListener(this);
        mLlNetBookDownAll = (LinearLayout) findViewById(R.id.ll_net_book_down_all);
        mLlNetBookDownAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_book_down_delect:
                if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty())
                    for (NetDownSelectVo vo : mSelectDoneVos) {
                        if (vo.isSelect()) {
                            DownVideoDb db = mDao.queryUserDownInfomWithKid(vo.getId());
                            delectVideo(db.getDownlist());
                            mDao.delectKItem(vo.getId());

                        }
                    }
                if (mSelectNOVos != null && !mSelectNOVos.isEmpty())
                    for (NetDownSelectVo vo : mSelectNOVos) {
                        if (vo.isSelect()) {
                            DownVideoDb db = mDao.queryUserDownInfomWithKid(vo.getId());
                            delectVideo(db.getDownlist());
                            mDao.delectKItem(vo.getId());
                        }
                    }
                initData(true, false);
                mNoDoneAdapter.notifyDataSetChanged();
                mOverAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_net_book_down_make:
                String trim = mTvNetBookDownMake.getText().toString().trim();
                if (trim.equals(getString(R.string.edit))) {
                    mLlNetDownAll.setVisibility(View.VISIBLE);
                    mTvNetBookDownMake.setText(R.string.complete);
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(true);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(true);
                        }
                    }
                    mNoDoneAdapter.notifyDataSetChanged();
                    mOverAdapter.notifyDataSetChanged();

                } else {
                    mLlNetDownAll.setVisibility(View.GONE);
                    mTvNetBookDownMake.setText(R.string.edit);
                    if (mSelectDoneVos != null && !mSelectDoneVos.isEmpty()) {
                        for (int i = 0; i < mSelectDoneVos.size(); i++) {
                            NetDownSelectVo net = mSelectDoneVos.get(i);
                            net.setShow(false);
                        }
                    }
                    if (mSelectNOVos != null && !mSelectNOVos.isEmpty()) {
                        for (int i = 0; i < mSelectNOVos.size(); i++) {
                            NetDownSelectVo net = mSelectNOVos.get(i);
                            net.setShow(false);
                        }
                    }
                    mNoDoneAdapter.notifyDataSetChanged();
                    mOverAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
//    static int	BITRATE_ERROR
//    码率（清晰度）错误
//    static int	DELETE_VIDEO_FILE_FAIL
//    删除文件失败
//    static int	DELETE_VIDEO_SUCCESS
//    删除视频成功
//    static int	DOWNLOADER_DIR_NULL
//    下载文件夹为null（未设置）
//    static int	VIDEO_ID_ERROR
//    视频id错误

    private void delectVideo(List<DownVideoVo> vid) {
        if (vid == null || vid.isEmpty()) {
            return;
        }
        for (DownVideoVo vo : vid) {
            if (vo.getPercent() > 0) {
                delectVideo(vo.getVid());
            }
        }
    }

    private void delectVideo(String vid) {
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String string = strings[0];
                Log.e("=====", "删除视频id: " + string);
                PolyvDownloaderUtils utils = new PolyvDownloaderUtils();
                int i = utils.deleteVideo(string);
                Log.e("yfl", "删除视频结果: " + i);
                return null;
            }
        };
        asyncTask.execute(vid);
    }
}
