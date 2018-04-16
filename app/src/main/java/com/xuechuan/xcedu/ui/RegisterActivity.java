package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.CountdownUtil;
import com.xuechuan.xcedu.utils.T;
/**
 * @Title:  RegisterActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description:  注册界面
 * @author: L-BackPacker
 * @date:   2018/4/16 12:01
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private CountdownUtil mTimeUtils;
    private Button mBtnSend;
    private EditText mEtRegisterPaw;
    private EditText mEtRegisterPaws;
    private EditText mEtRegisterPhone;
    private EditText mEtRegisterCode;
    private Context mContext;
    private Button mBtnRegisterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {

        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEtRegisterPaw = (EditText) findViewById(R.id.et_register_paw);
        mEtRegisterPaw.setOnClickListener(this);
        mEtRegisterPaws = (EditText) findViewById(R.id.et_register_paws);
        mEtRegisterPaws.setOnClickListener(this);
        mEtRegisterPhone = (EditText) findViewById(R.id.et_register_phone);
        mEtRegisterPhone.setOnClickListener(this);
        mEtRegisterCode = (EditText) findViewById(R.id.et_register_code);
        mEtRegisterCode.setOnClickListener(this);
        mContext = this;
        mBtnRegisterLogin = (Button) findViewById(R.id.btn_register_login);
        mBtnRegisterLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                CountdownUtil.getInstance().startTime(mBtnSend);
                mBtnSend.setEnabled(false);
                sendRequestHttp();
                break;

            case R.id.btn_register_login:
                submit();
                break;
        }
    }

    /**
     * 请求验证码
     */
    private void sendRequestHttp() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        CountdownUtil.getInstance().stop();
    }

    private void submit() {
        String phone = getTextStr(mEtRegisterPhone);
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = getTextStr(mEtRegisterCode);
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        String paw = getTextStr(mEtRegisterPaw);
        if (TextUtils.isEmpty(paw)) {
            Toast.makeText(this, "账户密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String paws = getTextStr(mEtRegisterPaws);
        if (TextUtils.isEmpty(paws)) {
            Toast.makeText(this, "确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!paw.equals(paws)) {
            T.showToast(mContext, "密码错误,请重新输入");
            mEtRegisterPaw.setInputType(InputType.TYPE_CLASS_TEXT);
            mEtRegisterPaws.setInputType(InputType.TYPE_CLASS_TEXT);
            return;
        }

    }

}
