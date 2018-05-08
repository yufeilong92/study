package com.xuechuan.xcedu.base;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基类
 * @author: L-BackPacker
 * @date: 2018/4/10 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 传入参数-标题
     */
    public static final String CSTR_EXTRA_TITLE_STR = "title";
    private String mBaseTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mBaseTitle = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
        initContentView(savedInstanceState);
        if (!StringUtil.isEmpty(mBaseTitle)) {
            setTitle(mBaseTitle);
        }


    }

    protected abstract void initContentView(Bundle savedInstanceState);


    private void setTitle(String str) {
        TextView title = (TextView) findViewById(R.id.activity_title_text);
        title.setText(str);
    }

    public void onHomeClick(View view) {
        finish();
    }

    protected String getTextStr(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            return tv.getText().toString().trim();
        }
        if (view instanceof Button) {
            Button btn = (Button) view;
            return btn.getText().toString().trim();
        }
        if (view instanceof EditText) {
            EditText et = (EditText) view;
            return et.getText().toString().trim();
        }
        return null;
    }

    protected String getStringWithId(int id) {
        return getResources().getString(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.e("走了父类产品----------------------");
        OkGo.getInstance().cancelAll();
    }
}
