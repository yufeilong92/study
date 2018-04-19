package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.HomeAllAdapter;
import com.xuechuan.xcedu.adapter.HomeContentAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomActivity;
import com.xuechuan.xcedu.ui.home.AddressSelectActivity;
import com.xuechuan.xcedu.ui.home.BookActivity;
import com.xuechuan.xcedu.ui.home.SearchActivity;
import com.xuechuan.xcedu.ui.home.SpecasActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.AdvisoryBean;
import com.xuechuan.xcedu.vo.ArticleBean;
import com.xuechuan.xcedu.vo.BannerBean;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.xuechuan.xcedu.weight.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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
    private TextView mTvSearch;
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
    /**
     * 地址
     */
    private String provice;
    private RelativeLayout mRlHomeBook;
    private RelativeLayout mRlHomeStandard;
    private android.support.v7.app.AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLocationClient != null) {
            mLocationClient.restart();
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, null);
//        initView(view);
//        return view;
//    }

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
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initBaiduLocation();
    }

    @Override
    protected void initViewCreate(View view, Bundle savedInstanceState) {
        initData();
    }

    /**
     * 初始百度
     */
    private void initBaiduLocation() {
        mDialog = DialogUtil.showDialog(mContext, null, getStrWithId(R.string.loading));
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


    /**
     * 请求资讯
     *
     * @param code
     */
    private void requestData(final String code) {
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
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                    HomePageVo.DataBean data = homePageVo.getData();
                    List<AdvisoryBean> advisory = data.getAdvisory();
                    List<ArticleBean> article = data.getArticle();
                    List<BannerBean> banner = data.getBanner();
                    bindInfomData(advisory);
                    bindAllData(article);
                    bindBanner(banner);


                } else {//失败
//                    T.showToast(mContext, status.getMessage());
                    mDialog.dismiss();
                    requestData(code);
                }
            }

            @Override
            public void onError(Response<String> response) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                L.e("错误", response.message());
            }
        });

    }

    /**
     * banne图片
     *
     * @param banner
     */
    private void bindBanner(List<BannerBean> banner) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {
            list.add(banner.get(i).getImageurl());
        }
        ArrayList<String> list1 = DataMessageVo.getImageList1();
        bindData(list1);
    }

    /**
     * 资讯
     *
     * @param article
     */
    private void bindAllData(List<ArticleBean> article) {
        HomeAllAdapter allAdapter = new HomeAllAdapter(mContext, article);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvRecommendAll.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerviewline));
        mRlvRecommendAll.setLayoutManager(manager);
        mRlvRecommendAll.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeAllAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                T.showToast(mContext, position + "");
                ArticleBean vo = (ArticleBean) obj;
                InfomActivity.newInstance(mContext, vo.getGourl());
            }
        });

    }

    /**
     * 文章
     *
     * @param advisory
     */
    private void bindInfomData(List<AdvisoryBean> advisory) {
        HomeContentAdapter allAdapter = new HomeContentAdapter(mContext, advisory);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvRecommendContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerviewline));
        mRlvRecommendContent.setLayoutManager(manager);
        mRlvRecommendContent.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeContentAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                AdvisoryBean vo = (AdvisoryBean) obj;
                T.showToast(mContext, position + "");
                InfomActivity.newInstance(mContext, vo.getGourl());
            }
        });

    }

    private void initView(View view) {
        mBanHome = view.findViewById(R.id.ban_home);
        mContext = getActivity();
        mTvAddress = (AddressTextView) view.findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mTvSearch = (TextView) view.findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(this);
        mIvOption = (ImageView) view.findViewById(R.id.iv_option);
        mIvOption.setOnClickListener(this);
        mIvLiftOption = (ImageView) view.findViewById(R.id.iv_lift_option);
        mIvLiftOption.setOnClickListener(this);
        mRlvRecommendContent = (RecyclerView) view.findViewById(R.id.rlv_recommend_content);
        mRlvRecommendContent.setOnClickListener(this);
        mRlvRecommendAll = (RecyclerView) view.findViewById(R.id.rlv_recommend_all);
        mRlvRecommendAll.setOnClickListener(this);
        mRlHomeBook = (RelativeLayout) view.findViewById(R.id.rl_home_book);
        mRlHomeBook.setOnClickListener(this);
        mRlHomeStandard = (RelativeLayout) view.findViewById(R.id.rl_home_standard);
        mRlHomeStandard.setOnClickListener(this);
    }


    private void initData() {
//        bindData(list);
    }

    private void bindData(ArrayList<String> list) {
        mBanHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        mBanHome.setDelayTime(2000);
        mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                MyAppliction.getInstance().displayImages(imageView, (String) path, false);
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
            String code = PushXmlUtil.getInstance().getLocationCode(mContext, province);
            if (!StringUtil.isEmpty(code))
                requestData(code);
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
            case R.id.tv_search://搜索
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.rl_home_book://教材
                Log.e("yfl", "onClick: " );
                BookActivity.newInstance(mContext, null, null);
                break;
            case R.id.rl_home_standard://规范
                SpecasActivity.newInstance(mContext, null, null);
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
                L.d("地址数据回调", provice);
                mTvAddress.setText(provice);
                String code = PushXmlUtil.getInstance().getLocationCode(mContext, provice);
                if (!StringUtil.isEmpty(code))
                    requestData(code);
            }
        }
    }
}
