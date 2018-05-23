package com.xuechuan.xcedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.player.PolyvPlayerActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.RequestBody;

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
                L.e("===========", getStr());
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
        one.addProperty("name","1");

        JsonArray two = new JsonArray();
        JsonObject lan1=new JsonObject();
        lan1.addProperty("", 1);

        JsonObject lan2=new JsonObject();
        lan2.addProperty("", 2);
        two.add(1);
        two.add(2);


        JsonObject threeArr1 = new JsonObject();
        threeArr1.addProperty("name","name1");
        JsonObject threeArr2 = new JsonObject();
        threeArr2.addProperty("name","name2");
        JsonObject threeArr3 = new JsonObject();
        threeArr3.addProperty("name","name2");

        JsonArray array = new JsonArray();
        array.add(threeArr1);
        array.add(threeArr2);
        jsonObject.add("one",one);
        jsonObject.add("two",two);
        jsonObject.add("three",array);

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
