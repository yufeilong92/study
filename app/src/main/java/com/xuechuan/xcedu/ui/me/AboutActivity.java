package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.Utils;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AboutActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 关于app
 * @author: L-BackPacker
 * @date: 2018/5/22 8:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class AboutActivity extends BaseActivity {

    private ImageView mIvLogo;
    private TextView mTvAboutappCode;
    private TextView mTvAbout;
    private TextView mTvAboutappServiceAgreement;
    private Context mContext;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        initView();
        initData();
    }

    private void initData() {
        int versionCode = Utils.getVersionCode(mContext);
        mTvAboutappCode.setText(versionCode + "");

    }

    private void initView() {
        mContext = this;
        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        mTvAboutappCode = (TextView) findViewById(R.id.tv_aboutapp_code);
        mTvAbout = (TextView) findViewById(R.id.tv_about);
        mTvAboutappServiceAgreement = (TextView) findViewById(R.id.tv_aboutapp_service_agreement);
    }

}
