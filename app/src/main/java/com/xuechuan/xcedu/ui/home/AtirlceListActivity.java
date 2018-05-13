package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.andview.refreshview.callback.IFooterCallBack;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.MyTagIndicatorAdapter;
import com.xuechuan.xcedu.adapter.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.TagInfomFragment;
import com.xuechuan.xcedu.mvp.model.ArticleTagModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ArticleTagPresenter;
import com.xuechuan.xcedu.mvp.view.ArticleTagView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.ArtilceTagVo;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtirlceListActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 文章tag 列表
 * @author: L-BackPacker
 * @date: 2018/5/11 20:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/11
 */
public class AtirlceListActivity extends BaseActivity implements ArticleTagView {

    private ViewPager mViewpage;
    private ArticleTagPresenter mPresenter;
    private static String ATIRLCETAG = "atirlcetag";
    private Context mContext;
    private AlertDialog mDialog;
    private MagicIndicator magicIndicator;
    private List<Fragment> fragmentList;

    public static Intent newInstance(Context context, String atirlcetag) {
        Intent intent = new Intent(context, AtirlceListActivity.class);
        intent.putExtra(ATIRLCETAG, atirlcetag);
        return intent;
    }


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atirlce_list);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_atirlce_list);
        initView();
        initData();
//        initIndicator();
    }

    private void initData() {
        mPresenter = new ArticleTagPresenter(new ArticleTagModelImpl(), this);
        mPresenter.reqeustTagList(this);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
    }

    private void initIndicator(List<ArtilceTagVo.DatasBean> list) {
        magicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdjustMode(true);
        MyTagIndicatorAdapter adapter = new MyTagIndicatorAdapter(list, mViewpage);
        magicIndicator.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        List<Fragment> fragments = creartFragment(list);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpage.setAdapter(tagPagerAdapter);
        mViewpage.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(magicIndicator, mViewpage);
    }

    private List<Fragment> creartFragment(List<ArtilceTagVo.DatasBean> list) {
        if (list.size() < 2) {
            magicIndicator.setVisibility(View.GONE);
        }
        if (fragmentList == null)
            fragmentList = new ArrayList<>();
        else
            fragmentList.clear();
        for (int i = 0; i < list.size(); i++) {
            ArtilceTagVo.DatasBean bean = list.get(i);
            TagInfomFragment fragment = TagInfomFragment.newInstance(String.valueOf(bean.getId()));
            fragmentList.add(fragment);
        }
        return fragmentList;
    }

    private void initView() {
        mContext = this;
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mViewpage = (ViewPager) findViewById(R.id.viewpage);
    }

    @Override
    public void ArticleTagSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        ArtilceTagVo vo = gson.fromJson(con, ArtilceTagVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ArtilceTagVo.DatasBean> datas = vo.getDatas();
            initIndicator(datas);
        } else {
            L.e(vo.getStatus().getMessage());

        }
        L.d("tagArticleTagSuccess" + con);
    }

    @Override
    public void ArticleTagError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.d("tagArticleTagError" + con);
    }


}
