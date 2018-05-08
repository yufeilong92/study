package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.SpecalTabModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SpecalTabPresenter;
import com.xuechuan.xcedu.mvp.view.SpecalTabView;
import com.xuechuan.xcedu.utils.L;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecalTabActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 规范页
 * @author: L-BackPacker
 * @date: 2018/5/8 16:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/8
 */

public class SpecalTabActivity extends BaseActivity implements SpecalTabView{

    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPageContent;
    private SpecalTabPresenter tabPresenter;
    private Context mContext;
    /**
     * 省份
     */
    private static String PROVICECODE = "provicecode";
    /**
     * @param context
     * @param proviceCode 省份
     */
    public static Intent newInstance(Context context, String proviceCode) {
        Intent intent = new Intent(context, AdvisoryListActivity.class);
        intent.putExtra(PROVICECODE, proviceCode);
        return intent;
    }
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specal_tab);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initView();
        initData();

    }

    private void initData() {
        tabPresenter = new SpecalTabPresenter(this,new SpecalTabModelImpl());
        tabPresenter.reqeustTagList(mContext);
    }

    private void initIndicator() {
//
//        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setScrollPivotX(0.25f);
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return mDataList == null ? 0 : mDataList.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int index) {
//                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
//                simplePagerTitleView.setText(mDataList.get(index));
//                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
//                simplePagerTitleView.setSelectedColor(Color.WHITE);
//                simplePagerTitleView.setTextSize(12);
//                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mViewPageContent.setCurrentItem(index);
//                    }
//                });
//                return simplePagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setYOffset(UIUtil.dip2px(context, 3));
//                indicator.setColors(Color.parseColor("#ffffff"));
//                return indicator;
//            }
//        });
//        mMagicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(mMagicIndicator, mViewPageContent);
    }

    private void initView() {
        mContext = this;
        mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mViewPageContent = (ViewPager) findViewById(R.id.view_page_content);
    }

    @Override
    public void SpecalTagSuccess(String con) {
        L.d("SpecalTagSuccess"+con);

    }

    @Override
    public void SpecalTagError(String con) {
        L.d("SpecalTagError"+con);
    }

    @Override
    public void SpecalTagConSuccess(String con) {
        L.d("SpecalTagConSuccess"+con);
    }

    @Override
    public void SpecalTagConError(String con) {
        L.d("SpecalTagConError"+con);
    }
}
