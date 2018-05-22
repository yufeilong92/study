package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.PayModelImpl;
import com.xuechuan.xcedu.mvp.presenter.PayPresenter;
import com.xuechuan.xcedu.mvp.view.PayView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BankValueVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BankBuyActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 题库购买页
 * @author: L-BackPacker
 * @date: 2018/5/22 16:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class BankBuyActivity extends BaseActivity implements  PayView, View.OnClickListener {

    private Context mContext;
    /**
     * 课目id
     */
    private static String COUNTID = "countid";
    private int value = 0;
    private IWXAPI wxapi;
    private PayPresenter mPresenter;
    private LinearLayout mLlBBankPay;
    private CheckBox mChbBPaySkill;
    private TextView mTvBSkillPayValue;
    private CheckBox mChbBPayCollo;
    private TextView mTvBColloPayValue;
    private CheckBox mChbBPayCase;
    private TextView mTvBCasePayValue;
    private TextView mTvBPayCount;
    private CheckBox mChbBPayZfb;
    private LinearLayout mLlBPayZfb;
    private CheckBox mChbBPayWeixin;
    private LinearLayout mLlBWeixin;
    private Button mBtnBSubmitFrom;
    private double skillPrice=-1;
    private double colloPrice=-1;
    private double casePrice=-1;
    private int colloid;
    private int skillId;
    private int caseId;

    public static Intent newInstance(Context context, String countid) {
        Intent intent = new Intent(context, BankBuyActivity.class);
        intent.putExtra(COUNTID, countid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_buy);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bank_buy);
        initView();
        mPresenter = new PayPresenter(new PayModelImpl(), this);
        mPresenter.reuqestBookId(mContext);
    }

    private void initData(List<BankValueVo.DatasBean> bean) {
        bindData(bean);
        mChbBPaySkill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value += skillPrice;
                } else {
                    value -= skillPrice;
                }
                showCount();
            }
        });
        mChbBPayCollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value += colloPrice;
                } else {
                    value -= colloPrice;
                }
                showCount();
            }
        });
        mChbBPayCase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    value += casePrice;
                } else {
                    value -= casePrice;
                }
                showCount();
            }
        });
        mChbBPayZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPayChb(true, false);
                } else {
                    setPayChb(false, false);
                }
            }
        });
        mChbBPayWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPayChb(false, true);
                } else {
                    setPayChb(false, false);
                }
            }
        });

    }

    private void bindData(List<BankValueVo.DatasBean> bean) {
        for (BankValueVo.DatasBean vo : bean) {
            switch (vo.getCourseid()) {
                case 1://综合能力
                    mTvBColloPayValue.setText(String.valueOf(vo.getPrice()));
                    colloPrice = vo.getPrice();
                    colloid = vo.getId();
                    break;
                case 2://技术
                    mTvBSkillPayValue.setText(String.valueOf(vo.getPrice()));
                    skillPrice = vo.getPrice();
                    skillId = vo.getId();
                    break;
                case 3://案例
                    mTvBCasePayValue.setText(String.valueOf(vo.getPrice()));
                    casePrice = vo.getPrice();
                    caseId = vo.getId();
                    break;
                default:
            }


        }


    }

    private void showCount() {
        mTvBPayCount.setText(value + "");
    }

    private void setPayChb(boolean zfb, boolean weixin) {
        mChbBPayZfb.setChecked(zfb);
        mChbBPayWeixin.setChecked(weixin);
    }

    private void initView() {
        mContext = this;
        mLlBBankPay = (LinearLayout) findViewById(R.id.ll_b_bank_pay);
        mLlBBankPay.setOnClickListener(this);
        mChbBPaySkill = (CheckBox) findViewById(R.id.chb_b_pay_skill);
        mChbBPaySkill.setOnClickListener(this);
        mTvBSkillPayValue = (TextView) findViewById(R.id.tv_b_skill_pay_value);
        mTvBSkillPayValue.setOnClickListener(this);
        mChbBPayCollo = (CheckBox) findViewById(R.id.chb_b_pay_collo);
        mChbBPayCollo.setOnClickListener(this);
        mTvBColloPayValue = (TextView) findViewById(R.id.tv_b_collo_pay_value);
        mTvBColloPayValue.setOnClickListener(this);
        mChbBPayCase = (CheckBox) findViewById(R.id.chb_b_pay_case);
        mChbBPayCase.setOnClickListener(this);
        mTvBCasePayValue = (TextView) findViewById(R.id.tv_b_case_pay_value);
        mTvBCasePayValue.setOnClickListener(this);
        mTvBPayCount = (TextView) findViewById(R.id.tv_b_pay_count);
        mTvBPayCount.setOnClickListener(this);
        mChbBPayZfb = (CheckBox) findViewById(R.id.chb_b_pay_zfb);
        mChbBPayZfb.setOnClickListener(this);
        mLlBPayZfb = (LinearLayout) findViewById(R.id.ll_b_pay_zfb);
        mLlBPayZfb.setOnClickListener(this);
        mChbBPayWeixin = (CheckBox) findViewById(R.id.chb_b_pay_weixin);
        mChbBPayWeixin.setOnClickListener(this);
        mLlBWeixin = (LinearLayout) findViewById(R.id.ll_b_weixin);
        mLlBWeixin.setOnClickListener(this);
        mBtnBSubmitFrom = (Button) findViewById(R.id.btn_b_submit_from);
        mBtnBSubmitFrom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_b_submit_from://提交表单
                submit();
                break;

        }

    }

    private void submit() {
        int value = 0;
        int payType = -1;

        List<Integer> list = new ArrayList<>();
        if (mChbBPaySkill.isChecked()) {
            value += skillPrice;
            list.add(skillId);
        }
        if (mChbBPayCollo.isChecked()) {
            value += colloPrice;
            list.add(colloid);
        }
        if (mChbBPayCase.isChecked()) {
            value += casePrice;
            list.add(caseId);
        }
        if (value == 0) {
            T.showToast(mContext, "请选择要购买题库");
            return;
        }
        if (mChbBPayZfb.isChecked()) {
            payType = 1;
        }
        if (mChbBPayWeixin.isChecked()) {
            payType = 2;
        }
        if (payType == -1) {
            T.showToast(mContext, getString(R.string.pay_type));
            return;
        }
        mPresenter.submitPayFrom(mContext,String.valueOf(value),list,"app",null);

  /*      if (payType == 1) {//支付宝

        } else if (payType == 2) {//微信
            wxapi = WXAPIFactory.createWXAPI(mContext, "wx0c71e64b9e151c84");

        }*/


    }

    @Override
    public void SumbitFromSuccess(String con) {

    }

    @Override
    public void SumbitFromError(String con) {

    }

    @Override
    public void SumbitPaySuccess(String con) {

    }

    @Override
    public void SumbitPayError(String con) {

    }

    @Override
    public void BookIDSuccess(String con) {
        Gson gson = new Gson();
        BankValueVo vo = gson.fromJson(con, BankValueVo.class);
        if (vo.getStatus().getCode() == 200) {
            mLlBBankPay.setVisibility(View.VISIBLE);
            List<BankValueVo.DatasBean> list = vo.getDatas();
            initData(list);
        } else {
            L.e(vo.getStatus().getMessage());

        }
    }

    @Override
    public void BookIDError(String con) {
        L.e(con);
    }
}
