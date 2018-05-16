package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.NetTableAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetBookInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetBookInfomPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetMyBookVualueFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description 我的课程评价
 * @author: L-BackPacker
 * @date: 2018/5/16 14:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetMyBookVualueFragment extends BaseFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvSpecaContent;
    private XRefreshView mXrfvSpecaRefresh;
    private boolean isRefresh;
    private Context mContext;
    private List mArrary;
    private TextView mTvNetEmptyContent;
    private long lastRefreshTime;

    public NetMyBookVualueFragment() {
    }

    public static NetMyBookVualueFragment newInstance(String param1, String param2) {
        NetMyBookVualueFragment fragment = new NetMyBookVualueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_my_book_vualue, container, false);
        initView(view);
        return view;
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_my_book_vualue;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
        loadNewData();
    }

    private void initData() {

    }


    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;

    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
  /*      adapter = new NetTableAdapter(mContext, mArrary);
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);*/
    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(false);
        mXrfvSpecaRefresh.setEmptyView(mTvNetEmptyContent);
//        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
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
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) view.findViewById(R.id.xrfv_speca_refresh);
        mTvNetEmptyContent = (TextView) view.findViewById(R.id.tv_net_empty_content);
        mTvNetEmptyContent.setOnClickListener(this);
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
    public void onClick(View v) {

    }
}
