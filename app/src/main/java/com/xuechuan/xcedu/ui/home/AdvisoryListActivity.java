package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.umeng.debug.log.E;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AdvisoryListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
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

import java.util.ArrayList;
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
    public static String CSTR_EXTREA_TITLE = "titletex";
    private String mProvinceCode;
    private RecyclerView mRlvAdvisoryList;
    private Context mContext;
    private ImageView mIvAdviistoryLocation;
    private TextView mTvAddressTitle;
    private XRefreshView mXrvContent;
    /**
     * 保存数据
     */
    private List mArray;
    /**
     * 防止重复刷新
     */
    private boolean isRefresh;
    private AdvisoryListAdapter adapter;
    private long lastRefreshtime;


    /**
     * @param context
     * @param proviceCode 省份
     */
    public static Intent newInstance(Context context, String proviceCode) {
        Intent intent = new Intent(context, AdvisoryListActivity.class);
        intent.putExtra(PROVICECODE, proviceCode);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisory_list);
        initView();
        if (getIntent() != null) {
            mProvinceCode = getIntent().getStringExtra(PROVICECODE);
        }
    }*/

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

        clearData();
        bindAdapter();
        initRxfresh();
        mXrvContent.startRefresh();
//        requestData(mProvinceCode);
    }

    private void initRxfresh() {
        mXrvContent.setPullLoadEnable(true);
        mXrvContent.stopLoadMore(true);
        mXrvContent.setAutoLoadMore(true);
        mXrvContent.restoreLastRefreshTime(lastRefreshtime);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXrvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData(mProvinceCode);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = HomeService.getInstance(mContext);
        service.requestAdvisoryList(mProvinceCode, getPager() + 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXrvContent.stopRefresh();
                String message = response.body().toString();
                L.d("资讯数据==" + message);
                Gson gson = new Gson();
                AdvisoryListVo vo = gson.fromJson(message, AdvisoryListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<AdvisoryVo> datas = vo.getDatas();
//                    clearData();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    }else {
                        mXrvContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXrvContent.setLoadComplete(false);
                        mXrvContent.setPullLoadEnable(true);
                    } else {
                        mXrvContent.setLoadComplete(true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                L.e(response.message());
            }
        });
    }

    private void requestData(String mProvinceCode) {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = HomeService.getInstance(mContext);
        service.requestAdvisoryList(mProvinceCode, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXrvContent.stopRefresh();
                lastRefreshtime = mXrvContent.getLastRefreshTime();

                String message = response.body().toString();
                L.d("资讯数据==" + message);
                Gson gson = new Gson();
                AdvisoryListVo vo = gson.fromJson(message, AdvisoryListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<AdvisoryVo> datas = vo.getDatas();
                    clearData();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    }
                    if (mArray.size()<DataMessageVo.CINT_PANGE_SIZE||mArray.size() == vo.getTotal().getTotal()) {
                        mXrvContent.setLoadComplete(true);
                    } else {
                        mXrvContent.setPullLoadEnable(true);
                        mXrvContent.setLoadComplete(false);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
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
        mXrvContent = (XRefreshView) findViewById(R.id.xrv_content);
        mXrvContent.setOnClickListener(this);
    }

    private void bindAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new AdvisoryListAdapter(mContext, mArray);
        mRlvAdvisoryList.setLayoutManager(gridLayoutManager);
        mRlvAdvisoryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvAdvisoryList.setAdapter(adapter);
        adapter.setClickListener(new AdvisoryListAdapter.onItemClickListener() {
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

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (mArray == null) {
            clearData();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mArray.addAll(list);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private int getPager() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }
}

