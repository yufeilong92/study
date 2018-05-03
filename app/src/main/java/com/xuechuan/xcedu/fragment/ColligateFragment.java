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
import com.xuechuan.xcedu.base.BaseFragment;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: ColligateFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 综合能力
 * @author: YFL
 * @date: 2018/4/24  23:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ColligateFragment extends Fragment implements View.OnClickListener {
    private static final String TYPEOID = "typeoid";

    private String mTypeOid;
    private TextView mTvBCooWroing;
    private LinearLayout mLlBCoError;
    private TextView mTvBCoCoolect;
    private LinearLayout mLlBCoCollect;
    private ImageView mIvBCoOrder;
    private ImageView mIvBCoText;
    private TextView mTvBCoFree;
    private TextView mTvBCoZhuanxiang;
    private TextView mTvBCoShunxu;


    public ColligateFragment() {
    }

    public static ColligateFragment newInstance(String id) {
        ColligateFragment fragment = new ColligateFragment();
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
        View view = inflater.inflate(R.layout.fragment_colligate, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBCooWroing = (TextView) view.findViewById(R.id.tv_b_coo_wroing);
        mLlBCoError = (LinearLayout) view.findViewById(R.id.ll_b_co_error);
        mTvBCoCoolect = (TextView) view.findViewById(R.id.tv_b_co_coolect);
        mLlBCoCollect = (LinearLayout) view.findViewById(R.id.ll_b_co_collect);
        mIvBCoOrder = (ImageView) view.findViewById(R.id.iv_b_co_order);
        mIvBCoText = (ImageView) view.findViewById(R.id.iv_b_co_text);
        mTvBCoFree = (TextView) view.findViewById(R.id.tv_b_co_free);
        mTvBCoZhuanxiang = (TextView) view.findViewById(R.id.tv_b_co_zhuanxiang);
        mTvBCoShunxu = (TextView) view.findViewById(R.id.tv_b_co_shunxu);
        mLlBCoCollect.setOnClickListener(this);
        mLlBCoError.setOnClickListener(this);
        mIvBCoOrder.setOnClickListener(this);
        mIvBCoText.setOnClickListener(this);
        mTvBCoCoolect.setOnClickListener(this);
        mTvBCoFree.setOnClickListener(this);
        mTvBCoShunxu.setOnClickListener(this);
        mTvBCoZhuanxiang.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_b_co_collect://我的收藏
                break;
            case R.id.ll_b_co_error://我的错误
                break;
            case R.id.iv_b_co_order://章节练习
                break;
            case R.id.iv_b_co_text://模拟考试
                break;
            case R.id.tv_b_co_free://自由 组卷
                break;
            case R.id.tv_b_co_shunxu://顺序练习
                break;
            case R.id.tv_b_co_zhuanxiang://专项练习
                break;


            default:

        }
    }
}
