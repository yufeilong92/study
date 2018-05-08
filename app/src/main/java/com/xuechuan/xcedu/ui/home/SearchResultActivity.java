package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.umeng.debug.log.D;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.HomeReasultViewPagAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.ResultAtirleFragment;
import com.xuechuan.xcedu.fragment.ResultQuestionFragment;
import com.xuechuan.xcedu.utils.ArrayToListUtil;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SearchResultActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 搜索结果
 * @author: L-BackPacker
 * @date: 2018/5/8 18:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/8
 */
public class SearchResultActivity extends BaseActivity {

    private TabLayout mTabTitleContent;
    private ViewPager mVpSearchReasult;


    /**
     * 搜索
     */
    private static String SEARCHKEY = "key";
    private String mSearchKey;
    private Context mContext;

    public static Intent newInsanter(Context context, String content) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCHKEY, content);
        return intent;
    }
    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_search_result);
           initView();
       }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_result);
        if (getIntent() != null) {
            mSearchKey = getIntent().getStringExtra(SEARCHKEY);
        }
        initView();
        initData();
    }

    private void initData() {

        ArrayList<String> list = ArrayToListUtil.arraytoList(mContext, R.array.search_reasult);
        ArrayList<Fragment> fragments = new ArrayList<>();
        ResultAtirleFragment fragment = ResultAtirleFragment.newInstance(mSearchKey, DataMessageVo.ARTICLE);
        ResultQuestionFragment questionFragment = ResultQuestionFragment.newInstance(mSearchKey, DataMessageVo.QUESTION);
        fragments.add(fragment);
        fragments.add(questionFragment);
        HomeReasultViewPagAdapter adapter = new HomeReasultViewPagAdapter(getSupportFragmentManager(), mContext, fragments, list);
        mVpSearchReasult.setAdapter(adapter);
        mTabTitleContent.setupWithViewPager(mVpSearchReasult);

    }


    private void initView() {
        mContext = this;
        mTabTitleContent = (TabLayout) findViewById(R.id.tab_title_content);
        mVpSearchReasult = (ViewPager) findViewById(R.id.vp_search_reasult);
    }

}
