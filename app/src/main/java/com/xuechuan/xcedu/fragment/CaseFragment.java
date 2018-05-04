package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.bank.AtricleListActivity;
import com.xuechuan.xcedu.ui.bank.MockTestActivity;
import com.xuechuan.xcedu.ui.bank.MyErrorOrCollectTextActivity;

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
public class CaseFragment extends BaseFragment implements View.OnClickListener {
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
    private Context mContext;


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

    @Override
    protected int initInflateView() {
        return R.layout.fragment_case;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
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
        mLiBCaseError.setOnClickListener(this);
        mLlBCaseCollect.setOnClickListener(this);
        mIvBCaseOrder.setOnClickListener(this);
        mIvBCaseText.setOnClickListener(this);
        mTvBCaseFree.setOnClickListener(this);
        mTvBCaseShunxu.setOnClickListener(this);
        mTvBCaseZhuanxiang.setOnClickListener(this);
        mContext = getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_b_case_error://我的错题
                Intent intent1 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid, MyErrorOrCollectTextActivity.ERRTYPE);
                intent1.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的错题");
                startActivity(intent1);
                break;
            case R.id.ll_b_case_collect://我的收藏
                Intent intent2 = MyErrorOrCollectTextActivity.newInstance(mContext, mTypeOid, MyErrorOrCollectTextActivity.FAVTYPE);
                intent2.putExtra(MyErrorOrCollectTextActivity.CSTR_EXTRA_TITLE_STR, "我的收藏");
                startActivity(intent2);
                break;
            case R.id.iv_b_case_order://章节
                Intent intent = AtricleListActivity.newInstance(mContext, mTypeOid);
                intent.putExtra(AtricleListActivity.CSTR_EXTRA_TITLE_STR, "章节练习");
                startActivity(intent);
                break;
            case R.id.iv_b_case_text://考试
                Intent intent3 = MockTestActivity.newInstance(mContext, mTypeOid, DataMessageVo.MARKTYPECASE);
                intent3.putExtra(MockTestActivity.CSTR_EXTRA_TITLE_STR, "模拟考试");
                startActivity(intent3);
                break;
            case R.id.tv_b_case_free://自由
                break;
            case R.id.tv_b_case_zhuanxiang://专项
                break;
            case R.id.tv_b_case_shunxu://顺序
                break;
            default:
                break;
        }
    }
}
