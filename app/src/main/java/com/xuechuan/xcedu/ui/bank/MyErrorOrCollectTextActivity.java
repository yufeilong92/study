package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.ErrorTextAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.ErrorModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ErrorTextPresenter;
import com.xuechuan.xcedu.mvp.view.ErrorTextView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ErrorVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyErrorTextActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 我的错题/收藏
 * @author: L-BackPacker
 * @date: 2018/5/3 20:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3
 */
public class MyErrorOrCollectTextActivity extends BaseActivity implements ErrorTextView, View.OnClickListener {

    private ImageView mIvBMore;
    private TextView mTvErrorNumber;
    private Button mBtnGoDoText;
    private RecyclerView mRlvErrorList;
    private ErrorTextPresenter mPresenter;
    private Context mContext;
    /**
     * 科目id
     */
    private static String COURESID = "couresid";
    /**
     * 题干类型
     */
    private static String TEXTTYPE = "texttype";
    public static String ERRTYPE = "err";
    public static String FAVTYPE = "fav";
    private String mOid;
    private AlertDialog mDialog;
    private String mType;
    private TextView mTvErrorText;
    private LinearLayout mLlErrorHear;
    //类型内容
    private String content;

    public static Intent newInstance(Context context, String Couresid, String textType) {
        Intent intent = new Intent(context, MyErrorOrCollectTextActivity.class);
        intent.putExtra(COURESID, Couresid);
        intent.putExtra(TEXTTYPE, textType);
        return intent;
    }

    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_error_text);
           initView();
       }
   */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_error_text);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURESID);
            mType = getIntent().getStringExtra(TEXTTYPE);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new ErrorTextPresenter(new ErrorModelImpl(), this);
        //请求错误数据
        String con = null;
        if (mType.equals(ERRTYPE)) {
            con = ERRTYPE;
            content = getString(R.string.myError);
            mLlErrorHear.setBackgroundResource(R.drawable.ic_wt_bg);
        } else if (mType.equals(FAVTYPE)) {
            con = FAVTYPE;
            content = getString(R.string.MyCollor);
            mLlErrorHear.setBackgroundResource(R.drawable.ic_col_bg);
        }

        mPresenter.reqeusetQuestionCount(mContext, mOid, con);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mTvErrorText.setText(content);

    }

    @Override
    public void ErrorSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.e("yfl" + con);
        Gson gson = new Gson();
        ErrorVo vo = gson.fromJson(con, ErrorVo.class);
        if (vo.getStatus().getCode() == 200) {
            bindViewData(vo);
            if (vo.getDatas() != null) {
                bindAdapter(vo.getDatas());
            }
        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    /**
     * 绑定数据
     *
     * @param vo
     */
    private void bindViewData(ErrorVo vo) {
        mTvErrorNumber.setText(vo.getTotal().getTotal() + "");
    }

    private void bindAdapter(List<ErrorVo.DatasBean> details) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        ErrorTextAdapter adapter = new ErrorTextAdapter(mContext, details);
        mRlvErrorList.setLayoutManager(manager);
        mRlvErrorList.setAdapter(adapter);
        adapter.setClickListener(new ErrorTextAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ErrorVo.DatasBean vo = (ErrorVo.DatasBean) obj;
                Toast.makeText(MyErrorOrCollectTextActivity.this, vo.getTagname(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void ErrorError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void initView() {
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mTvErrorNumber = (TextView) findViewById(R.id.tv_error_number);
        mBtnGoDoText = (Button) findViewById(R.id.btn_go_do_text);
        mRlvErrorList = (RecyclerView) findViewById(R.id.rlv_error_list);
        mContext = this;
        mBtnGoDoText.setOnClickListener(this);
        mTvErrorText = (TextView) findViewById(R.id.tv_error_text);
        mTvErrorText.setOnClickListener(this);
        mLlErrorHear = (LinearLayout) findViewById(R.id.ll_error_hear);
        mLlErrorHear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_do_text:

                break;
        }
    }
}
