package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecasActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 规范页
 * @author: L-BackPacker
 * @date: 2018/4/19 17:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class SpecasActivity extends BaseActivity {

    private ListView mLvSpecaContent;

    private static String PARAME = "PARAME";
    private static String PARAME1 = "PARAME";
    private String parame;
    private String parame1;

    public static void newInstance(Context context, String parame, String parame1) {
        Intent intent = new Intent(context, SpecasActivity.class);
        intent.putExtra(PARAME, parame);
        intent.putExtra(PARAME1, parame1);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specas);
        if (getIntent()!=null){
            parame = getIntent().getStringExtra(PARAME);
            parame1 = getIntent().getStringExtra(PARAME1);
        }
        initView();
    }


    private void initView() {
        mLvSpecaContent = (ListView) findViewById(R.id.lv_speca_content);

    }
}
