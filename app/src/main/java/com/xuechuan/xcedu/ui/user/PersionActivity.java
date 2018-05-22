package com.xuechuan.xcedu.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PersionActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 个人信息修改页
 * @author: L-BackPacker
 * @date: 2018/5/22 9:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class PersionActivity extends BaseActivity {


    private TextView mTvMPSubmit;
    private ImageView mIvMPImg;
    private EditText mEtMPName;
    private LinearLayout mLlMPName;
    private TextView mTvMPSex;
    private LinearLayout mLiMPSex;
    private TextView mEtMPBirthday;
    private LinearLayout mLlMPBirthday;
    private TextView mEtMPCity;
    private LinearLayout mLlMPCity;
    private EditText mEtMPPhone;
    private LinearLayout mLlMPPhone;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_persion);
        initView();
    }


    private void initView() {
        mTvMPSubmit = (TextView) findViewById(R.id.tv_m_p_submit);
        mIvMPImg = (ImageView) findViewById(R.id.iv_m_p_img);
        mEtMPName = (EditText) findViewById(R.id.et_m_p_name);
        mLlMPName = (LinearLayout) findViewById(R.id.ll_m_p_name);
        mTvMPSex = (TextView) findViewById(R.id.tv_m_p_sex);
        mLiMPSex = (LinearLayout) findViewById(R.id.li_m_p_sex);
        mEtMPBirthday = (TextView) findViewById(R.id.et_m_p_birthday);
        mLlMPBirthday = (LinearLayout) findViewById(R.id.ll_m_p_birthday);
        mEtMPCity = (TextView) findViewById(R.id.et_m_p_city);
        mLlMPCity = (LinearLayout) findViewById(R.id.ll_m_p_city);
        mEtMPPhone = (EditText) findViewById(R.id.et_m_p_phone);
        mLlMPPhone = (LinearLayout) findViewById(R.id.ll_m_p_phone);
    }

    private void submit() {
        String name = getTextStr(mEtMPName);
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = getTextStr(mEtMPPhone);
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
