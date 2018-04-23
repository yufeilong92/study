package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.utils.Utils;

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
public class HomeActivity extends BaseActivity implements View.OnClickListener {

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
        initData();
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
        selectTabBg(true,false,false,false);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.rdb_home_home://首页
                selectTabBg(true,false,false,false);
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 0);
                break;
            case R.id.rdb_home_bank://题库
                selectTabBg(false,true,false,false);
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 1);
                break;
            case R.id.rdb_home_net://网课
                selectTabBg(false,false,true,false);
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 2);
                break;
            case R.id.rdb_home_personal://个人
                selectTabBg(false,false,false,true);
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 3);
                break;
        }
    }

    private void selectTabBg(boolean home, boolean book, boolean net, boolean user) {
        Drawable homeDrawable;
        if (home) {
            homeDrawable = getResources().getDrawable(R.drawable.common_tab_home_s);
        } else {
            homeDrawable = getResources().getDrawable(R.drawable.common_tab_home_n);
        }
        homeDrawable.setBounds(0, 0,  homeDrawable.getMinimumWidth(), homeDrawable.getMinimumHeight());
        mRdbHomeHome.setCompoundDrawables(null, homeDrawable, null, null);
        Drawable banDrawable;
        if (book) {
            banDrawable = getResources().getDrawable(R.drawable.common_tab_questionbank_s);
        } else {
            banDrawable = getResources().getDrawable(R.drawable.common_tab_questionbank_n);
        }
        banDrawable.setBounds(0, 0,  banDrawable.getMinimumWidth(), banDrawable.getMinimumHeight());
        mRdbHomeBank.setCompoundDrawables(null, banDrawable, null, null);
        Drawable netDrawable;
        if (net) {
            netDrawable = getResources().getDrawable(R.drawable.common_tab_course_s);
        } else {
            netDrawable = getResources().getDrawable(R.drawable.common_tab_course_n);
        }
        netDrawable.setBounds(0, 0, netDrawable.getMinimumWidth(), netDrawable.getMinimumHeight());
        mRdbHomeNet.setCompoundDrawables(null, netDrawable, null, null);
        Drawable userDrawable;
        if (user) {
            userDrawable = getResources().getDrawable(R.drawable.common_tab_mine_s);
        } else {
            userDrawable = getResources().getDrawable(R.drawable.common_tab_mine_n);
        }
        userDrawable.setBounds(0, 0, userDrawable.getMinimumWidth(), userDrawable.getMinimumHeight());

        mRdbHomePersonal.setCompoundDrawables(null, userDrawable, null, null);
    }
}
