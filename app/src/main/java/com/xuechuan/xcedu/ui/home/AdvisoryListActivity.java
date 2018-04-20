package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AdvisoryListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.AdvisoryListVo;
import com.xuechuan.xcedu.vo.AdvisoryVo;
import com.xuechuan.xcedu.vo.ProvinceEvent;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AdvisoryListActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 资讯列表
 * @author: L-BackPacker
 * @date: 2018/4/20 9:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/20
 */
public class AdvisoryListActivity extends BaseActivity implements View.OnClickListener {

    private static String PROVICECODE = "provicecode";
    public static String CSTR_EXTREA_TITLE ="titletex";
    private String mProvinceCode;
    private RecyclerView mRlvAdvisoryList;
    private Context mContext;
    private ImageView mIvAdviistoryLocation;
    private TextView mTvAddressTitle;


    /**
     * @param context
     * @param proviceCode 省份
     */
    public static Intent newInstance(Context context, String proviceCode) {
        Intent intent = new Intent(context, AdvisoryListActivity.class);
        intent.putExtra(PROVICECODE, proviceCode);
        return intent;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_advisory_list);
//        initView();
//        if (getIntent() != null) {
//            mProvinceCode = getIntent().getStringExtra(PROVICECODE);
//        }
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_advisory_list);
        EventBus.getDefault().register(this);
        String title = null;
        if (getIntent() != null) {
            mProvinceCode = getIntent().getStringExtra(PROVICECODE);
            title = getIntent().getStringExtra(CSTR_EXTREA_TITLE);
        }
        initView();
        mTvAddressTitle.setText(title);
        requestData(mProvinceCode);
    }

    private void requestData(String mProvinceCode) {
        HomeService service = HomeService.getInstance(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext("", getStringWithId(R.string.loading));
        service.requestAdvisoryList(mProvinceCode, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.d("资讯数据==" + message);
                Gson gson = new Gson();
                AdvisoryListVo vo = gson.fromJson(message, AdvisoryListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<AdvisoryVo> datas = vo.getDatas();
                    bindAdapter(datas);
                } else {
                    T.showToast(mContext, status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                L.e(response.message());
            }
        });
    }

    private void initView() {
        mRlvAdvisoryList = (RecyclerView) findViewById(R.id.rlv_advisory_list);
        mContext = this;
        mIvAdviistoryLocation = (ImageView) findViewById(R.id.iv_adviistory_location);
        mIvAdviistoryLocation.setOnClickListener(this);
        mTvAddressTitle = (TextView) findViewById(R.id.tv_address_title);
        mTvAddressTitle.setOnClickListener(this);
    }

    private void bindAdapter(List<AdvisoryVo> vos) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        AdvisoryListAdapter advisoryListAdapter = new AdvisoryListAdapter(mContext, vos);
        mRlvAdvisoryList.setLayoutManager(gridLayoutManager);
        mRlvAdvisoryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvAdvisoryList.setAdapter(advisoryListAdapter);
        advisoryListAdapter.setClickListener(new AdvisoryListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                AdvisoryVo vo = (AdvisoryVo) obj;
                String gourl = vo.getGourl();
                InfomActivity.newInstance(mContext, gourl);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_adviistory_location:
                String provice = PushXmlUtil.getInstance().getLocationProvice(mContext, mProvinceCode);
                Intent intent = AddressShowActivity.newInstance(mContext, provice, AddressShowActivity.TYPELIST);
                startActivity(intent);
                break;
            default:

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(ProvinceEvent event) {
        String province = event.getProvince();
        mTvAddressTitle.setText(province);
        mProvinceCode = event.getCode();
        requestData(mProvinceCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

