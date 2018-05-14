package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookinfomFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 网课详情页
 * @author: L-BackPacker
 * @date: 2018/5/14 17:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBookinfomFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public NetBookinfomFragment() {
    }

    public static NetBookinfomFragment newInstance(String param1, String param2) {
        NetBookinfomFragment fragment = new NetBookinfomFragment();
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
        View view = inflater.inflate(R.layout.fragment_net_bookinfom, container, false);
        return view;
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_bookinfom;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {

    }

}
