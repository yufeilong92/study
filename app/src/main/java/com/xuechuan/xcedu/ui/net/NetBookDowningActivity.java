package com.xuechuan.xcedu.ui.net;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

public class NetBookDowningActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvNetDowningMake;
    private TextView mTvNetDowningDo;
    private TextView mTvDowning;
    private RecyclerView mRlvNetDowningList;
    private TextView mTvNetDowningStop;
    private CheckBox mChbNetDownAll;
    private Button mBtnNetDownDelect;
    private RelativeLayout mRlNetDownDelect;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_book_downing);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_book_downing);
        initView();
        initData();
    }

    private void initData() {



    }

    private void initView() {
        mTvNetDowningMake = (TextView) findViewById(R.id.tv_net_downing_make);
        mTvNetDowningDo = (TextView) findViewById(R.id.tv_net_downing_do);
        mTvDowning = (TextView) findViewById(R.id.tv_downing);
        mRlvNetDowningList = (RecyclerView) findViewById(R.id.rlv_net_downing_list);
        mTvNetDowningStop = (TextView) findViewById(R.id.tv_net_downing_stop);
        mChbNetDownAll = (CheckBox) findViewById(R.id.chb_net_down_all);
        mBtnNetDownDelect = (Button) findViewById(R.id.btn_net_down_delect);
        mRlNetDownDelect = (RelativeLayout) findViewById(R.id.rl_net_down_delect);

        mBtnNetDownDelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_down_delect:

                break;
        }
    }
}
