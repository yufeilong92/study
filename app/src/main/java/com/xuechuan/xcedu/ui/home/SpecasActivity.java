package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView mRlvSpecaContent;

    private static String PARAME = "PARAME";
    private static String PARAME1 = "PARAME";
    private String parame;
    private String parame1;
    private Context mContext;
    private XRefreshView mXrfvSpecaRefresh;
    private List mArrary;
    /**
     * 刷新时间
     */
    public static long lastRefreshTime;
    private SpecsOrderAdapter adapter;
    /**
     * 防止冲突
     */
    private boolean isRefresh = false;

    public static Intent newInstance(Context context, String parame, String parame1) {
        Intent intent = new Intent(context, SpecasActivity.class);
        intent.putExtra(PARAME, parame);
        intent.putExtra(PARAME1, parame1);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specas);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specas);
        if (getIntent() != null) {
            parame = getIntent().getStringExtra(PARAME);
            parame1 = getIntent().getStringExtra(PARAME1);
        }
        initView();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvSpecaRefresh.startRefresh();
    }

    private void requestData() {
        if (isRefresh) {
            isRefresh = false;
            return;
        }
        HomeService service = HomeService.getInstance(mContext);
        service.requestChapterList(1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                mXrfvSpecaRefresh.stopRefresh();
                String s = response.body().toString();
                L.e("获取规范章节列表数据" + s);
                Gson gson = new Gson();
                SpecasChapterListVo vo = gson.fromJson(s, SpecasChapterListVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
//                    List<DatasBeanVo> mArraylist = vo.getDatas();
                    List list = vo.getDatas();
                    clearData();
                    addListData(list);
                    adapter.notifyDataSetChanged();
                    mXrfvSpecaRefresh.setPullLoadEnable(true);
                    mXrfvSpecaRefresh.setLoadComplete(false);
//                    mXrfvSpecaRefresh.setLoadComplete(true);

                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                L.e("获取规范章节错误" + response.message());
            }
        });
    }

    /**
     * 绑定适配器数据
     */
    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new SpecsOrderAdapter(mContext, mArrary, gridLayoutManager);
        mRlvSpecaContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);



    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mXrfvSpecaRefresh.setPullLoadEnable(true);
                        mXrfvSpecaRefresh.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
//                mXrfvSpecaRefresh.setLoadComplete(false);
            }
        });
        adapter.setClickListener(new SpecsOrderAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {

            }
        });
    }


    private void initView() {
        mContext = this;
        mRlvSpecaContent = (RecyclerView) findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) findViewById(R.id.xrfv_speca_refresh);

    }

    private void initRefresh(SpecsOrderAdapter adapter) {

    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

}
