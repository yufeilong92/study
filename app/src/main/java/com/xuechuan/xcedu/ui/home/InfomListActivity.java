package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.HomeService;

public class InfomListActivity extends BaseActivity {

    private RecyclerView mRlvInfomList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infom_list);
        initView();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_infom_list);
        initView();
        initData();
    }

    private void initData() {
        HomeService.getInstance(mContext);
    }

    private void initView() {
        mContext = this;
        mRlvInfomList = (RecyclerView) findViewById(R.id.rlv_infom_list);
    }
}
