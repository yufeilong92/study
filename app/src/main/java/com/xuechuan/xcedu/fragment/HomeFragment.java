package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.AddressSelectActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.vo.ProvinceEvent;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 首页展示
 * @author: L-BackPacker
 * @date: 2018/4/11 17:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Banner mBanHome;
    private Context mContext;
    private LocationClient mLocationClient;
    private AddressTextView mTvAddress;
    private TextView mBtnSearch;
    private ImageView mIvOption;
    private ImageView mIvLiftOption;
    private RecyclerView mRlvRecommendContent;
    private RecyclerView mRlvRecommendAll;
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
    private String provice;
    private String code;
    private String position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLocationClient != null) {
            mLocationClient.restart();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public HomeFragment() {
    }

    /**
     * @param param1
     * @param param2
     * @return
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initCreateView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initBaiduLocation();
    }

    @Override
    protected void initViewCreate(View view, Bundle savedInstanceState) {
        initView(view);
        initData();

    }

    /**
     * 初始百度
     */
    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getActivity());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProvinceEvent event) {
        String province = event.getProvince();
        String code = PushXmlUtil.getInstance().getLocationCode(mContext, province);
        requestData(code);
    }

    /**
     * 请求资讯
     *
     * @param code
     */
    private void requestData(String code) {
        HomeService service = HomeService.getInstance(mContext);
        service.requestHomePager(code, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.d("主界面数据", message);
                Gson gson = new Gson();
                HomePageVo homePageVo = gson.fromJson(message, HomePageVo.class);
                BaseVo.StatusBean status = homePageVo.getStatus();
                if (status.getCode() == 200) {//成功
                    HomePageVo.DataBean data = homePageVo.getData();

                }


            }

            @Override
            public void onError(Response<String> response) {
                L.e("错误", response.message());
            }
        });

    }

    private void initView(View view) {
        mBanHome = view.findViewById(R.id.ban_home);
        mContext = getActivity();
        mTvAddress = (AddressTextView) view.findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mBtnSearch = (TextView) view.findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
        mIvOption = (ImageView) view.findViewById(R.id.iv_option);
        mIvOption.setOnClickListener(this);
        mIvLiftOption = (ImageView) view.findViewById(R.id.iv_lift_option);
        mIvLiftOption.setOnClickListener(this);
        mRlvRecommendContent = (RecyclerView) view.findViewById(R.id.rlv_recommend_content);
        mRlvRecommendContent.setOnClickListener(this);
        mRlvRecommendAll = (RecyclerView) view.findViewById(R.id.rlv_recommend_all);
        mRlvRecommendAll.setOnClickListener(this);
    }


    private void initData() {
        ArrayList<String> list = DataMessageVo.getImageList1();
        mBanHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        mBanHome.setDelayTime(2000);
        mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                MyAppliction.displayImages(imageView, (String) path, false);
            }
        });
        mBanHome.setImages(list);
        mBanHome.start();
        mBanHome.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                T.showToast(mContext, position + "");
            }
        });

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
            L.d("定位位置", province);
            EventBus.getDefault().post(new ProvinceEvent(province));
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address://定位
                String provice = getTextStr(mTvAddress);
                Intent intent = AddressSelectActivity.newInstance(mContext, provice);
                intent.putExtra(AddressSelectActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
                break;

            default:

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == REQUESTRESULT) {
            if (data != null) {
                provice = data.getStringExtra(STR_INT_PROVINCE);
//                code = data.getStringExtra(STR_INT_CODE);
//                position = data.getStringExtra(STR_INT_POSITION);
                mTvAddress.setText(provice);
            }
        }
    }
}
