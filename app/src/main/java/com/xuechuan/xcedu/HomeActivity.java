package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.ui.AddressSelectActivity;
import com.xuechuan.xcedu.utils.Utils;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mFlContent;
    private Button mBtnHomeHome;
    private Button mBtnHomeBank;
    private Button mBtnHomeNet;
    private Button mBtnHomePersonal;
    private ArrayList<Fragment> mFragmentLists;
    private LinearLayout mLlBtns;
    private FragmentManager mSfm;
    private Context mContext;
    /**
     * 填充的布局
     */
    private int mFragmentLayout = R.id.fl_content;
    /**
     * 请求结果码
     */
    private int REQUESTCODE = 1001;
    /**
     * 请求回调码
     */
    public static int REQUESTRESULT = 1002;
    /**
     * 省份
     */
    public static String STR_INT_PROVINCE = "province";
    /**
     * code码
     */
    public static String STR_INT_CODE = "code";
    /**
     * 位标
     */
    public static String STR_INT_POSITION = "position";
    private TextView mTvAddress;
    private String mProvice;
    private String mCode;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        initView();
//
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        mProvice = "河南省";
        initView();
        initData();

    }

    protected void initView() {
        mContext = this;
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mFlContent.setOnClickListener(this);
        mBtnHomeHome = (Button) findViewById(R.id.btn_home_home);
        mBtnHomeHome.setOnClickListener(this);
        mBtnHomeBank = (Button) findViewById(R.id.btn_home_bank);
        mBtnHomeBank.setOnClickListener(this);
        mBtnHomeNet = (Button) findViewById(R.id.btn_home_net);
        mBtnHomeNet.setOnClickListener(this);
        mBtnHomePersonal = (Button) findViewById(R.id.btn_home_personal);
        mBtnHomePersonal.setOnClickListener(this);
        mLlBtns = (LinearLayout) findViewById(R.id.ll_btns);
        mLlBtns.setOnClickListener(this);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
    }

    protected void initData() {
        addFragmentData();

    }

    private void addFragmentData() {
        mFragmentLists = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        BankFragment bankFragment = new BankFragment();
        NetFragment netFragment = new NetFragment();
        PersionalFragment persionalFragment = new PersionalFragment();
        mFragmentLists.add(homeFragment);
        mFragmentLists.add(bankFragment);
        mFragmentLists.add(netFragment);
        mFragmentLists.add(persionalFragment);
        mSfm = getSupportFragmentManager();
        for (int i = 0; i < mFragmentLists.size(); i++) {
            FragmentTransaction transaction = mSfm.beginTransaction();
            Fragment fragment = mFragmentLists.get(i);
            transaction.add(mFragmentLayout, fragment).hide(fragment).commit();
        }
        FragmentTransaction transaction = mSfm.beginTransaction();
        transaction.show(homeFragment).commit();
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_home_home:
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 0);
                break;
            case R.id.btn_home_bank://首页
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 1);
                break;
            case R.id.btn_home_net://网课
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 2);
                break;
            case R.id.btn_home_personal://个人
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 3);
                break;
            case R.id.tv_address://地址
                Intent intent = AddressSelectActivity.newInstance(mContext, mProvice);
                startActivityForResult(intent, REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode ==REQUESTRESULT) {
            if (data != null) {
                mProvice = data.getStringExtra(STR_INT_PROVINCE);
                mCode = data.getStringExtra(STR_INT_CODE);
                mTvAddress.setText(mProvice);

            }
        }
    }
}
