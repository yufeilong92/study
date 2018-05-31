package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.xuechuan.xcedu.Event.EvalueTwoEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.EvalueTwoAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.EvalueModelImpl;
import com.xuechuan.xcedu.mvp.presenter.EvaluePresenter;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By
 *
 * @version V 1.0 xxxxxxx
 * @Title: EvalueTwoActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 带输入的评论
 * @author: YFL
 * @date: 2018/5/3  22:58
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/3   Inc. All rights reserved.
 * 注意：本内容仅限于XXXXXX有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class EvalueTwoActivity extends BaseActivity implements View.OnClickListener, EvalueView {

    private RecyclerView mRlvInfomtwoContent;
    private XRefreshView mXfvContentTwoDetail;
    private EditText mEtInfomTwoContent;
    private Button mBtnInfomTwoSend;
    private RelativeLayout mRlInfomTwoLayout;
    private EvaluePresenter mPresenter;
    private Context mContext;
    private AlertDialog mDialog;
    /**
     * 问题id
     */
    public static String QUESTTION = "questtion";
    /**
     * 评价id
     */
    public static String COMMONID = "commonid";
    /**
     * 评价内容
     */
    public static String EVALUEDATA = "evaluedata";
    /**
     * 刷新时间
     */
    public static long lastRefreshTime;

    private String mQuestion;
    private String mCommonid;

    private List mArray;
    private EvalueTwoAdapter adapter;
    boolean isRefresh;
    private TextView mTvEvalueEmpty;
    private EvalueVo.DatasBean mData;
    private AlertDialog mDialog1;

    public static Intent newInstance(Context context, String question, String commonid) {
        Intent intent = new Intent(context, EvalueTwoActivity.class);
        intent.putExtra(QUESTTION, question);
        intent.putExtra(COMMONID, commonid);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalue_two);
        initView();
        initData();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evalue_two);
        if (getIntent() != null) {
            mQuestion = getIntent().getStringExtra(QUESTTION);
            mCommonid = getIntent().getStringExtra(COMMONID);
        }
        initView();

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void setHeardata(EvalueTwoEvent heardata) {
        clearData();
        initData();
        initAdapter();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_two_evaluate_hear, null);
        initHearView(view, heardata.getBean());
        adapter.setHeaderView(view, mRlvInfomtwoContent);
        initXflrView();
        mXfvContentTwoDetail.startRefresh();

    }

    private void initHearView(View view, EvalueVo.DatasBean bean) {
        ImageView mIvEvaluateHear = (ImageView) view.findViewById(R.id.iv_evaluate_hear);
        TextView mTvEvalueUserName = (TextView) view.findViewById(R.id.tv_evalue_user_name);
        TextView mTvEvalueContent = (TextView) view.findViewById(R.id.tv_evalue_content);
        TextView mTvEvalueTime = (TextView) view.findViewById(R.id.tv_evalue_time);
        TextView mTvEvalueEvalue = (TextView) view.findViewById(R.id.tv_evalue_evalue);
        CheckBox mChbEvaluaIssupper = (CheckBox) view.findViewById(R.id.chb_evalua_issupper);
        TextView mTvEvalueSuppernumber = (TextView) view.findViewById(R.id.tv_evalue_suppernumber);
        View line = (View) view.findViewById(R.id.v_line_hear);
        line.setVisibility(View.VISIBLE);
        mTvEvalueUserName.setText(bean.getNickname());
        if (bean.isIssupport()) {
            mTvEvalueSuppernumber.setText(bean.getSupportcount() + "");
        } else {
            mTvEvalueSuppernumber.setText("赞");
        }
        mChbEvaluaIssupper.setChecked(bean.isIssupport());
        mTvEvalueContent.setText(bean.getContent());
        String ymdt = TimeUtil.getYMDT(bean.getCreatetime());
        String stamp = TimeSampUtil.getStringTimeStamp(bean.getCreatetime());
        L.e(stamp);
        mTvEvalueTime.setText(stamp);
        if (!StringUtil.isEmpty(bean.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvEvaluateHear, bean.getHeadicon(), true);
        }
        mTvEvalueEvalue.setText(bean.getCommentcount() + "");
    }

    private void initXflrView() {
        mXfvContentTwoDetail.restoreLastRefreshTime(lastRefreshTime);
        mXfvContentTwoDetail.setPullLoadEnable(true);
        mXfvContentTwoDetail.setAutoLoadMore(true);
        mXfvContentTwoDetail.setPullRefreshEnable(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvContentTwoDetail.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
        adapter.setClickListener(new EvalueTwoAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {

            }
        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestEvalueTwoMoreContent(mContext, getNowPage() + 1, mQuestion, mCommonid);
    }

    private void requestData() {

        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestEvalueTwoContent(mContext, 1, mQuestion, mCommonid);
    }

    private void initAdapter() {
        adapter = new EvalueTwoAdapter(mContext, mArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvInfomtwoContent.setLayoutManager(gridLayoutManager);
        mRlvInfomtwoContent.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvInfomtwoContent.setAdapter(adapter);
    }

    private void initData() {
        mPresenter = new EvaluePresenter(new EvalueModelImpl(), this);

    }

    private void initView() {
        mContext = this;
        mRlvInfomtwoContent = (RecyclerView) findViewById(R.id.rlv_infomtwo_content);
        mXfvContentTwoDetail = (XRefreshView) findViewById(R.id.xfv_content_two_detail);
        mEtInfomTwoContent = (EditText) findViewById(R.id.et_infom_two_content);
        mBtnInfomTwoSend = (Button) findViewById(R.id.btn_infom_two_send);
        mRlInfomTwoLayout = (RelativeLayout) findViewById(R.id.rl_infom_two_layout);

        mBtnInfomTwoSend.setOnClickListener(this);
        mTvEvalueEmpty = (TextView) findViewById(R.id.tv_evalue_empty);
        mTvEvalueEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_infom_two_send:
                String str = getTextStr(mEtInfomTwoContent);
                if (StringUtil.isEmpty(str)) {
                    T.showToast(mContext, getStringWithId(R.string.content_is_empty));
                    return;
                }
                // TODO: 2018/5/3 提交二级评价
                submitEvalut(str);
                mDialog1 = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                Utils.hideInputMethod(mContext, mEtInfomTwoContent);
                break;
        }
    }

    private void submitEvalut(String str) {
//        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
//        UserBean user = userInfom.getData().getUser();
     /*   EvalueVo.DatasBean bean = new EvalueVo.DatasBean();
        bean.setCommentcount(0);
        bean.setCommentid(Integer.parseInt(mCommonid));
        bean.setContent(str);
        bean.setHeadicon(user.getHeadicon());
        bean.setNickname(user.getNickname());
        bean.setTargetid(Integer.parseInt(mQuestion));
        bean.setSupportcount(0);
        List<EvalueVo.DatasBean> beans = new ArrayList<>();
        beans.add(bean);
        addListData(beans);
        adapter.notifyDataSetChanged();*/
        mEtInfomTwoContent.setText("");
        mPresenter.submitContent(mContext, mQuestion, str, mCommonid, DataMessageVo.QUESTION);
    }


    @Override
    public void submitEvalueSuccess(String con) {
        if (mDialog1 != null) {
            mDialog1.dismiss();
        }
        T.showToast(mContext, getString(R.string.evelua_sucee));
    }

    @Override
    public void submitEvalueError(String con) {
        if (mDialog1 != null) {
            mDialog1.dismiss();
        }
    }

    @Override
    public void GetTwoEvalueSuccess(String con) {
        mXfvContentTwoDetail.stopRefresh();
        isRefresh = false;
        L.e(con);
        Gson gson = new Gson();
        EvalueVo vo = gson.fromJson(con, EvalueVo.class);
        if (vo.getStatus().getCode() == 200) {
            List list = vo.getDatas();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

            if (mArray.size() < DataMessageVo.CINT_PANGE_SIZE || mArray.size() == vo.getTotal().getTotal()) {
                mXfvContentTwoDetail.setLoadComplete(true);
            } else {
                mXfvContentTwoDetail.setPullLoadEnable(true);
                mXfvContentTwoDetail.setLoadComplete(false);
            }
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void GetTwoEvalueError(String con) {
        isRefresh = false;
        mXfvContentTwoDetail.stopRefresh();
        L.e(con);
    }

    @Override
    public void GetTwoMoreEvalueSuccess(String con) {
        isRefresh = false;
        Gson gson = new Gson();
        EvalueVo vo = gson.fromJson(con, EvalueVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXfvContentTwoDetail.setLoadComplete(false);
                mXfvContentTwoDetail.setPullLoadEnable(true);
            } else {
                mXfvContentTwoDetail.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void GetTwoMoreEvalueError(String con) {
        isRefresh = false;
        mXfvContentTwoDetail.stopRefresh();
        L.e(con);
    }

    @Override
    public void GetOneEvalueSuccess(String con) {

    }

    @Override
    public void GetOneEvalueError(String con) {

    }

    @Override
    public void GetOneMoreEvalueSuccess(String con) {

    }

    @Override
    public void GetOneMoreEvalueError(String con) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
