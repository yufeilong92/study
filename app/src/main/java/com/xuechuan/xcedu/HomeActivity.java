package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.umeng.debug.log.D;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.presenter.RefreshTokenPresenter;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.TokenVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeActivity
 * @Package com.xuechuan.xcedu
 * @Description: 主页
 * @author: L-BackPacker
 * @date: 2018/4/17 8:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/17
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, RefreshTokenView {

    private FrameLayout mFlContent;
    private RadioButton mRdbHomeHome;
    private RadioButton mRdbHomeBank;
    private RadioButton mRdbHomeNet;
    private RadioButton mRdbHomePersonal;
    private ArrayList<Fragment> mFragmentLists;
    private RadioGroup mRgBtns;
    private FragmentManager mSfm;
    private Context mContext;
    /**
     * 填充的布局
     */
    private int mFragmentLayout = R.id.fl_content;
    private static String Params = "Params";
    private static String Params1 = "Params";
    private AlertDialog mDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(mContext);
    }

    public static void newInstance(Context context, String params1, String param2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Params, params1);
        intent.putExtra(Params1, param2);
        context.startActivity(intent);
    }

    //        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        initView();
//
//    }
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        initView();
        refreshToken();

    }

    /**
     * 刷新token
     */
    private void refreshToken() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = DbHelperAssist.getInstance().queryWithuuId(userId);
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfomDb.getVo() != null) {
            userInfom = userInfomDb.getVo();
        }
        MyAppliction.getInstance().setUserInfom(userInfom);
        RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
        presenter.refreshToken(mContext, userInfomDb.getToken());
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.login));

    }


    protected void initView() {
        mContext = this;
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mFlContent.setOnClickListener(this);
        mRdbHomeHome = (RadioButton) findViewById(R.id.rdb_home_home);
        mRdbHomeHome.setOnClickListener(this);
        mRdbHomeBank = (RadioButton) findViewById(R.id.rdb_home_bank);
        mRdbHomeBank.setOnClickListener(this);
        mRdbHomeNet = (RadioButton) findViewById(R.id.rdb_home_net);
        mRdbHomeNet.setOnClickListener(this);
        mRdbHomePersonal = (RadioButton) findViewById(R.id.rdb_home_personal);
        mRdbHomePersonal.setOnClickListener(this);
        mRgBtns = (RadioGroup) findViewById(R.id.rg_btns);
        mRgBtns.setOnClickListener(this);
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
            case R.id.rdb_home_home://首页
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 0);
                break;
            case R.id.rdb_home_bank://题库
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 1);
                break;
            case R.id.rdb_home_net://网课
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 2);
                break;
            case R.id.rdb_home_personal://个人
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 3);
                break;
        }
    }


    @Override
    public void TokenSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

        L.d(con);
        Gson gson = new Gson();
        TokenVo tokenVo = gson.fromJson(con, TokenVo.class);
        if (tokenVo.getStatus().getCode() == 200) {
            int statusX = tokenVo.getData().getStatusX();
            TokenVo.DataBean data = tokenVo.getData();
            switch (statusX) {
                case -1:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                case -2:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;
                case 1:
                    updataToken(data);
                    break;
                default:
            }
        } else {
            if (mDialog != null) {
                mDialog.dismiss();
                SaveUUidUtil.getInstance().delectUUid();
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                this.finish();
            }
            T.showToast(mContext, tokenVo.getStatus().getMessage());
        }
    }

    private void updataToken(TokenVo.DataBean data) {
        TokenVo.DataBean.TokenBean token = data.getToken();
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        UserBean user = userInfom.getData().getUser();
        user.setId(token.getStaffid());
        user.setToken(token.getSigntoken());
        user.setTokenexpire(token.getExpiretime());
        DbHelperAssist.getInstance().saveUserInfom(userInfom);
        initData();
    }

    @Override
    public void TokenError(String con) {

    }
}
