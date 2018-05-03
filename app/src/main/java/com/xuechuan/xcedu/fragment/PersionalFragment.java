package com.xuechuan.xcedu.fragment;

import android.os.Bundle;
import android.view.View;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;

/**
 * @Title:  PersionalFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 我的信息
 * @author: L-BackPacker
 * @date:   2018/4/11 17:15
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class PersionalFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PersionalFragment() {
    }

     public static PersionalFragment newInstance(String param1, String param2) {
        PersionalFragment fragment = new PersionalFragment();
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
    protected int initInflateView() {
        return R.layout.fragment_persional;
    }

    @Override
    protected void initCreateView(View view,Bundle savedInstanceState) {

    }

}
