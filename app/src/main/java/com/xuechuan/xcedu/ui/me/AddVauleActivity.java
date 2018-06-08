package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.AddVauleModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AddVaulePresenter;
import com.xuechuan.xcedu.mvp.view.AddVauleView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddVauleActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 增值服务
 * @author: L-BackPacker
 * @date: 2018/5/22 11:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class AddVauleActivity extends BaseActivity implements View.OnClickListener, AddVauleView {

    private EditText mEtMAddCode;
    private Button mBtnMAddValue;
    private Context mContext;
    private AddVaulePresenter mPresenter;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaule);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_vaule);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new AddVaulePresenter(new AddVauleModelImpl(), this);
    }
    private void initView() {
        mEtMAddCode = (EditText) findViewById(R.id.et_m_add_code);
        mBtnMAddValue = (Button) findViewById(R.id.btn_m_add_value);
        mBtnMAddValue.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_add_value:
                submit();
                break;
        }
    }

    private void submit() {
        String code = getTextStr(mEtMAddCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, R.string.code_empty);
            return;
        }
        mPresenter.requestAddValueWithCode(mContext, code);

    }

    @Override
    public void AddVauleSuccess(String com) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, getString(R.string.code_suceess));
        } else {
            T.showToast(mContext, getString(R.string.code_error));
            L.e(vo.getStatus().getMessage());
        }

    }

    @Override
    public void AddVauleError(String com) {
        T.showToast(mContext,mContext.getResources().getString(R.string.net_error));
        L.e(com);
    }
}
