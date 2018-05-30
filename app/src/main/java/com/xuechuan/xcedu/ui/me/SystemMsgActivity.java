package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.MyMsgAdapter;
import com.xuechuan.xcedu.adapter.MySystemAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MySystemContract;
import com.xuechuan.xcedu.mvp.model.MySystemModel;
import com.xuechuan.xcedu.mvp.presenter.MySystemPresenter;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.MyOrderVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SystemMsgActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 系统设置消息
 * @author: L-BackPacker
 * @date: 2018/5/30 18:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/30
 */
public class SystemMsgActivity extends BaseActivity implements MySystemContract.View {

    private RecyclerView mRlvMySystem;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentSystem;
    private List mArrary;
    private Context mContext;
    private long lastRefreshTime;
    private boolean isRefresh;
    private MySystemPresenter mPresenter;
    private MySystemAdapter adapter;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_msg);
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentSystem.startRefresh();
    }

    private void initData() {
        mPresenter = new MySystemPresenter();
        mPresenter.initModelView(new MySystemModel(), this);
    }

    private void initView() {
        mContext = this;
        mRlvMySystem = (RecyclerView) findViewById(R.id.rlv_my_system);
        mIvContentEmpty = (ImageView) findViewById(R.id.iv_content_empty);
        mXfvContentSystem = (XRefreshView) findViewById(R.id.xfv_content_system);
    }

    private void initXrfresh() {
        mXfvContentSystem.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentSystem.setPullLoadEnable(true);
        mXfvContentSystem.setAutoLoadMore(true);
        mXfvContentSystem.setPullRefreshEnable(true);
        mXfvContentSystem.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentSystem.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }
            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }

        });

    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestSystemMoreMsg(mContext, getNowPage() + 1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestSystemMsg(mContext,1);
    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new MySystemAdapter(mContext, mArrary);
        mRlvMySystem.setLayoutManager(gridLayoutManager);
        mRlvMySystem.setAdapter(adapter);

    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrary == null || mArrary.isEmpty())
            return 0;
        if (mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }


    @Override
    public void SystemSuccess(String con) {
        mXfvContentSystem.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        //        MyOrderVo orderVo = gson.fromJson(con, MyOrderVo.class);
//        if (orderVo.getStatus().getCode() == 200) {
//            List<MyOrderVo.DatasBean> datas = orderVo.getDatas();
//            clearData();
//            if (datas != null && !datas.isEmpty()) {
//                addListData(datas);
//            } else {
//                mXfvContentSystem.setPullRefreshEnable(true);
//                mXfvContentSystem.setLoadComplete(true);
//                adapter.notifyDataSetChanged();
//                return;
//            }
//            if (mArrary.size() < DataMessageVo.CINT_PANGE_SIZE || mArrary.size() == orderVo.getTotal().getTotal()) {
//                mXfvContentSystem.setPullRefreshEnable(true);
//                mXfvContentSystem.setLoadComplete(true);
//            } else {
//
//                mXfvContentSystem.setLoadComplete(false);
//            }
//            adapter.notifyDataSetChanged();
//        } else {
//            isRefresh = false;
//            L.e(orderVo.getStatus().getMessage());
//        }
    }

    @Override
    public void SystemErrorr(String con) {
        mXfvContentSystem.stopRefresh();
        isRefresh = false;
    }

    @Override
    public void SystemMoreSuccess(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderVo orderVo = gson.fromJson(con, MyOrderVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentSystem.setPullLoadEnable(true);
                mXfvContentSystem.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentSystem.setLoadComplete(false);
                mXfvContentSystem.setPullLoadEnable(true);
            } else {
                mXfvContentSystem.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void SystemMoreErrorr(String con) {
        isRefresh = false;
    }

    @Override
    public void DelSuccess(String con) {

    }

    @Override
    public void DelError(String con) {

    }
}