package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.umeng.debug.log.D;
import com.xuechuan.xcedu.Event.VideoIdEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.NetMyBookEvaleAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetVideoEvalueModelImple;
import com.xuechuan.xcedu.mvp.presenter.NetVideoEvaluePresenter;
import com.xuechuan.xcedu.mvp.view.NetVideoEvalueView;
import com.xuechuan.xcedu.ui.EvalueTwoActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.SuppertUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.EvalueInfomVo;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.ResultVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetMyBookVualueFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description 我的课程评价
 * @author: L-BackPacker
 * @date: 2018/5/16 14:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/16
 */
public class NetMyBookVualueFragment extends BaseFragment implements View.OnClickListener, NetVideoEvalueView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRlvSpecaContent;
    private XRefreshView mXrfvSpecaRefresh;
    private boolean isRefresh;
    private Context mContext;
    private List mArrary;
    private TextView mTvNetEmptyContent;
    private long lastRefreshTime;
    private NetVideoEvaluePresenter mPresenter;
    private String mVideoId;
    private TextView mEtNetBookEvalue;
    private ImageView mIvNetBookSend;
    private NetMyBookEvaleAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    public NetMyBookVualueFragment() {
    }

    public static NetMyBookVualueFragment newInstance(String param1, String param2) {
        NetMyBookVualueFragment fragment = new NetMyBookVualueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainVideoEvale(VideoIdEvent event) {
        mVideoId = event.getVideoId();
        loadNewData();
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_my_book_vualue, container, false);
        initView(view);
        return view;
    }*/


    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_my_book_vualue;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        clearData();
        bindAdapterData();
        initXrfresh();
//        loadNewData();
    }

    private void initData() {
        mPresenter = new NetVideoEvaluePresenter(new NetVideoEvalueModelImple(), this);

    }


    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestVideoEvalue(mContext, mVideoId, 1);

    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new NetMyBookEvaleAdapter(mContext, mArrary);
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);
        adapter.setClickListener(new NetMyBookEvaleAdapter.onItemClickListener() {
            @Override
            public void onClickListener(EvalueVo.DatasBean vo, int position) {
                Intent intent = EvalueTwoActivity.newInstance(mContext, String.valueOf(vo.getTargetid()),
                        String.valueOf(vo.getId()), DataMessageVo.VIDEO);
                startActivity(intent);
            }
        });
        adapter.setChbClickListener(new NetMyBookEvaleAdapter.onItemChbClickListener() {
            @Override
            public void onClickChbListener(EvalueVo.DatasBean bean, boolean isChick, int position) {
                EvalueVo.DatasBean vo = (EvalueVo.DatasBean) mArrary.get(position);
                SuppertUtil suppertUtil = SuppertUtil.getInstance(mContext);
                vo.setIssupport(isChick);
                if (isChick) {
                    vo.setSupportcount(vo.getSupportcount() + 1);
                    suppertUtil.submitSupport(String.valueOf(bean.getTargetid()), "true", DataMessageVo.USERTYPEVC);
                    adapter.notifyDataSetChanged();
                } else {
                    vo.setSupportcount(vo.getSupportcount() - 1);
                    suppertUtil.submitSupport(String.valueOf(bean.getTargetid()), "false", DataMessageVo.USERTYPEVC);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(false);
        mXrfvSpecaRefresh.setEmptyView(mTvNetEmptyContent);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }


            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });


    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        mPresenter.requestMoreVideoEvalue(mContext, mVideoId, getNowPage() + 1);
    }

    private void initView(View view) {
        mContext = getActivity();
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) view.findViewById(R.id.xrfv_speca_refresh);
        mTvNetEmptyContent = (TextView) view.findViewById(R.id.tv_net_empty_content);
        mIvNetBookSend = (ImageView) view.findViewById(R.id.iv_net_book_send);
        mIvNetBookSend.setOnClickListener(this);
        mTvNetEmptyContent.setOnClickListener(this);
        mEtNetBookEvalue = (TextView) view.findViewById(R.id.et_net_book_evalue);
        mEtNetBookEvalue.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_net_book_evalue://发送
                if (StringUtil.isEmpty(mVideoId)) {
                    T.showToast(mContext, "请选择播放视频，再来评价");
                    return;
                }
                showInputDialog();
                break;
            default:

        }
    }

    private EditText mEtDialogContent;
    private Button mBtnInputCancle;
    private Button mBtnInputSure;

    /**
     * 显示输入框内容
     */
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_inpout, null);
        mEtDialogContent = view.findViewById(R.id.et_dialog_content);
        mBtnInputCancle = view.findViewById(R.id.btn_input_cancle);
        mBtnInputSure = view.findViewById(R.id.btn_input_sure);
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
        mBtnInputSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvalue(getTextStr(mEtDialogContent));
                dialog.dismiss();
            }
        });
        mBtnInputCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void submitEvalue(String str) {
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStrWithId(R.string.content_is_empty));
            return;
        }
        mPresenter.submitEvalue(mContext, mVideoId, str, null);
    }

    @Override
    public void EvalueOneSuccess(String con) {
        mXrfvSpecaRefresh.stopRefresh();
        isRefresh = false;
        L.e("视频评价" + con);
        Gson gson = new Gson();
        EvalueInfomVo vo = gson.fromJson(con, EvalueInfomVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
            clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }

            if (mArrary.size() < DataMessageVo.CINT_PANGE_SIZE || mArrary.size() == vo.getTotal().getTotal()) {
                mXrfvSpecaRefresh.setLoadComplete(true);
            } else {
                mXrfvSpecaRefresh.setPullLoadEnable(true);
                mXrfvSpecaRefresh.setLoadComplete(false);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(vo.getStatus().getMessage());
        }

    }

    @Override
    public void EvalueOneError(String rror) {
        isRefresh = false;
    }

    @Override
    public void EvalueMoreSuccess(String con) {
        isRefresh = false;
        L.e("视频评价vodie" + con);
        L.e(getNowPage() + "集合长度" + mArrary.size());
        Gson gson = new Gson();
        EvalueInfomVo vo = gson.fromJson(con, EvalueInfomVo.class);
        if (vo.getStatus().getCode() == 200) {//成功
            List list = vo.getDatas();
//                    clearData();
            if (list != null && !list.isEmpty()) {
                addListData(list);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
                adapter.notifyDataSetChanged();
                return;
            }
            //判断是否能整除
            if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mXrfvSpecaRefresh.setLoadComplete(false);
                mXrfvSpecaRefresh.setPullLoadEnable(true);
            } else {
                mXrfvSpecaRefresh.setLoadComplete(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            isRefresh = false;
            L.e(vo.getStatus().getMessage());
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void EvalueMoreError(String rror) {
        isRefresh = false;
    }

    @Override
    public void SubmitEvalueSuccess(String con) {
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, getString(R.string.evelua_sucee));
            mEtDialogContent.setText(null);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//            T.showToast(mContext, vo.getStatus().getMessage());
        }
        L.d("视频评价" + con);
    }

    @Override
    public void SubmitEvalueError(String con) {
        L.e("视频评价" + con);
    }


}
