package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.ui.AddressSelectActivity;
import com.xuechuan.xcedu.ui.SearchActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ProvinceEvent;
import com.xuechuan.xcedu.weight.AddressTextView;

import org.greenrobot.eventbus.EventBus;

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
    private AddressTextView mTvAddress;
    private String mProvice;
    private String mCode;
    private TextView mTvSearch;
    private LocationClient mLocationClient;
    private static HomeActivity service;

    private static String Params = "Params";
    private static String Params1 = "Params";

    public static void newInstance(Context context, String params1, String param2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Params, params1);
        intent.putExtra(Params1, param2);
        context.startActivity(intent);
    }

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
        initView();
        initBaiduLocation();
        initData();
    }

    /**
     * 初始百度
     */
    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.start();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mLocationClient != null) {
            mLocationClient.restart();
        }
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
        mLlBtns = (LinearLayout) findViewById(R.id.ll_btns);
        mLlBtns.setOnClickListener(this);
        mTvAddress = (AddressTextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mTvSearch = (TextView) findViewById(R.id.btn_search);
        mTvSearch.setOnClickListener(this);
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
            case R.id.tv_address://地址
                Intent intent = AddressSelectActivity.newInstance(mContext, mProvice);
                intent.putExtra(AddressSelectActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.btn_search://搜素
                Intent searchIntent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (StringUtil.isEmpty(location.getProvince())) {
                mLocationClient.restart();
                return;
            }
            String province = location.getProvince();    //获取省份
            mTvAddress.setText(province);
            L.d("定位位置",province);
            EventBus.getDefault().post(new ProvinceEvent(province));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
