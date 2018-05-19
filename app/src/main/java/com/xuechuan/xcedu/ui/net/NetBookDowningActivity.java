package com.xuechuan.xcedu.ui.net;

import android.content.Context;
import android.os.Bundle;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.xuechuan.xcedu.Event.NetDownEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.DownloadListViewAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

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

    private DownVideoDb mDataBean;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void startNetDown(NetDownEvent net) {
        mDataBean = net.getBean();
        bindAdapter();
    }

    private void bindAdapter() {
        DownloadListViewAdapter adapter = new DownloadListViewAdapter(mDataBean, mContext, mRlvNetDowningList);
        mRlvNetDowningList.setAdapter(adapter);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_downing);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        computeStorage();
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
        mTvNetDowningDo = (TextView) findViewById(R.id.tv_net_downing_do);
        mTvDowning = (TextView) findViewById(R.id.tv_downing);
//        mRlvNetDowningList = (RecyclerView) findViewById(R.id.rlv_net_downing_list);
        mRlvNetDowningList = (ListView) findViewById(R.id.rlv_net_downing_list);
        mTvNetDowningStop = (TextView) findViewById(R.id.tv_net_downing_stop);
        mChbNetDownAll = (CheckBox) findViewById(R.id.chb_net_down_all);
        mBtnNetDownDelect = (Button) findViewById(R.id.btn_net_down_delect);
        mRlNetDownDelect = (RelativeLayout) findViewById(R.id.rl_net_down_delect);
        mContext = this;
        mBtnNetDownDelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_delect:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
