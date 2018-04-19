package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AddressAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.HomeFragment;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.RecyclerSelectItem;
import com.xuechuan.xcedu.vo.ProvincesVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddressSelectActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 定位选择页
 * @author: L-BackPacker
 * @date: 2018/4/14 15:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/14
 */
public class AddressSelectActivity extends BaseActivity {
    private RecyclerView mRlvSelAdress;
    private Context mContext;
    /**
     * 地区
     */
    private static String ADDRESS = "address";
    private String mAddress;
    private int mPosition;
    /**
     * 位标
     */
    private static String POSITON = "position";
    private ArrayList<ProvincesVo> list;

    public static Intent newInstance(Context context, String address) {
        Intent intent = new Intent(context, AddressSelectActivity.class);
        intent.putExtra(ADDRESS, address);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_select);
        if (getIntent() != null) {
            mAddress = getIntent().getStringExtra(ADDRESS);
        }
        initView();
        initData();
        bingView();
    }

    private void initData() {
        if (list == null) {
            list = PushXmlUtil.getInstance().pushXml(mContext);
        } else {
            list.clear();
            list = PushXmlUtil.getInstance().pushXml(mContext);
        }
    }

    private void initView() {
        mContext = this;
        mRlvSelAdress = (RecyclerView) findViewById(R.id.rlv_sel_adress);
    }

    private void bingView() {
        AddressAdapter addressAdapter = new AddressAdapter(mContext, list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvSelAdress.setLayoutManager(gridLayoutManager);
        mRlvSelAdress.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvSelAdress.setAdapter(addressAdapter);
        int mPosition = getPersionPosition(mAddress, list);
        addressAdapter.setSelectItem(mAddress, mPosition);
        RecyclerSelectItem.MoveToPostion(gridLayoutManager, mRlvSelAdress, mPosition);
        addressAdapter.setOnClickListener(new AddressAdapter.AddressOnClickListener() {
            @Override
            public void onClickListener(ProvincesVo vo, int position) {
                Intent intent = new Intent();
                intent.putExtra(HomeFragment.STR_INT_PROVINCE, vo.getName());
                intent.putExtra(HomeFragment.STR_INT_CODE, vo.getCode());
                intent.putExtra(HomeFragment.STR_INT_POSITION, position);
                setResult(HomeFragment.REQUESTRESULT, intent);
                finish();
            }
        });
    }

    private int getPersionPosition(String mAddress, ArrayList<ProvincesVo> list) {
        if (mAddress == null || list == null) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            ProvincesVo vo = list.get(i);
            if (vo.getName().equals(mAddress)) {
                return i;
            }
        }
        return -1;
    }


}
