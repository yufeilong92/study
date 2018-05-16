package com.xuechuan.xcedu.ui.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBookDownActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 下载详情
 * @author: L-BackPacker
 * @date: 2018/5/16 11:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetBookDownActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRlvNetLoadingGoing;
    private LinearLayout mLlNetLoadingGoing;
    private RecyclerView mRlvLoadingOver;
    private LinearLayout mLlLoadingOver;
    private Button mBtnNetDownDelect;
    private LinearLayout mLlNetDownAll;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_down);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_down);
        initView();
    }

    private void initView() {
        mRlvNetLoadingGoing = (RecyclerView) findViewById(R.id.rlv_net_loading_going);
        mLlNetLoadingGoing = (LinearLayout) findViewById(R.id.ll_net_loading_going);
        mRlvLoadingOver = (RecyclerView) findViewById(R.id.rlv_loading_over);
        mLlLoadingOver = (LinearLayout) findViewById(R.id.ll_loading_over);
        mBtnNetDownDelect = (Button) findViewById(R.id.btn_net_down_delect);
        mBtnNetDownDelect.setOnClickListener(this);
        mLlNetDownAll = (LinearLayout) findViewById(R.id.ll_net_down_all);
        mLlNetDownAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_delect:

                break;
        }
    }
}
