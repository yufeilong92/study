package com.xuechuan.xcedu.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.MyMsgAdapter;
import com.xuechuan.xcedu.adapter.MySystemAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.MySystemContract;
import com.xuechuan.xcedu.mvp.model.MySystemModel;
import com.xuechuan.xcedu.mvp.presenter.MySystemPresenter;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.utils.Defaultcontent;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.ShareUtils;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.MyOrderVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SystemVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

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

    private int mPosition = -1;


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
        mPresenter.requestSystemMsg(mContext, 1);
    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new MySystemAdapter(mContext, mArrary);
        mRlvMySystem.setLayoutManager(gridLayoutManager);
        mRlvMySystem.setAdapter(adapter);
        adapter.setClickListener(new MySystemAdapter.onItemClickListener() {
            @Override
            public void onClickListener(SystemVo.DatasBean obj, int position) {
                Intent intent = AgreementActivity.newInstance(mContext, obj.getGourl());
                intent.putExtra(AgreementActivity.CSTR_EXTRA_TITLE_STR, obj.getTitle());
                startActivity(intent);
            }
        });
        adapter.setClickLangListener(new MySystemAdapter.onItemLangClickListener() {
            @Override
            public void onClickLangListener(final SystemVo.DatasBean obj, int position) {
                submitDelSystem(obj, position);
            }
        });

    }

    /**
     * 提交删除
     *
     * @param obj
     * @param position
     */
    private void submitDelSystem(final SystemVo.DatasBean obj, final int position) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(obj.getId());
        mPosition = position;
        mPresenter.submitDelSystemMsg(mContext, list);
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
        SystemVo orderVo = gson.fromJson(con, SystemVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<SystemVo.DatasBean> datas = orderVo.getDatas();
            clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
                mXfvContentSystem.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            if (mArrary.size() < DataMessageVo.CINT_PANGE_SIZE || mArrary.size() == orderVo.getTotal().getTotal()) {
                mXfvContentSystem.setPullRefreshEnable(true);
                mXfvContentSystem.setLoadComplete(true);
            } else {
                mXfvContentSystem.setLoadComplete(false);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(orderVo.getStatus().getMessage());
        }
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
        SystemVo orderVo = gson.fromJson(con, SystemVo.class);
        if (orderVo.getStatus().getCode() == 200) {
            List<SystemVo.DatasBean> datas = orderVo.getDatas();
//                    clearData();
            if (datas != null && !datas.isEmpty()) {
                addListData(datas);
            } else {
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
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            mArrary.remove(mPosition);
            adapter.notifyDataSetChanged();
            T.showToast(mContext, getStringWithId(R.string.delect_Success));
        } else {
            T.showToast(mContext, getStringWithId(R.string.delect_error));
            String message = vo.getStatus().getMessage();
            L.e(message);
        }
    }

    @Override
    public void DelError(String con) {
        T.showToast(mContext, getStringWithId(R.string.delect_error));
    }

}
