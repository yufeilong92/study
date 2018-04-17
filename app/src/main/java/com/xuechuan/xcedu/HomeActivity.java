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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.ui.AddressSelectActivity;
import com.xuechuan.xcedu.ui.SearchActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ProvincesVo;
import com.xuechuan.xcedu.weight.AddressTextView;

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
    private AddressTextView mTvAddress;
    private String mProvice;
    private String mCode;
    private Button mBtnSearch;
    private LocationClient mLocationClient;

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
        initData();
        initBaiduLocation();
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
        mTvAddress = (AddressTextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
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
            case R.id.btn_home_home://首页
                Utils.showSelectFragment(mSfm, mFragmentLists, mFragmentLayout, 0);
                break;
            case R.id.btn_home_bank://题库
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
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String adCode = location.getAdCode();
            L.e(province + adCode);
            mTvAddress.setText(province);
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            L.e(addr + "\n" + country + "\n" + province + "\n" + city + "\n" + district + "\n" + street + "\n");
        }
    };
}
