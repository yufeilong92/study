package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.RegisterService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.CountdownUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RegisterActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 注册界面
 * @author: L-BackPacker
 * @date: 2018/4/16 12:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String UUIONID = "uuionid";
    private CountdownUtil mTimeUtils;
    private Button mBtnSend;
    private EditText mEtRegisterPaw;
    private EditText mEtRegisterPaws;
    private EditText mEtRegisterPhone;
    private EditText mEtRegisterCode;
    private Context mContext;
    private Button mBtnRegisterLogin;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        initView();
//    }
    /**
     * 微信
     */
    private static String OPENID = "openid";
    private static String UNIONID = "unionid";
    /**
     * 请求类型
     */
    private static String HTTPTYPE = "httptype";
    private String mOpenid;
    private String mUuionid;
    private String mType;
    /**
     * 注册
     */
    public static String CEX_INT_TYPE_REG = "reg";
    /**
     * 找回密码
     */
    public static String CEX_INT_TYPE_PAW = "retrieve";
    /**
     * 绑定手机
     */
    public static String CEX_INT_TYPE_BIND = "bindreg";

    /**
     * @param context 上下文
     * @param openid  微信openid
     * @param unionid 平台标识
     */
    public static Intent newInstance(Context context, String type, String openid, String unionid) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(OPENID, openid);
        intent.putExtra(HTTPTYPE, type);
        intent.putExtra(UNIONID, unionid);
        return intent;
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        if (getIntent() != null) {
            mOpenid = getIntent().getStringExtra(OPENID);
            mUuionid = getIntent().getStringExtra(UUIONID);
            mType = getIntent().getStringExtra(HTTPTYPE);
        }
        initView();
        initData();
    }
   //处理请求
    private void initData() {
        if (!StringUtil.isEmpty(mType)) {
            if (mType.equals(CEX_INT_TYPE_PAW)) {//找回密码
                mBtnRegisterLogin.setText(R.string.reset_password);
            } else if (mType.equals(CEX_INT_TYPE_BIND)) {//绑定手机
                 mBtnRegisterLogin.setText(R.string.bingphone);
            } else if (mType.equals(CEX_INT_TYPE_REG)) {//注册
               mBtnRegisterLogin.setText(R.string.regist);
            }
        }
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
            case R.id.btn_send://发送验证码
                String phone = getTextStr(mEtRegisterPhone);
                if (!StringUtil.isEmpty(phone)) {
                    CountdownUtil.getInstance().startTime(mContext,mBtnSend);
                    mBtnSend.setEnabled(false);
                    sendRequestCodeHttp(phone);
                } else {
                    T.showToast(mContext, getString(R.string.please_input_phone));
                }
                break;

            case R.id.btn_register_login://登录
                submit();
                break;
        }
    }

    /**
     * 请求验证码
     */
    private void sendRequestCodeHttp(String phone) {
        RegisterService service = RegisterService.getInstance(mContext);
        service.setIsShowDialog(false);
        service.setDialogContext(mContext, null, getString(R.string.get_code));
        service.requestRegisterCode(phone, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                L.d(response.body().toString());
                T.showToast(mContext, "验证码已发送");
            }

            @Override
            public void onError(Response<String> response) {
                T.showToast(mContext, response.message());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        CountdownUtil.getInstance().stop();
    }

    private void submit() {
        String phone = getTextStr(mEtRegisterPhone);
        if (TextUtils.isEmpty(phone)) {
            T.showToast(mContext, getString(R.string.please_input_phone));
            return;
        }
        final String code = getTextStr(mEtRegisterCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, getString(R.string.please_input_code));
            return;
        }
        String paw = getTextStr(mEtRegisterPaw);
        if (TextUtils.isEmpty(paw)) {
            T.showToast(mContext, getString(R.string.please_input_pass));
            return;
        }
        String paws = getTextStr(mEtRegisterPaws);
        if (TextUtils.isEmpty(paws)) {
            T.showToast(mContext, getString(R.string.please_input_pass));
            return;
        }
        if (!paw.equals(paws)) {
            T.showToast(mContext, "密码错误,请重新输入");
            return;
        }
        RegisterService service = RegisterService.getInstance(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext(null, getString(R.string.login_loading));
        service.requestRegister(mType,phone, code, paw, mOpenid, mUuionid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                Gson gson = new Gson();
                UserInfomVo vo = gson.fromJson(message, UserInfomVo.class);
                int code1 = vo.getStatus().getCode();
                if (code1 == 200) {//注册成功
                    T.showToast(mContext, getString(R.string.registerOK));
                    MyAppliction.getInstance().setUserInfom(vo);
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {//失败
                    String message1 = vo.getStatus().getMessage();
                    T.showToast(mContext, message1);
                }
            }

            @Override
            public void onError(Response<String> response) {

            }
        });
    }

}
