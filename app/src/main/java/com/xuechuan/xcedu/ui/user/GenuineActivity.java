package com.xuechuan.xcedu.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.ExchangeModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ExchangePresenter;
import com.xuechuan.xcedu.mvp.view.ExchangeView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GenuineActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 正版验证
 * @author: L-BackPacker
 * @date: 2018/5/22 12:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class GenuineActivity extends BaseActivity implements View.OnClickListener, ExchangeView {

    private EditText mEtMGCode;
    private Button mBtnMGValue;
    private Context mContext;
    private ExchangePresenter mPresenter;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genuine);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_genuine);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new ExchangePresenter(new ExchangeModelImpl(), this);

    }

    private void initView() {
        mEtMGCode = (EditText) findViewById(R.id.et_m_g_code);
        mBtnMGValue = (Button) findViewById(R.id.btn_m_g_value);

        mBtnMGValue.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_g_value:
                submit();
                break;
        }
    }

    private void submit() {
        String code = getTextStr(mEtMGCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, R.string.code_empty1);
            return;
        }
        mPresenter.requestExchangeWithCode(mContext, code);


    }

    @Override
    public void ExchangeSuccess(String com) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, "此验证是正版码");
        } else {
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void ExchangeError(String com) {
        L.e(com);
    }
}
