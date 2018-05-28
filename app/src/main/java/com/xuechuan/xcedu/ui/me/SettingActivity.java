package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.contract.SettingViewContract;
import com.xuechuan.xcedu.mvp.model.SettingViewModel;
import com.xuechuan.xcedu.mvp.presenter.SettingViewPresenter;
import com.xuechuan.xcedu.net.MeService;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.ui.RegisterActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AppUpDataVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SettingActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 设置界面
 * @author: L-BackPacker
 * @date: 2018/5/22 9:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, SettingViewContract.View {

    private TextView mTvMSettingWeixin;
    private LinearLayout mLlMSettingBindWei;
    private TextView mTvMSettingPaw;
    private TextView mTvMSettingCode;
    private LinearLayout mLlMSettingUpdata;
    private TextView mTvMSettingAbout;
    private Button mBtnBSOut;
    private Context mContext;
    private SettingViewPresenter mPresenter;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    private void initData() {
        int versionCode = Utils.getVersionCode(mContext);
        mTvMSettingCode.setText(versionCode + "");
        mPresenter = new SettingViewPresenter();
        mPresenter.initModelView(new SettingViewModel(), this);
    }

    private void initView() {
        mContext = this;
        mTvMSettingWeixin = (TextView) findViewById(R.id.tv_m_setting_weixin);
        mLlMSettingBindWei = (LinearLayout) findViewById(R.id.ll_m_setting_bindWei);
        mLlMSettingBindWei.setOnClickListener(this);
        mTvMSettingPaw = (TextView) findViewById(R.id.tv_m_setting_paw);
        mTvMSettingPaw.setOnClickListener(this);
        mTvMSettingCode = (TextView) findViewById(R.id.tv_m_setting_code);
        mLlMSettingUpdata = (LinearLayout) findViewById(R.id.ll_m_setting_updata);
        mLlMSettingUpdata.setOnClickListener(this);
        mTvMSettingAbout = (TextView) findViewById(R.id.tv_m_setting_about);
        mTvMSettingAbout.setOnClickListener(this);
        mBtnBSOut = (Button) findViewById(R.id.btn_b_s_out);
        mBtnBSOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_m_setting_about://关于
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                intent.putExtra(AddVauleActivity.CSTR_EXTRA_TITLE_STR, getStringWithId(R.string.about));
                startActivity(intent);
                break;
            case R.id.ll_m_setting_updata://更新
                mPresenter.requestAppCode(mContext);
                break;
            case R.id.tv_m_setting_paw://修改密码
                startActivity(new Intent(SettingActivity.this, PawChangerActivity.class));
                break;
            case R.id.ll_m_setting_bindWei://绑定微信

                break;
            case R.id.btn_b_s_out:
                MyAppliction.getInstance().setUserInfom(null);
                SaveUUidUtil.getInstance().delectUUid();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                this.finish();
                break;
        }
    }

    @Override
    public void AppCodeSuccess(String cont) {
        L.d("======请求app====" + cont);
        Gson gson = new Gson();
        AppUpDataVo vo = gson.fromJson(cont, AppUpDataVo.class);
        if (vo.getStatus().getCode() == 200) {
            AppUpDataVo.DataBean data = vo.getData();
        } else {
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void AppCodeError(String msg) {
        L.d("======请求错误app====" + msg);
        L.e(msg);
    }
}
