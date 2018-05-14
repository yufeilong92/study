package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetBookInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetBookInfomPresenter;
import com.xuechuan.xcedu.mvp.view.NetBookInfomView;
import com.xuechuan.xcedu.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBooKListFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 课程表。
 * @author: L-BackPacker
 * @date: 2018/5/14 17:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBooKListFragment extends BaseFragment implements NetBookInfomView {
    private static final String CLASSID = "classid";
    private static final String ARG_PARAM2 = "param2";

    private String mCalssId;
    private RecyclerView mRlvSpecaContent;
    private XRefreshView mXrfvSpecaRefresh;

    private List mArrary;
    private NetBookInfomPresenter mPresenter;
    private Context mContext;

    public NetBooKListFragment() {
    }


    public static NetBooKListFragment newInstance(String calssid) {
        NetBooKListFragment fragment = new NetBooKListFragment();
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

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_boo_klist, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_boo_klist;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();

//        bindAdapterData();
//        initXrfresh();
//        mXrfvSpecaRefresh.startRefresh();
    }

    private void initData() {
        mPresenter = new NetBookInfomPresenter(new NetBookInfomModelImpl(), this);
        mPresenter.requestVideoBookOneList(mContext, 1, mCalssId);
    }


    private void initView(View view) {
        mContext = getActivity();
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
        L.d(result);
    }

    @Override
    public void VideoInfomError(String msg) {
        L.e(msg);
    }

    @Override
    public void VideoInfomMoreSuccess(String result) {

    }

    @Override
    public void VideoInfomMoreError(String msg) {

    }
}
