package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.T;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PawChangerActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 修改密码页
 * @author: L-BackPacker
 * @date: 2018/5/28 9:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/28
 */
public class PawChangerActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtMPOldPaw;
    private EditText mEtMPNewPaw;
    private EditText mEtMPNewPaw1;
    private Button mBtnMPCpSubmit;
    private Context mContext;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paw_changer);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_paw_changer);
        initView();
    }

    private void initView() {
        mContext = this;
        mEtMPOldPaw = (EditText) findViewById(R.id.et_m_p_old_paw);
        mEtMPNewPaw = (EditText) findViewById(R.id.et_m_p_new_paw);
        mEtMPNewPaw1 = (EditText) findViewById(R.id.et_m_p_new_paw1);
        mBtnMPCpSubmit = (Button) findViewById(R.id.btn_m_p_cp_submit);

        mBtnMPCpSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_p_cp_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String paw = getTextStr(mEtMPOldPaw);
        if (TextUtils.isEmpty(paw)) {
            T.showToast(mContext, getString(R.string.old_paw_empty));
            return;
        }
        String newPaw = getTextStr(mEtMPNewPaw);
        if (TextUtils.isEmpty(newPaw)) {
            T.showToast(mContext,getString( R.string.new_paw_empty));
            return;
        }
        String newPaw1 = getTextStr(mEtMPNewPaw1);
        if (TextUtils.isEmpty(newPaw1)) {
            T.showToast(mContext,getString( R.string.sure_paw_sure));
            return;
        }
        if (!newPaw.equals(newPaw1)) {
            T.showToast(mContext, getString(R.string.paw_no_same));
            return;
        }


    }
}
