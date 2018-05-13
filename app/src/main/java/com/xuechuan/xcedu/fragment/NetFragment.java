package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.mvp.model.NetHomeModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetHomePresenter;
import com.xuechuan.xcedu.mvp.view.NetHomeView;
import com.xuechuan.xcedu.utils.L;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 网课
 * @author: L-BackPacker
 * @date: 2018/4/11 17:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class NetFragment extends BaseFragment implements NetHomeView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvInfomList;
    private XRefreshView mXfvContent;
    private NetHomePresenter mPresenter;
    private Context mContext;

    public NetFragment() {
    }

    public static NetFragment newInstance(String param1, String param2) {
        NetFragment fragment = new NetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = View.inflate(getActivity(), R.layout.fragment_net, null);
            initView(view);
            return view;
        }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        mPresenter = new NetHomePresenter(new NetHomeModelImpl(), this);
        mPresenter.requestClassSandProducts(mContext);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvInfomList = (RecyclerView) view.findViewById(R.id.rlv_infom_list);
        mXfvContent = (XRefreshView) view.findViewById(R.id.xfv_content);
    }

    @Override
    public void ClassListContSuccess(String msg) {
        L.d("Class"+msg);
    }

    @Override
    public void ClassListContError(String msg) {
        L.e("Class"+msg);
    }
}
