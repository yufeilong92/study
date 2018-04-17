package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

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
public class HomeFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Banner mBanHome;
    private Context mContext;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
//        View view = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initCreateView(Bundle savedInstanceState) {
    }

    @Override
    protected void initViewCreate(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initView(View view) {
        mBanHome = view.findViewById(R.id.ban_home);
        mContext = getActivity();
    }

    private void initData() {
        ArrayList<String> list = DataMessageVo.getImageList1();
        mBanHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        mBanHome.setDelayTime(2000);
        mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                L.e((String) path+"");
                MyAppliction.displayImages(imageView, (String) path,false);
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


}
