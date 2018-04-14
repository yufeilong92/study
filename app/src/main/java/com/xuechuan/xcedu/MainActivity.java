package com.xuechuan.xcedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.baidu.LocationActivity;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.OkTextGetRequest;
import com.xuechuan.xcedu.net.OkTextPostRequest;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.player.PolyvPlayerActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXml;
import com.xuechuan.xcedu.vo.ProvincesVo;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnPlay;
    private String url;
    private Button mBtnPlayGet;
    private Button mBtnPlayPost;
    private MainActivity mContext;
    private Button mBtnLocation;
    private Button mBtnBaoli;
    private Button mBtnHome;
    private Button mBtnGetToken;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initView() {
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        url = getResources().getString(R.string.app_content_token);
        mBtnPlayGet = (Button) findViewById(R.id.btn_play_get);
        mBtnPlayGet.setOnClickListener(this);
        mBtnPlayPost = (Button) findViewById(R.id.btn_play_post);
        mBtnPlayPost.setOnClickListener(this);
        mContext = this;
        mBtnLocation = (Button) findViewById(R.id.btn_location);
        mBtnLocation.setOnClickListener(this);
        mBtnBaoli = (Button) findViewById(R.id.btn_baoli);
        mBtnBaoli.setOnClickListener(this);
        mBtnHome = findViewById(R.id.btn_home);
        mBtnHome.setOnClickListener(this);
        mBtnGetToken = (Button) findViewById(R.id.btn_getToken);
        mBtnGetToken.setOnClickListener(this);

    }

    protected void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:

                break;
            case R.id.btn_play_get:
                OkTextGetRequest.getInstance().sendRequestGet(mContext, "id2", new StringCallBackView() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        L.e(response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }
                });

                break;
            case R.id.btn_play_post:
                OkTextPostRequest.getInstance().sendRequestPost(mContext, "id2", new StringCallBackView() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        L.e(response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }
                });
                break;
            case R.id.btn_location:
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baoli:
                Intent intent1 = new Intent(MainActivity.this, PolyvPlayerActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_home:
                Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_getToken:

                break;
        }
    }


}
