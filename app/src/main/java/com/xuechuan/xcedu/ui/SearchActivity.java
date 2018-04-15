package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.weight.NewLineViewGroup;

/**
 * @version V 1.0 xxxxxxx
 * @Title: SearchActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 搜索页
 * @author: YFL
 * @date: 2018/4/15  14:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private static String ARG_PARAM1 = "arg_param1";
    private static String ARG_PARAM2 = "arg_param2";
    private EditText mEtSearch;
    private ImageView mIvSearch;
    private TextView mTvSearchClear;
    private RelativeLayout mRlSearchHistory;
    private NewLineViewGroup mVgSearchHistory;
    private NewLineViewGroup mVgSearchHost;


    public static void newInstance(Context context, String param1, String param2) {
        Intent intent = new Intent();
        intent.putExtra(ARG_PARAM1, param1);
        intent.putExtra(ARG_PARAM2, param2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initData() {


    }
    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mTvSearchClear = (TextView) findViewById(R.id.tv_search_clear);
        mRlSearchHistory = (RelativeLayout) findViewById(R.id.rl_search_history);
        mVgSearchHistory = (NewLineViewGroup) findViewById(R.id.vg_search_history);
        mVgSearchHost = (NewLineViewGroup) findViewById(R.id.vg_search_host);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
