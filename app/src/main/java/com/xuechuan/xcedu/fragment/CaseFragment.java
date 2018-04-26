package com.xuechuan.xcedu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: CaseFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 案例分析
 * @author: YFL
 * @date: 2018/4/24  23:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class CaseFragment extends Fragment {
    private static final String TYPEOID = "typeoid";

    private String mTypeOid;
    private TextView mTvCaseWroing;
    private LinearLayout mLiBCaseError;
    private TextView mTvCaseCoolect;
    private LinearLayout mLlBCaseCollect;
    private ImageView mIvBCaseOrder;
    private ImageView mIvBCaseText;
    private TextView mTvBCaseFree;
    private TextView mTvBCaseZhuanxiang;
    private TextView mTvBCaseShunxu;


    public CaseFragment() {
    }

    public static CaseFragment newInstance(String id) {
        CaseFragment fragment = new CaseFragment();
        Bundle args = new Bundle();
        args.putString(TYPEOID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTypeOid = getArguments().getString(TYPEOID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mTvCaseWroing = (TextView) view.findViewById(R.id.tv_case_wroing);
        mLiBCaseError = (LinearLayout) view.findViewById(R.id.li_b_case_error);
        mTvCaseCoolect = (TextView) view.findViewById(R.id.tv_case_coolect);
        mLlBCaseCollect = (LinearLayout) view.findViewById(R.id.ll_b_case_collect);
        mIvBCaseOrder = (ImageView) view.findViewById(R.id.iv_b_case_order);
        mIvBCaseText = (ImageView) view.findViewById(R.id.iv_b_case_text);
        mTvBCaseFree = (TextView) view.findViewById(R.id.tv_b_case_free);
        mTvBCaseZhuanxiang = (TextView) view.findViewById(R.id.tv_b_case_zhuanxiang);
        mTvBCaseShunxu = (TextView) view.findViewById(R.id.tv_b_case_shunxu);
    }

}
