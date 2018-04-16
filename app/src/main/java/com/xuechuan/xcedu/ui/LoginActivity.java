package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.T;
/**
 * @Title:  LoginActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 登陆页
 * @author: L-BackPacker
 * @date:   2018/4/16 12:02
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLoginLogin;
    private Button mBtnLoginForgetpaw;
    private Button mBtnLoginRegist;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnLoginLogin = (Button) findViewById(R.id.btn_login_login);
        mBtnLoginForgetpaw = (Button) findViewById(R.id.btn_login_forgetpaw);
        mBtnLoginRegist = (Button) findViewById(R.id.btn_login_regist);

        mBtnLoginLogin.setOnClickListener(this);
        mBtnLoginForgetpaw.setOnClickListener(this);
        mBtnLoginRegist.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:

                break;
            case R.id.btn_login_forgetpaw:

                break;
            case R.id.btn_login_regist:
                 Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                 startActivity(intent);
                break;
        }
    }

    private void submit() {
        String username = getTextStr(mEtLoginUsername);
        if (TextUtils.isEmpty(username)) {
            T.showToast(mContext, R.string.please_username);
            return;
        }
        String password = getTextStr(mEtLoginPassword);
        if (TextUtils.isEmpty(password)) {
            T.showToast(mContext, R.string.please_paw);
            return;
        }


    }
}
