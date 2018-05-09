package com.xuechuan.xcedu.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.mvp.model.LoginModelImpl;
import com.xuechuan.xcedu.mvp.presenter.LoginPresenter;
import com.xuechuan.xcedu.mvp.view.LoginView;
import com.xuechuan.xcedu.net.LoginService;
import com.xuechuan.xcedu.net.WeiXinLoginSercvice;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: LoginActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 登陆页
 * @author: L-BackPacker
 * @date: 2018/4/16 12:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/16
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLoginLogin;
    private TextView mTvLoginForgetpaw;
    private TextView mTvLoginRegist;
    private Context mContext;
    private ImageView mIvWeixinlogin;
    private IWXAPI api;
    private BroadcastReceiver receiver;
    private CheckBox mChbLoginEyable;
    private LoginPresenter mPresenter;
    private AlertDialog mDialog;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        initView();
        regToWx();
        String userId = SaveUUidUtil.getInstance().getUserId();
        if (StringUtil.isEmpty(userId)) {
            initData();
        } else {
            HomeActivity.newInstance(mContext, null, null);
            finishActivity();
        }

    }

    //注册微信
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID, true);
        api.registerApp(DataMessageVo.APP_ID);
    }

    private void initView() {
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnLoginLogin = (Button) findViewById(R.id.btn_login_login);
        mTvLoginForgetpaw = (TextView) findViewById(R.id.tv_login_forgetpaw);
        mTvLoginRegist = (TextView) findViewById(R.id.tv_login_regist);
        mBtnLoginLogin.setOnClickListener(this);
        mTvLoginForgetpaw.setOnClickListener(this);
        mTvLoginRegist.setOnClickListener(this);
        mContext = this;
        mIvWeixinlogin = (ImageView) findViewById(R.id.iv_weixinlogin);
        mIvWeixinlogin.setOnClickListener(this);
        mChbLoginEyable = (CheckBox) findViewById(R.id.chb_login_eyable);
        mChbLoginEyable.setOnClickListener(this);
        Utils.showPassWord(mChbLoginEyable, mEtLoginPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login://登录
                Utils.hideInputMethod(LoginActivity.this);
                String phone = getTextStr(mEtLoginUsername);
                boolean phoneNum = Utils.isPhoneNum(phone);
                L.w(phone + phoneNum);
                if (!Utils.isPhoneNum(phone)) {
                    T.showToast(mContext, getStringWithId(R.string.please_right_phone));
                    return;
                }
                submit();
                break;
            case R.id.tv_login_forgetpaw://忘记密码
                Intent intent1 = RegisterActivity.newInstance(mContext, RegisterActivity.CEX_INT_TYPE_PAW, null, null);
                intent1.putExtra(RegisterActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.forget_password));
                startActivity(intent1);
                break;
            case R.id.tv_login_regist://手机注册
                Intent intent2 = RegisterActivity.newInstance(mContext, RegisterActivity.CEX_INT_TYPE_REG, null, null);
                startActivity(intent2);
                break;
            case R.id.iv_weixinlogin://微信登录
                loginWeiXin();
                break;

        }
    }

    /**
     * 处理微信登录回调
     */
    private void initData() {
        mPresenter = new LoginPresenter(new LoginModelImpl(), this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataMessageVo.WEI_LOGIN_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {

                    String extra = intent.getStringExtra(DataMessageVo.WEISTATE);
                    String code = intent.getStringExtra(DataMessageVo.WEICODE);
//                    requestLogin(extra, code);
                    mPresenter.getWeiXinLoginContent(mContext, code);
                }
            }
        };
        registerReceiver(receiver, filter);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mBtnLoginLogin.setEnabled(false);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_point);
                    return;
                }
                String trim = mEtLoginPassword.getText().toString().trim();
                if (!StringUtil.isEmpty(trim)) {
                    mBtnLoginLogin.setEnabled(true);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_normal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mBtnLoginLogin.setEnabled(false);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_point);
                    return;
                }
                String trim = mEtLoginUsername.getText().toString().trim();
                if (!StringUtil.isEmpty(trim)) {
                    mBtnLoginLogin.setEnabled(true);
                    mBtnLoginLogin.setBackgroundResource(R.drawable.btn_login_bg_normal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    //调用微信
    private void loginWeiXin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    private void submit() {
        String username = getTextStr(mEtLoginUsername);
        if (TextUtils.isEmpty(username)) {
            T.showToast(mContext, R.string.please_input_phone);
            return;
        }
        String password = getTextStr(mEtLoginPassword);
        if (password.length() < 6) {
            T.showToast(mContext, getStringWithId(R.string.passundersixt));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            T.showToast(mContext, R.string.please_paw);
            return;
        }
        final LoginService service = LoginService.getInstance(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext(null, getStringWithId(R.string.login_loading));
        mPresenter.getLoginContent(mContext, username, password);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.login_loading));
    }

    @Override
    public void WeiXinLoginSuccess(String infom) {
        JsonElement parse = new JsonParser().parse(infom);

        L.d(infom);
        Gson gson = new Gson();
        UserInfomVo vo = gson.fromJson(infom, UserInfomVo.class);
        UserInfomVo.DataBean voData = vo.getData();
        if (vo.getStatus().getCode() != 200) {//失败情况
            T.showToast(mContext, vo.getStatus().getMessage());
            return;
        }
        if (voData.isIsbinduser()) {//已经绑定数据（手机）
            //保存信息
            DbHelperAssist.getInstance().saveUserInfom(vo);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finishActivity();
        } else {//没有绑定手机
            Intent intent = RegisterActivity.newInstance(mContext, voData.getOpenid(), RegisterActivity.CEX_INT_TYPE_BIND, voData.getUnionid());
            intent.putExtra(RegisterActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.bingphone));
            startActivity(intent);
        }
    }

    @Override
    public void WeiXinLoginError(String error) {
        T.showToast(mContext, error);
    }

    @Override
    public void LoginSuccess(String message) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.d("登录成功", message);
        String code = JSONObject.quote(message);
        L.w(code);
        Gson gson = new Gson();
        UserInfomVo vo = gson.fromJson(message, UserInfomVo.class);
        if (vo.getStatus().getCode() == 200) {
            UserInfomVo.DataBean voData = vo.getData();
            int status = voData.getStatus();
            if (status == -1) {
                T.showToast(mContext, "账号被禁用");
                return;
            }
            if (status == -2) {
                T.showToast(mContext, "账号密码错误");
                return;
            }
            if (status == -3) {
                T.showToast(mContext, "账号未注册");
                return;
            }
            DbHelperAssist.getInstance().saveUserInfom(vo);
            HomeActivity.newInstance(mContext, null, null);
            finishActivity();
        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void LoginError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.e(con);
    }
    public void finishActivity(){
        this.finish();
    }

}
