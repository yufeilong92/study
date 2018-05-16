package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.NetTableAdapter;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetBookInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetBookInfomPresenter;
import com.xuechuan.xcedu.mvp.view.NetBookInfomView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;

import java.util.ArrayList;
import java.util.List;
/**
 * @Title:  NetTableFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 课程表
 * @author: L-BackPacker
 * @date:   2018/5/15 16:40
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/15
 */
public class NetTableFragment extends BaseFragment implements NetBookInfomView {

    private static final String CLASSID = "classid";
    private String mCalssId;
    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvSpecaContent;
    private XRefreshView mXrfvSpecaRefresh;
    List mArrary;
    private Context mContext;
    private NetTableAdapter adapter;
    private long lastRefreshTime;
    private NetBookInfomPresenter mPresenter;
    private boolean isRefresh;
    private View empty;


    public NetTableFragment() {

    }


    public static NetTableFragment newInstance(String calssid) {
        NetTableFragment fragment = new NetTableFragment();
        Bundle args = new Bundle();
        args.putString(CLASSID, calssid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalssId = getArguments().getString(CLASSID);
        }
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_net_table, container, false);
        initView(view);
        return view;
    }
*/
    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_table;
    }
    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        loadNewData();
//        mXrfvSpecaRefresh.startRefresh();
    }

    private void initData() {

    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(false);
        mXrfvSpecaRefresh.setEmptyView(empty);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
        mPresenter = new NetBookInfomPresenter(new NetBookInfomModelImpl(), this);
        mPresenter.requestVideoBookOneList(mContext, getNowPage() + 1, mCalssId);
    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter = new NetBookInfomPresenter(new NetBookInfomModelImpl(), this);
        mPresenter.requestVideoBookOneList(mContext, 1, mCalssId);

    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new NetTableAdapter(mContext, mArrary);
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);
    }

    private void initView(View view) {
        mContext = getActivity();
        empty = view.findViewById(R.id.tv_net_empty_content);
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) view.findViewById(R.id.xrfv_speca_refresh);
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
    public void VideoInfomSuccess(String result) {
        mXrfvSpecaRefresh.stopRefresh();
        mXrfvSpecaRefresh.setPullRefreshEnable(false);
        isRefresh = false;
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(result, NetBookTableVo.class);
        if (tableVo.getStatus().getCode() == 200) {
            NetBookTableVo.DataBean data = tableVo.getData();
            List<ChaptersBeanVo> chapters = data.getChapters();
            clearData();
            if (chapters != null && !chapters.isEmpty()) {
                addListData(chapters);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            if (mArrary.size() < DataMessageVo.CINT_PANGE_SIZE || mArrary.size() == tableVo.getTotal().getTotal()) {
                mXrfvSpecaRefresh.setLoadComplete(true);
            } else {
                mXrfvSpecaRefresh.setPullLoadEnable(true);
                mXrfvSpecaRefresh.setLoadComplete(false);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(tableVo.getStatus().getMessage());
        }

    }

    @Override
    public void VideoInfomError(String msg) {
        isRefresh = false;
    }

    @Override
    public void VideoInfomMoreSuccess(String result) {
        isRefresh = false;
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(result, NetBookTableVo.class);
        if (tableVo.getStatus().getCode() == 200) {
            NetBookTableVo.DataBean data = tableVo.getData();
            List<ChaptersBeanVo> chapters = data.getChapters();
//                    clearData();
            if (chapters != null && !chapters.isEmpty()) {
                addListData(chapters);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvSpecaRefresh.setLoadComplete(false);
                mXrfvSpecaRefresh.setPullLoadEnable(true);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(tableVo.getStatus().getMessage());
        }

    }
    @Override
    public void VideoInfomMoreError(String msg) {
        isRefresh = false;
    }
}
