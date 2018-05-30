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
import com.xuechuan.xcedu.adapter.MyOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MyMsgContract;
import com.xuechuan.xcedu.mvp.model.MyMsgModel;
import com.xuechuan.xcedu.mvp.presenter.MyMsgPresenter;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.MyOrderVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyMsgActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/5/22 15:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class MyMsgActivity extends BaseActivity implements MyMsgContract.View {

    private RecyclerView mRlvMyMsg;
    private ImageView mIvContentEmpty;
    private XRefreshView mXfvContentMsg;
    private List mArrary;
    private long lastRefreshTime;
    private Context mContext;
    private MyMsgPresenter mPresenter;
    private MyMsgAdapter adapter;
    private boolean isRefresh;
    /*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msg);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_msg);
        initView();
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContentMsg.startRefresh();
    }

    private void initData() {
        mPresenter = new MyMsgPresenter();
        mPresenter.initModelView(new MyMsgModel(),this);

    }

    private void initView() {
        mContext = this;
        mRlvMyMsg = (RecyclerView) findViewById(R.id.rlv_my_msg);
        mIvContentEmpty = (ImageView) findViewById(R.id.iv_content_empty);
        mXfvContentMsg = (XRefreshView) findViewById(R.id.xfv_content_msg);
    }
    private void initXrfresh() {
        mXfvContentMsg.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentMsg.setPullLoadEnable(true);
        mXfvContentMsg.setAutoLoadMore(true);
        mXfvContentMsg.setPullRefreshEnable(true);
        mXfvContentMsg.setEmptyView(mIvContentEmpty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXfvContentMsg.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter.requestMyMsg(mContext,getNowPage()+1);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestMyMsg(mContext,1);
    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new MyMsgAdapter(mContext, mArrary);
        mRlvMyMsg.setLayoutManager(gridLayoutManager);
        mRlvMyMsg.setAdapter(adapter);
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
    public void MyMsgSuccess(String con) {
        mXfvContentMsg.stopRefresh();
        isRefresh = false;
        Gson gson = new Gson();
        Log.e("=====", "MyMsgSuccess: "+con );
//        MyOrderVo orderVo = gson.fromJson(con, MyOrderVo.class);
//        if (orderVo.getStatus().getCode() == 200) {
//            List<MyOrderVo.DatasBean> datas = orderVo.getDatas();
//            clearData();
//            if (datas != null && !datas.isEmpty()) {
//                addListData(datas);
//            } else {
//                mXfvContentMsg.setPullRefreshEnable(true);
//                mXfvContentMsg.setLoadComplete(true);
//                adapter.notifyDataSetChanged();
//                return;
//            }
//            if (mArrary.size() < DataMessageVo.CINT_PANGE_SIZE || mArrary.size() == orderVo.getTotal().getTotal()) {
//                mXfvContentMsg.setPullRefreshEnable(true);
//                mXfvContentMsg.setLoadComplete(true);
//            } else {
//                mXfvContentMsg.setPullLoadEnable(true);
//                mXfvContentMsg.setLoadComplete(false);
//            }
//            adapter.notifyDataSetChanged();
//        } else {
//            isRefresh = false;
//            L.e(orderVo.getStatus().getMessage());
//        }
    }

    @Override
    public void MyMsgError(String con) {
        mXfvContentMsg.stopRefresh();
        isRefresh = false;
    }

    @Override
    public void MyMsgMoreSuccess(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        MyOrderVo orderVo = gson.fromJson(con, MyOrderVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<MyOrderVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentMsg.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentMsg.setLoadComplete(false);
                mXfvContentMsg.setPullLoadEnable(true);
            } else {
                mXfvContentMsg.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(orderVo.getStatus().getMessage());
        }
    }

    @Override
    public void MyMsgMoreError(String con) {

        isRefresh = false;
    }

    @Override
    public void DelMyMsg(String con) {

    }

    @Override
    public void DelMyError(String con) {

    }
}
