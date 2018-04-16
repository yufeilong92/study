package com.xuechuan.xcedu.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.WeiXinLoginSercvice;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.wxapi.WXEntryActivity;

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
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLoginLogin;
    private Button mBtnLoginForgetpaw;
    private Button mBtnLoginRegist;
    private Context mContext;
    private ImageView mIvWeixinlogin;

    private IWXAPI api;
    private BroadcastReceiver receiver;
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        initView();
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        initView();
        regToWx();
        initData();
    }


    private void regToWx() {
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID, true);
        api.registerApp(DataMessageVo.APP_ID);
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
        mIvWeixinlogin = (ImageView) findViewById(R.id.iv_weixinlogin);
        mIvWeixinlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                break;
            case R.id.btn_login_forgetpaw:
                break;
            case R.id.btn_login_regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_weixinlogin:
                loginWeiXin();
                break;
        }
    }

    private void initData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataMessageVo.WEI_LOGIN_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String extra = intent.getStringExtra(DataMessageVo.WEISTATE);
                    String code = intent.getStringExtra(DataMessageVo.WEICODE);
                    L.d(code + "//" + extra);
                    requestLogin(extra, code);
                }
            }
        };
        registerReceiver(receiver, filter);

    }

    private void requestLogin(String extra, String code) {
        WeiXinLoginSercvice instance = WeiXinLoginSercvice.getInstance();
        instance.requestWeiCode(mContext, code, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
               L.e(response.code()+"");
                L.e(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                L.e(response.message());
            }
        });

    }

    private void loginWeiXin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
