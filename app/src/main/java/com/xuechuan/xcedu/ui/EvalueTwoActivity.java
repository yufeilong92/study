package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.EvalueModelImpl;
import com.xuechuan.xcedu.mvp.presenter.EvaluePresenter;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: EvalueTwoActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 带输入的评论
 * @author: YFL
 * @date: 2018/5/3  22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class EvalueTwoActivity extends BaseActivity implements View.OnClickListener, EvalueView {

    private RecyclerView mRlvInfomtwoContent;
    private XRefreshView mXfvContentTwoDetail;
    private EditText mEtInfomTwoContent;
    private Button mBtnInfomTwoSend;
    private RelativeLayout mRlInfomTwoLayout;
    private EvaluePresenter mPresenter;
    private Context mContext;
    private AlertDialog mDialog;
    /**
     * 问题id
     */
    public static String QUESTTION = "questtion";
    /**
     * 评价id
     */
    public static String COMMONID = "commonid";
    private String mQuestion;
    private String mCommonid;

    public static Intent newInstance(Context context, String question, String commonid) {
        Intent intent = new Intent(context, EvalueTwoActivity.class);
        intent.putExtra(QUESTTION, question);
        intent.putExtra(COMMONID, commonid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalue_two);
        initView();
        initData();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evalue_two);
        if (getIntent() != null) {
            mQuestion = getIntent().getStringExtra(QUESTTION);
            mCommonid = getIntent().getStringExtra(COMMONID);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new EvaluePresenter(new EvalueModelImpl(), this);
        mPresenter.requestEvalueContent(mContext, mQuestion, mCommonid);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
    }

    private void initView() {
        mContext = this;
        mRlvInfomtwoContent = (RecyclerView) findViewById(R.id.rlv_infomtwo_content);
        mXfvContentTwoDetail = (XRefreshView) findViewById(R.id.xfv_content_two_detail);
        mEtInfomTwoContent = (EditText) findViewById(R.id.et_infom_two_content);
        mBtnInfomTwoSend = (Button) findViewById(R.id.btn_infom_two_send);
        mRlInfomTwoLayout = (RelativeLayout) findViewById(R.id.rl_infom_two_layout);

        mBtnInfomTwoSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_infom_two_send:
                String str = getTextStr(mEtInfomTwoContent);
                if (StringUtil.isEmpty(str)) {
                    T.showToast(mContext, getStringWithId(R.string.content_is_empty));
                    return;
                }
                // TODO: 2018/5/3 提交二级评价
//                mPresenter.submitContent();
                Utils.hideInputMethod(mContext, mEtInfomTwoContent);
                break;
        }
    }


    @Override
    public void submitEvalueSuccess(String con) {

    }

    @Override
    public void submitEvalueError(String con) {

    }

    @Override
    public void GetEvalueSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.e(con);
    }

    @Override
    public void GetEvalueError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.e(con);
    }
}
