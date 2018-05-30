package com.xuechuan.xcedu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.player.PolyvPlayerActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.Defaultcontent;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.ShareUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnPlay;
    private Button mBtnPlayGet;
    private Button mBtnPlayPost;
    private MainActivity mContext;
    private Button mBtnLocation;
    private Button mBtnBaoli;
    private Button mBtnHome;
    private Button mBtnGetToken;
    private Button mBtnLogin;
    private Button mButton;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;

/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initView() {
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        mBtnPlayGet = (Button) findViewById(R.id.btn_play_get);
        mBtnPlayGet.setOnClickListener(this);
        mBtnPlayPost = (Button) findViewById(R.id.btn_play_post);
        mBtnPlayPost.setOnClickListener(this);
        mContext = this;
        mBtnLocation = (Button) findViewById(R.id.btn_location);
        mBtnLocation.setOnClickListener(this);
        mBtnBaoli = (Button) findViewById(R.id.btn_baoli);
        mBtnBaoli.setOnClickListener(this);
        mBtnHome = (Button) findViewById(R.id.btn_home);
        mBtnHome.setOnClickListener(this);
        mBtnGetToken = (Button) findViewById(R.id.btn_getToken);
        mBtnGetToken.setOnClickListener(this);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton5.setOnClickListener(this);
    }

    protected void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                L.e("===========", getStr());
                break;
            case R.id.btn_play_get:
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
            case R.id.button:
                ShareUtils.shareWeb(mContext, Defaultcontent.url,Defaultcontent.title
                ,Defaultcontent.text,Defaultcontent.imageurl,R.mipmap.m_setting_about_xcimg
                        ,SHARE_MEDIA.QQ);
                break;
            case R.id.button2:
                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.m_setting_about_xcimg, SHARE_MEDIA.QZONE
                );
                break;
            case R.id.button3:
                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.m_setting_about_xcimg, SHARE_MEDIA.WEIXIN
                );
                break;
            case R.id.button4:
                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.m_setting_about_xcimg, SHARE_MEDIA.WEIXIN_CIRCLE
                );
                break;
            case R.id.button5:
                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.m_setting_about_xcimg
                        , SHARE_MEDIA.SINA
                );
                break;
        }
    }


    private String getStr() {
     /*   Integer[] a = {1, 2, 3};
        JSONObject object = new JSONObject();
        text text = new text();
        text.setName("222222222");
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        List<MainActivity.text> texts = new ArrayList<>();
        texts.add(text);
        try {
            object.put("name", "xiao");
            object.put("int", a);
            object.put("text", texts);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        JsonObject jsonObject = new JsonObject();
        JsonObject one = new JsonObject();
        one.addProperty("name", "1");

        JsonArray two = new JsonArray();
        JsonObject lan1 = new JsonObject();
        lan1.addProperty("", 1);

        JsonObject lan2 = new JsonObject();
        lan2.addProperty("", 2);
        two.add(1);
        two.add(2);


        JsonObject threeArr1 = new JsonObject();
        threeArr1.addProperty("name", "name1");
        JsonObject threeArr2 = new JsonObject();
        threeArr2.addProperty("name", "name2");
        JsonObject threeArr3 = new JsonObject();
        threeArr3.addProperty("name", "name2");

        JsonArray array = new JsonArray();
        array.add(threeArr1);
        array.add(threeArr2);
        jsonObject.add("one", one);
        jsonObject.add("two", two);
        jsonObject.add("three", array);

        return jsonObject.toString();

    }


    public class text {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "text{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
