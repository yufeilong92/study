package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.HomeEvaluateAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: InfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 评价页详情
 * @author: L-BackPacker
 * @date: 2018/4/19 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class EvalueDetialActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 文章编号
     */
    private static String ARTICLEID = "articleid ";
    /***
     * commentid
     */
    private static String COMMENTID = "commentid ";
    private RecyclerView mRlvEvalueDetail;
    private XRefreshView mXfvEvauleContent;
    private boolean isRefresh;
    private List mArray;
    private Context mContext;
    private HomeEvaluateAdapter adapter;

    private String mArticleid;
    private String mCommentid;

    public static Intent newInstance(Context context, String articleid, String commentid) {
        Intent intent = new Intent(context, EvalueDetialActivity.class);
        intent.putExtra(ARTICLEID, articleid);
        intent.putExtra(COMMENTID, commentid);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalue_detial);
        initView();

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evalue_detial);
        if (getIntent() != null) {
            mArticleid = getIntent().getStringExtra(ARTICLEID);
            mCommentid = getIntent().getStringExtra(ARTICLEID);
        }
        initView();
        clearData();
        initAdapter();
        initRxfresh();
        mXfvEvauleContent.startRefresh();
    }

    private void initAdapter() {
        adapter = new HomeEvaluateAdapter(mContext, mArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvEvalueDetail.setLayoutManager(gridLayoutManager);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mRlvEvalueDetail.setAdapter(adapter);

    }

    private void initRxfresh() {
        mXfvEvauleContent.setAutoLoadMore(true);
        mXfvEvauleContent.setPullLoadEnable(false);
        mXfvEvauleContent.setPullRefreshEnable(true);
        mXfvEvauleContent.setMoveForHorizontal(true);
        mXfvEvauleContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }

            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }
        });
    }

    private void loadMoreData() {
        if (isRefresh){
            return;
        }
        isRefresh=true;
        HomeService service = new HomeService(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext(mContext, "", getStringWithId(R.string.loading));
        service.requestCommentCommentList(getNowPage()+1,mArticleid, mCommentid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXfvEvauleContent.stopRefresh();
                L.w(response.body().toString());

            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                T.showToast(mContext,response.message());
            }
        });

    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = new HomeService(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext(mContext, "", getStringWithId(R.string.loading));
        service.requestCommentCommentList(1,mArticleid, mCommentid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXfvEvauleContent.stopRefresh();
                L.w(response.body().toString());

            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                T.showToast(mContext,response.message());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        mContext = this;
        mRlvEvalueDetail = (RecyclerView) findViewById(R.id.rlv_evalue_detail);
        mXfvEvauleContent = (XRefreshView) findViewById(R.id.xfv_evaule_content);
    }
    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }
    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArray == null) {
            clearData();
        }
        mArray.addAll(list);
    }


}
