package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AnsewerResultAdpater;
import com.xuechuan.xcedu.base.DataMessageVo;

import java.util.ArrayList;
import java.util.List;

public class AnswerListActivity extends AppCompatActivity {

    private ImageView mIvBMore;
    private RecyclerView mRlvBAnswerContentResult;
    private LinearLayout mLlBBack;
    private LinearLayout mLlBGo;
    private ImageView mIvBExpand;
    private TextView mTvBNew;
    private TextView mTvBCount;
    private CheckBox mChbBCollect;
    private List mArrary;
    private Context mContext;
    private AnsewerResultAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        initView();
        initAdapter();

    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adpater = new AnsewerResultAdpater(mContext, mArrary);
        mRlvBAnswerContentResult.setLayoutManager(gridLayoutManager);
        LayoutInflater from = LayoutInflater.from(mContext);
        View view = from.inflate(R.layout.answer_layout_hear_reasult, null);
        adpater.setHeaderView(view,mRlvBAnswerContentResult);


        mRlvBAnswerContentResult.setAdapter(adpater);
    }

    private void initView() {
        mContext = this;
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mRlvBAnswerContentResult = (RecyclerView) findViewById(R.id.rlv_b_answer_content_result);
        mLlBBack = (LinearLayout) findViewById(R.id.ll_b_back);
        mLlBGo = (LinearLayout) findViewById(R.id.ll_b_go);
        mIvBExpand = (ImageView) findViewById(R.id.iv_b_expand);
        mTvBNew = (TextView) findViewById(R.id.tv_b_new);
        mTvBCount = (TextView) findViewById(R.id.tv_b_count);
        mChbBCollect = (CheckBox) findViewById(R.id.chb_b_collect);
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

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrary == null || mArrary.isEmpty())
            return 0;
        if (mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }
}
