package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @Title:  BookActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description:  教材页面
 * @author: L-BackPacker
 * @date:   2018/4/19 16:43
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class BookActivity extends BaseActivity {

    private ImageView mIvBookSkill;
    private ImageView mIvBookColligate;
    private ImageView mIvBookCase;

    private static String PARAMP = "PARAMP";
    private static String PARAMP1 = "PARAMP";
    private String params;
    private String params1;

    public static void newInstance(Context context, String param, String param1) {
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra(PARAMP, param);
        intent.putExtra(PARAMP, param1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book);
        if (getIntent()!=null){
            params = getIntent().getStringExtra(PARAMP);
            params1 = getIntent().getStringExtra(PARAMP1);
        }
        initView();
    }

    private void initView() {
        mIvBookSkill = (ImageView) findViewById(R.id.iv_book_skill);
        mIvBookColligate = (ImageView) findViewById(R.id.iv_book_colligate);
        mIvBookCase = (ImageView) findViewById(R.id.iv_book_case);
    }
}
