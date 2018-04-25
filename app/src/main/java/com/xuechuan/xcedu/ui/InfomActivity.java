package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.HomeEvaluateAdapter;
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

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: InfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 详情信息页
 * @author: L-BackPacker
 * @date: 2018/4/19 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class InfomActivity extends BaseActivity implements View.OnClickListener {
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

    private WebView mWebInfom;
    private RelativeLayout mRlvInfomShow;

    /**
     * 地址
     */
    private static String URLPARAM = "urlparam";
    private String mUrl;
    private WebView mWbInfom;
    private RecyclerView mRlvInfomComment;
    private EditText mEtInfomContent;
    private Button mBtnInfomSend;
    private RelativeLayout mRlInfomContent;
    private Context mContext;
    private String mTargetid;
    private CheckBox mChbbox;
    private String mType;
    private XRefreshView mXfvContent;
    private List mArray;
    private boolean isRefresh = false;
    long lastRefreshTime;
    private HomeEvaluateAdapter adapter;
    private String mSupperNumber;
    private TextView mTvSupperNumber;
    private CheckBox mChbIofomSupper;
    private TextView mTvIofomSuppernumber;
    private LinearLayout mLiSupperNumber;
    private TextView mTvInfomAppraisenumber;

    public static void newInstance(Context context, String url) {
        Intent intent = new Intent(context, InfomActivity.class);
        intent.putExtra(URLPARAM, url);
        context.startActivity(intent);
    }

    public static Intent startInstance(Context context, String url, String id, String usertype) {
        Intent intent = new Intent(context, InfomActivity.class);
        intent.putExtra(URLPARAM, url);
        intent.putExtra(MOID, id);
        intent.putExtra(MTYPE, usertype);
        return intent;
    }

    public static Intent startInstance(Context context, String url, String id, String usertype, String supper) {
        Intent intent = new Intent(context, InfomActivity.class);
        intent.putExtra(URLPARAM, url);
        intent.putExtra(MOID, id);
        intent.putExtra(MTYPE, usertype);
        intent.putExtra(SUPPER, supper);
        return intent;
    }


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infom);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_infom);
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(URLPARAM);
            mTargetid = getIntent().getStringExtra(MOID);
            mType = getIntent().getStringExtra(MTYPE);
            mSupperNumber = getIntent().getStringExtra(SUPPER);

        }
        initView();
        initVebView();
        initData();
        clearData();
        bindAdapterData();
        mXfvContent.startRefresh();
    }

    private void initVebView() {
        WebSettings settings = mWebInfom.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        settings.setTextSize(WebSettings.TextSize.LARGER);
        mWebInfom.loadUrl(mUrl);
        mWebInfom.setWebChromeClient(new WebChromeClient());
        mWebInfom.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRlvInfomShow.setVisibility(View.VISIBLE);
                mRlInfomContent.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRlvInfomShow.setVisibility(View.GONE);
                mRlInfomContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }


    private void initView() {
        mWebInfom = (WebView) findViewById(R.id.web_infom);
        mRlvInfomShow = (RelativeLayout) findViewById(R.id.rlv_infom_show);
        mRlvInfomComment = (RecyclerView) findViewById(R.id.rlv_infom_comment);
        mRlvInfomComment.setOnClickListener(this);
        mEtInfomContent = (EditText) findViewById(R.id.et_infom_content);
        mEtInfomContent.setOnClickListener(this);
        mBtnInfomSend = (Button) findViewById(R.id.btn_infom_send);
        mBtnInfomSend.setOnClickListener(this);
        mRlInfomContent = (RelativeLayout) findViewById(R.id.rl_infom_content);
        mRlInfomContent.setOnClickListener(this);
        mChbbox = (CheckBox) findViewById(R.id.chb_iofom_supper);
        mXfvContent = (XRefreshView) findViewById(R.id.xfv_content);
        mTvSupperNumber = findViewById(R.id.tv_iofom_suppernumber);
        mContext = this;
        mChbIofomSupper = (CheckBox) findViewById(R.id.chb_iofom_supper);
        mChbIofomSupper.setOnClickListener(this);
        mTvIofomSuppernumber = (TextView) findViewById(R.id.tv_iofom_suppernumber);
        mTvIofomSuppernumber.setOnClickListener(this);
        mLiSupperNumber = (LinearLayout) findViewById(R.id.li_supperNumber);
        mLiSupperNumber.setOnClickListener(this);
        mTvInfomAppraisenumber = (TextView) findViewById(R.id.tv_infom_appraisenumber);
        mTvInfomAppraisenumber.setOnClickListener(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebInfom.canGoBack()) {
            mWebInfom.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
        if (StringUtil.isEmpty(mSupperNumber)) {
            mLiSupperNumber.setVisibility(View.GONE);
        } else {
            mLiSupperNumber.setVisibility(View.VISIBLE);
        }
        mChbbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        mTvSupperNumber.setText(mSupperNumber);
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
                submitEvaluate(textStr);
                break;
        }
    }

    /**
     * 提交评价
     *
     * @param content
     */
    private void submitEvaluate(final String content) {
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
                    EvalueVo.DatasBean evalue = getEvalue();
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


    private void bindAdapterData() {
        adapter = new HomeEvaluateAdapter(mContext, mArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvInfomComment.setLayoutManager(gridLayoutManager);
        mXfvContent.restoreLastRefreshTime(lastRefreshTime);
        mRlvInfomComment.setAdapter(adapter);
        mXfvContent.setPullLoadEnable(true);
        mXfvContent.setAutoLoadMore(true);
        mXfvContent.setPullRefreshEnable(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestEvalueData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });

    }



    private void requestEvalueData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        if (StringUtil.isEmpty(mTargetid)) {
            mXfvContent.stopRefresh();
            return;
        }
        HomeService bankService = new HomeService(mContext);
        bankService.requestArticleCommentList(mTargetid, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXfvContent.stopRefresh();
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
                    mXfvContent.setPullLoadEnable(true);
                    mXfvContent.setLoadComplete(false);
                    adapter.notifyDataSetChanged();

                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                T.showToast(mContext, response.message());
            }
        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = false;
        if (StringUtil.isEmpty(mTargetid)) {
            return;
        }
        HomeService bankService = new HomeService(mContext);
        bankService.requestArticleCommentList(mTargetid, getNowPage() + 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
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
                isRefresh = false;
                T.showToast(mContext, response.message());
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

    private EvalueVo.DatasBean getEvalue() {
        return new EvalueVo.DatasBean();
    }
}
