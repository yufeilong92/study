package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

import java.util.ArrayList;
import java.util.List;

public class AtricleTextListActivity extends AppCompatActivity {

    private RecyclerView mRlvTextContent;
    private XRefreshView mXfvTextContent;
    private List mArray;
    private boolean isRefresh;
    private Context mContext;
    /**
     * 科目编号
     */
    private static String COURSEID = "courseid";
    private String mOid;

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(courseid, courseid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atricle_text);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        clearData();
        initData();
        initRefresh();
        setAdapter();
    }

    private void setAdapter() {

    }

    private void initRefresh() {
        mXfvTextContent.setPullRefreshEnable(true);
        mXfvTextContent.setPullLoadEnable(false);
        mXfvTextContent.setAutoLoadMore(true);
        mXfvTextContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
            }
        });
    }

    private void initData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        BankService service = new BankService(mContext);
        service.requestChapterList(mOid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh=false;
                mXfvTextContent.stopRefresh();

            }

            @Override
            public void onError(Response<String> response) {
                isRefresh=false;
            }
        });
    }

    private void initView() {
        mContext = this;
        mRlvTextContent = (RecyclerView) findViewById(R.id.rlv_text_content);
        mXfvTextContent = (XRefreshView) findViewById(R.id.xfv_text_content);
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
}
