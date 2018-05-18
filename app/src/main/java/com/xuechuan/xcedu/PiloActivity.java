package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.presenter.RefreshTokenPresenter;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.TokenVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PiloActivity
 * @Package com.xuechuan.xcedu
 * @Description: 引导页
 * @author: L-BackPacker
 * @date: 2018/5/14 8:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */

public class PiloActivity extends BaseActivity implements RefreshTokenView, EasyPermissions.PermissionCallbacks {

    private ImageView mIvPilo;
    private Context mContext;
    private static final int RC_CAMERA_PERM = 488;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilo);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pilo);
        initView();
//        startActivity(new Intent(PiloActivity.this,DownActivity.class));
        requesPermission();

    }

    private void requesPermission() {
        if (EasyPermissions.hasPermissions(this, DataMessageVo.Persmission)) {
            initData();
        } else {
            PermissionRequest build = new PermissionRequest.Builder(this, 0, DataMessageVo.Persmission)
                    .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用")
                    .setNegativeButtonText(R.string.cancel)
                    .setPositiveButtonText(R.string.allow)
                    .build();
            EasyPermissions.requestPermissions(build);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initData() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = DbHelperAssist.getInstance().queryWithuuId(userId);
        if (userInfomDb != null && userInfomDb.getVo() != null) {
            MyAppliction.getInstance().setUserInfom(userInfomDb.getVo());
            RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
            presenter.refreshToken(mContext, userInfomDb.getToken());
        } else {
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            startActivity(intent1);
            this.finish();
        }
    }


    @Override
    public void TokenSuccess(String con) {
        L.d(con);
        Gson gson = new Gson();
        TokenVo tokenVo = gson.fromJson(con, TokenVo.class);
        if (tokenVo.getStatus().getCode() == 200) {
            int statusX = tokenVo.getData().getStatusX();
            TokenVo.DataBean data = tokenVo.getData();
            switch (statusX) {
                case -1:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                case -2:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;
                case 1:
                    updataToken(data);
                    break;
                default:
            }
        } else {
            SaveUUidUtil.getInstance().delectUUid();
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            startActivity(intent1);
            this.finish();
            L.e(tokenVo.getStatus().getMessage());
        }
    }

    @Override
    public void TokenError(String con) {

    }

    private void updataToken(TokenVo.DataBean data) {
        TokenVo.DataBean.TokenBean token = data.getToken();
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        UserBean user = userInfom.getData().getUser();
        user.setId(token.getStaffid());
        user.setToken(token.getSigntoken());
        user.setTokenexpire(token.getExpiretime());
        DbHelperAssist.getInstance().saveUserInfom(userInfom);
        HomeActivity.newInstance(mContext, "", "");
        this.finish();
    }

    private void initView() {
        mContext = this;
        mIvPilo = (ImageView) findViewById(R.id.iv_pilo);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//某些权限已被授予
        List<String> mDATA = Arrays.asList(DataMessageVo.Persmission);
        if (perms.contains(mDATA)) {
            initData();
        } else {
            requesPermission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//某些权限已被拒绝

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < perms.size(); i++) {
            builder.append(perms.get(i).toString().trim()+"\n");
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setPositiveButton(R.string.allow)
                    .setTitle("权限申请")
                    .setNegativeButton(R.string.cancel)
                    .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用\n" + builder.toString())
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            requesPermission();
        }
    }
}
