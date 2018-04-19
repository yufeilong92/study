package com.xuechuan.xcedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.player.PolyvPlayerActivity;
import com.xuechuan.xcedu.ui.LoginActivity;

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
    private Button mBtnLogin;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initView() {
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        url = getResources().getString(R.string.app_content_token_text);
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
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

    }

    protected void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                break;
            case R.id.btn_play_get:
//                OkTextGetRequest.getInstance().sendRequestGet(mContext, "id2", new StringCallBackView() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        L.e(response.body().toString());
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//
//                    }
//                });

                break;
            case R.id.btn_play_post:
                break;
            case R.id.btn_location:
//                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
//                startActivity(intent);
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
            case R.id.btn_login:
                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
