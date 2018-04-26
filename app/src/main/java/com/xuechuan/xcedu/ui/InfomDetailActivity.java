package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.InfomDetailAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfomDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 资讯id
     */
    private static final String MOID = "oid";
    /**
     * 类型
     */
    private static final String MTYPE = "USERTYPE";
    /**
     * 用户赞
     */
    private static final String SUPPER = "supper";

    /**
     * 地址
     */
    private static String URLPARAM = "urlparam";
    /**
     * 数据
     */
    private List mArray;
    private RecyclerView mRlvInfomdetailContent;
    private XRefreshView mXfvContent;
    private String mUrl;
    private String mTargetid;
    private String mType;
    private String mSupperNumber;
    private Context mContext;
    private InfomDetailAdapter adapter;
    private XRefreshView mXfvContentDetail;
    private EditText mEtInfomContent;
    private Button mBtnInfomSend;

    public static void newInstance(Context context, String url) {
        Intent intent = new Intent(context, InfomDetailActivity.class);
        intent.putExtra(URLPARAM, url);
        context.startActivity(intent);
    }

    public static Intent startInstance(Context context, String url, String id, String usertype) {
        Intent intent = new Intent(context, InfomDetailActivity.class);
        intent.putExtra(URLPARAM, url);
        intent.putExtra(MOID, id);
        intent.putExtra(MTYPE, usertype);
        return intent;
    }

    /***
     *
     * @param context
     * @param url 网址
     * @param id  targid
     * @param usertype 类型
     * @param supper 点赞数
     * @return
     */
    public static Intent startInstance(Context context, String url, String id, String usertype, String supper) {
        Intent intent = new Intent(context, InfomDetailActivity.class);
        intent.putExtra(URLPARAM, url);
        intent.putExtra(MOID, id);
        intent.putExtra(MTYPE, usertype);
        intent.putExtra(SUPPER, supper);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infom_detail);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(URLPARAM);
            mTargetid = getIntent().getStringExtra(MOID);
            mType = getIntent().getStringExtra(MTYPE);
            mSupperNumber = getIntent().getStringExtra(SUPPER);

        }
        clearData();
        initView();
        reqesstEvaleData();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_infom_detail);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(URLPARAM);
            mTargetid = getIntent().getStringExtra(MOID);
            mType = getIntent().getStringExtra(MTYPE);
            mSupperNumber = getIntent().getStringExtra(SUPPER);

        }
        clearData();
        initView();
        reqesstEvaleData();
    }


    private void reqesstEvaleData() {
        if (StringUtil.isEmpty(mTargetid)) {
            return;
        }
        HomeService bankService = new HomeService(mContext);
        bankService.setIsShowDialog(true);
        bankService.setDialogContext(mContext, "", getStringWithId(R.string.loading));
        bankService.requestArticleCommentList(mTargetid, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                EvalueVo vo = gson.fromJson(message, EvalueVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<EvalueVo.DatasBean> datas = vo.getDatas();
                    clearData();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    } else {
                        mXfvContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                T.showToast(mContext, response.message());
            }
        });
    }

    private void initView() {
        mContext = this;
        mXfvContentDetail = (XRefreshView) findViewById(R.id.xfv_content_detail);
        mXfvContentDetail.setOnClickListener(this);
        mEtInfomContent = (EditText) findViewById(R.id.et_infom_content);
        mEtInfomContent.setOnClickListener(this);
        mBtnInfomSend = (Button) findViewById(R.id.btn_infom_send);
        mBtnInfomSend.setOnClickListener(this);
        mRlvInfomdetailContent = (RecyclerView) findViewById(R.id.rlv_infomdetail_content);
        mXfvContent = (XRefreshView) findViewById(R.id.xfv_content_detail);
        mXfvContent.setPullLoadEnable(true);
        mXfvContent.setAutoLoadMore(true);
        mXfvContent.setPullRefreshEnable(false);
        mXfvContent.setMoveForHorizontal(true);
        bindAdapter();


    }

    private void bindAdapter() {
        adapter = new InfomDetailAdapter(mContext, mArray);
        //头
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.item_infom_webview, null);
        adapter.setHeaderView(view, mRlvInfomdetailContent);
        initWebView(view);
        bindHearData(view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvInfomdetailContent.setLayoutManager(layoutManager);
        mRlvInfomdetailContent.setAdapter(adapter);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }
        });
        adapter.setClickListener(new InfomDetailAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                EvalueVo.DatasBean vo = (EvalueVo.DatasBean) obj;
                Intent intent = EvalueDetialActivity.newInstance(mContext, String.valueOf(vo.getTargetid()),
                        String.valueOf(vo.getCommentid()));
                mContext.startActivity(intent);
            }
        });

    }

    private void loadMoreData() {
        if (StringUtil.isEmpty(mTargetid)) {
            return;
        }
        HomeService bankService = new HomeService(mContext);
        bankService.requestArticleCommentList(mTargetid, getNowPage() + 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                EvalueVo vo = gson.fromJson(message, EvalueVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<EvalueVo.DatasBean> datas = vo.getDatas();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    } else {
                        mXfvContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXfvContent.setLoadComplete(false);
                        mXfvContent.setPullLoadEnable(true);
                    } else {
                        mXfvContent.setLoadComplete(true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                T.showToast(mContext, response.message());
            }
        });
    }

    /**
     * 绑定头布局
     *
     * @param view
     */
    private void bindHearData(View view) {
        CheckBox chb_select = view.findViewById(R.id.chb_iofom_detial_supper);
        LinearLayout mliSupper = view.findViewById(R.id.li_supperNumber);
        TextView tvNumber = view.findViewById(R.id.tv_iofom_detail_suppernumber);
        TextView tvHearNumber = view.findViewById(R.id.tv_h_evalue);
        if (!StringUtil.isEmpty(mSupperNumber)) {
            tvHearNumber.setVisibility(View.VISIBLE);
            tvHearNumber.setText("评论(" + mSupperNumber + ")");
        } else {
            tvHearNumber.setVisibility(View.GONE);
        }
        if (StringUtil.isEmpty(mSupperNumber)) {
            mliSupper.setVisibility(View.GONE);
        } else {
            mliSupper.setVisibility(View.VISIBLE);
        }
        chb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                L.e(isChecked + "是否点赞");
                if (isChecked) {
                    requestSupper("true");
                } else {
                    requestSupper("false");
                }
            }
        });
        tvNumber.setText(mSupperNumber);
    }

    /**
     * c初始化web
     *
     * @param view
     */
    private void initWebView(View view) {
        WebView webview = view.findViewById(R.id.web_infom_detail);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webview.loadUrl(mUrl);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }
        });
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
     * 点赞
     *
     * @param isSupper
     */
    private void requestSupper(String isSupper) {
        if (StringUtil.isEmpty(mTargetid)) {
            return;
        }
        CurrencyService currencyService = new CurrencyService(mContext);
        currencyService.subimtSpport(mTargetid, isSupper, mType, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                L.w(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_infom_send:
                String textStr = getTextStr(mEtInfomContent);
                if (StringUtil.isEmpty(textStr)) {
                    T.showToast(mContext, getString(R.string.content_is_empty));
                    return;
                }
                submit(textStr);
                break;
        }
    }

    private void submit(final String content) {
        CurrencyService currencyService = new CurrencyService(mContext);
        currencyService.setIsShowDialog(true);
        currencyService.setDialogContext(mContext, "", getString(R.string.submit));
        currencyService.submitConmment(mTargetid, content, null, DataMessageVo.USERTYOEARTICLE, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.d(message);
                Gson gson = new Gson();
                BaseVo vo = gson.fromJson(message, BaseVo.class);
                if (vo.getStatus().getCode() == 200) {
                    T.showToast(mContext, "提交成功");
                    UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
                    EvalueVo.DatasBean evalue = new EvalueVo.DatasBean();
                    evalue.setContent(content);
                    String s = TimeUtil.dateToString(new Date());
                    evalue.setCreatetime(s);
                    UserBean user = userInfom.getData().getUser();
                    evalue.setNickname(user.getNickname());
                    evalue.setHeadicon(user.getHeadicon());
                    evalue.setMemberid(user.getId());
                    List<EvalueVo.DatasBean> beans = new ArrayList<>();
                    beans.add(evalue);
                    addListData(beans);
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {

            }
        });
    }
}
