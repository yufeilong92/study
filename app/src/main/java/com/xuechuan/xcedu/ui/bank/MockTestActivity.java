package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.debug.log.E;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.ExamModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ExamPresenter;
import com.xuechuan.xcedu.mvp.view.ExamView;
import com.xuechuan.xcedu.utils.L;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MockTestActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 获取试卷（真题，独家密卷）
 * @author: L-BackPacker
 * @date: 2018/5/3 20:48
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3
 */
public class MockTestActivity extends BaseActivity implements ExamView {
    private ExamPresenter mPresenter;


    private static String MQUESTIONOID = "mquestionoid";
    private String mOid;

    public static Intent newInstance(Context context, String questionoid) {
        Intent intent = new Intent(context, MockTestActivity.class);
        intent.putExtra(MQUESTIONOID, questionoid);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mock_test);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(MQUESTIONOID);
        }
        initData();
    }

    private void initData() {
        mPresenter = new ExamPresenter(new ExamModelImpl(), this);
        mPresenter.requestExamContent(this, mOid);
    }

    @Override
    public void ExamSuccess(String con) {
        L.e("真题/迷题1" + con);
    }

    @Override
    public void ExamError(String con) {
        L.e("真题/迷题2" + con);
    }
}
