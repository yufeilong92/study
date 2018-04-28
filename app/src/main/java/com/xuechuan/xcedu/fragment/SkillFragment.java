package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.mvp.presenter.SkillController;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.ui.bank.AtricleTextListActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BuyVo;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.UserbuyOrInfomVo;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: SkillFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 技术实务
 * @author: YFL
 * @date: 2018/4/24  22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/24   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SkillFragment extends Fragment implements View.OnClickListener {
    private static final String TYPEOID = "typeoid";

    private String mTypeOid;
    private TextView mTvSkillWroing;
    private LinearLayout mLlBSkillError;
    private TextView mTvSkillCoolect;
    private LinearLayout mLlBSkillCollect;
    private ImageView mIvBOrder;
    private ImageView mIvBTest;
    private TextView mTvBFree;
    private TextView mTvBSpecial;
    private TextView mTvBTurn;
    private Context mContext;


    public SkillFragment() {
    }

    public static SkillFragment newInstance(String id) {
        SkillFragment fragment = new SkillFragment();
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
        View view = inflater.inflate(R.layout.fragment_skill, container, false);
        initView(view);
        requestBought();
        return view;
    }

    /**
     * 初始化用购买的科目
     */
    private void requestBought() {
        BankService bankService = new BankService(mContext);
        bankService.requestIsBought(mTypeOid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String msg = response.body().toString();
                Gson gson = new Gson();
                BuyVo vo = gson.fromJson(msg, BuyVo.class);
                if (vo.getStatus().getCode() == 200) {
                    BuyVo.DataBean data = vo.getData();
                    boolean isbought = data.isIsbought();
                    UserbuyOrInfomVo infomVo = new UserbuyOrInfomVo();
                    infomVo.setSkillbook(isbought);
                    SharedUserUtils.getInstance().putUserBuyVo(infomVo);
                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                L.e(response.message());
            }
        });

    }

    private void initView(View view) {
        mTvSkillWroing = (TextView) view.findViewById(R.id.tv_skill_wroing);
        mLlBSkillError = (LinearLayout) view.findViewById(R.id.ll_b_skill_error);
        mTvSkillCoolect = (TextView) view.findViewById(R.id.tv_skill_coolect);
        mLlBSkillCollect = (LinearLayout) view.findViewById(R.id.ll_b_skill_collect);
        mIvBOrder = (ImageView) view.findViewById(R.id.iv_b_order);
        mIvBTest = (ImageView) view.findViewById(R.id.iv_b_test);
        mTvBFree = (TextView) view.findViewById(R.id.tv_b_free);
        mTvBSpecial = (TextView) view.findViewById(R.id.tv_b_special);
        mTvBTurn = (TextView) view.findViewById(R.id.tv_b_turn);
        mTvBTurn.setOnClickListener(this);
        mIvBOrder.setOnClickListener(this);
        mLlBSkillError.setOnClickListener(this);
        mTvSkillCoolect.setOnClickListener(this);
        mLlBSkillCollect.setOnClickListener(this);
        mIvBTest.setOnClickListener(this);
        mTvBFree.setOnClickListener(this);
        mTvBSpecial.setOnClickListener(this);
        mContext = getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_b_skill_error://错题
                break;
            case R.id.ll_b_skill_collect://收藏
                break;
            case R.id.iv_b_order://章节
                Intent intent = AtricleTextListActivity.newInstance(mContext, mTypeOid);
                startActivity(intent);
                break;
            case R.id.iv_b_test://考试
                break;
            case R.id.tv_b_free://自由
                break;
            case R.id.tv_b_special://专项
                break;
            default:

        }
    }
}
