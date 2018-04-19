package com.xuechuan.xcedu.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.xuechuan.xcedu.R;

public class InfomListActivity extends AppCompatActivity {

    private RecyclerView mRlvInfomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infom_list);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mRlvInfomList = (RecyclerView) findViewById(R.id.rlv_infom_list);
    }
}
