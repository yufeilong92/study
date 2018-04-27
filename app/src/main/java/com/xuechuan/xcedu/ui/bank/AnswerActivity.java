package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.AnswerCardUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SharedPreUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.TextDetailVo;
import com.xuechuan.xcedu.vo.TitleNumberVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.HashMap;
import java.util.List;

public class AnswerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBType;
    private TextView mTvBNumber;
    private TextView mTvBMatter;
    private Context mContext;
    private TextView tv_settiong;
    /**
     * 科目编号
     */
    private static String COURSEID = "courseid";
    private ImageView mIvBMore;
    private TextView tv_share;
    private String mOid;
    private ImageView mIvBA;
    private TextView mTvBAContent;
    private ImageView mIvBB;
    private TextView mTvBBContent;
    private ImageView mIvBC;
    private TextView mTvBCContent;
    private ImageView mIvBD;
    private TextView mTvBDContent;
    private ImageView mIvBE;
    private TextView mTvBEContent;
    private LinearLayout mLlBESelect;
    private LinearLayout mLlRootLayout;
    private LinearLayout mLlBBack;
    private LinearLayout mLlBGo;
    private ImageView mIvBExpand;
    private TextView mTvBNew;
    private TextView mTvBCount;
    private CheckBox mChbBCollect;
    private LinearLayout mLlBASelect;
    private LinearLayout mLlBBSelect;
    private LinearLayout mLlBCselect;
    private LinearLayout mLlBDSelect;
    // 第几题
    private int mMark = 0;
    //题目数据
    private List<TitleNumberVo.DatasBean> mTextDetial;
    private AlertDialog dialog;
    /**
     * 保存题数据
     */
    private HashMap<Integer, TextDetailVo> hashMap;
    //用户是否点击下一题
    private boolean isNext;
    private String A = "a";
    private String B = "b";
    private String C = "c";
    private String D = "d";
    private String E = "e";
    //记录用户选中的选项
    private String item = null;
    //记录用户是否点击自动下一题
    private boolean mSelectNext;
    private String mRightItem = null;

    private String mSelectViewBgZC = "zc";
    private String mSelectViewBgHY = "hy";
    private String mSelectViewBgYJ = "yj";
    private CommonPopupWindow popMore;
    private CommonPopupWindow popSetting;
    private TextView mTvSettring;
    private TextView mTvShare;

    private CheckBox mSwtNext;
    private RadioGroup mRgSetType;
    private TextView mTvBAnsewer;
    private TextView mTvBRosoleContent;
    private LinearLayout mLlBResolveResult;
    private Button mBtnBBuy;
    private LinearLayout mLiBResolveBuy;
    private TextView mTvBAnswer;
    private TextView mTvBAccuracy;

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initView();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_answer);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
        }
        SharedPreUtil.initSharedPreference(this);
        initView();
        initData();
    }

    private void initData() {
        requestMatter();
    }

    //请求题干-获取题干详情
    private void requestMatter() {
        BankService bankService = new BankService(mContext);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        bankService.requestChapterQuestionids(mOid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String msg = response.body().toString();
                Gson gson = new Gson();
                TitleNumberVo vo = gson.fromJson(msg, TitleNumberVo.class);
                if (vo.getStatus().getCode() == 200) {
                    mTextDetial = vo.getDatas();
                    mTvBCount.setText(mTextDetial.size());
                    bindTextNumberData();
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

    private void bindTextNumberData() {
        if (mTextDetial != null) {

            if (mMark < 0) {
                T.showToast(mContext, "已经是第一题");
                return;
            }
            mTvBNew.setText(mMark);
            if (mMark < mTextDetial.size()) {//是否有题
                TitleNumberVo.DatasBean bean = mTextDetial.get(mMark);
                requestDTextData(bean);
            } else {//没有题了
                T.showToast(mContext, "已经是最后一题 ");
            }
        }


    }

    /**
     * 请求数据
     *
     * @param bean
     */
    private void requestDTextData(TitleNumberVo.DatasBean bean) {
        BankService service = new BankService(mContext);
        service.requestDetail(String.valueOf(bean.getId()), new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                String message = response.body().toString();
                L.d("题库详情", message);
                Gson gson = new Gson();
                TextDetailVo vo = gson.fromJson(message, TextDetailVo.class);
                if (vo.getStatus().getCode() == 200) {
                    if (hashMap != null) {//保存数据
                        hashMap.put(mMark, vo);
                    } else {
                        hashMap = new HashMap<>();
                        hashMap.put(mMark, vo);
                    }
                    bindViewData(vo.getData());

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
     * todo 界面背景选择
     */
    private void selectViewBg() {
        //用户选中是否护眼/夜间/正常
        if (mSelectViewBgZC.equals(mSelectViewBgHY)) {//护眼

        } else if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {//夜间

        }
    }

    private void initView() {
        mTvBType = (TextView) findViewById(R.id.tv_b_type);
        mTvBNumber = (TextView) findViewById(R.id.tv_b_number);
        mTvBMatter = (TextView) findViewById(R.id.tv_b_matter);
        mContext = this;
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mIvBMore.setOnClickListener(this);
        mIvBA = (ImageView) findViewById(R.id.iv_b_a);
        mTvBAContent = (TextView) findViewById(R.id.tv_b_a_content);
        mIvBB = (ImageView) findViewById(R.id.iv_b_b);
        mTvBBContent = (TextView) findViewById(R.id.tv_b_b_content);
        mIvBC = (ImageView) findViewById(R.id.iv_b_c);
        mTvBCContent = (TextView) findViewById(R.id.tv_b_c_content);
        mIvBD = (ImageView) findViewById(R.id.iv_b_d);
        mTvBDContent = (TextView) findViewById(R.id.tv_b_d_content);
        mIvBE = (ImageView) findViewById(R.id.iv_b_e);
        mTvBEContent = (TextView) findViewById(R.id.tv_b_e_content);

        mLlRootLayout = (LinearLayout) findViewById(R.id.ll_root_layout);
        mLlRootLayout.setOnClickListener(this);
        mLlBBack = (LinearLayout) findViewById(R.id.ll_b_back);
        mLlBBack.setOnClickListener(this);
        mLlBGo = (LinearLayout) findViewById(R.id.ll_b_go);
        mLlBGo.setOnClickListener(this);
        mIvBExpand = (ImageView) findViewById(R.id.iv_b_expand);
        mIvBExpand.setOnClickListener(this);
        mTvBNew = (TextView) findViewById(R.id.tv_b_new);
        mTvBNew.setOnClickListener(this);
        mTvBCount = (TextView) findViewById(R.id.tv_b_count);
        mTvBCount.setOnClickListener(this);
        mChbBCollect = (CheckBox) findViewById(R.id.chb_b_collect);
        mChbBCollect.setOnClickListener(this);
        mLlBASelect = (LinearLayout) findViewById(R.id.ll_b_a_select);
        mLlBASelect.setOnClickListener(this);
        mLlBBSelect = (LinearLayout) findViewById(R.id.ll_b_b_select);
        mLlBBSelect.setOnClickListener(this);
        mLlBCselect = (LinearLayout) findViewById(R.id.ll_b_cselect);
        mLlBCselect.setOnClickListener(this);
        mLlBDSelect = (LinearLayout) findViewById(R.id.ll_b_d_select);
        mLlBDSelect.setOnClickListener(this);
        mLlBESelect = (LinearLayout) findViewById(R.id.ll_b_e_select);
        mLlBESelect.setOnClickListener(this);
        mTvBRosoleContent = (TextView) findViewById(R.id.tv_b_rosole_content);
        mTvBRosoleContent.setOnClickListener(this);
        mLlBResolveResult = (LinearLayout) findViewById(R.id.ll_b_resolve_result);
        mLlBResolveResult.setOnClickListener(this);
        mBtnBBuy = (Button) findViewById(R.id.btn_b_buy);
        mBtnBBuy.setOnClickListener(this);
        mLiBResolveBuy = (LinearLayout) findViewById(R.id.li_b_resolve_buy);
        mLiBResolveBuy.setOnClickListener(this);
        mTvBAnswer = (TextView) findViewById(R.id.tv_b_answer);
        mTvBAnswer.setOnClickListener(this);
        mTvBAccuracy = (TextView) findViewById(R.id.tv_b_accuracy);
        mTvBAccuracy.setOnClickListener(this);
    }

    private void bindViewData(TextDetailVo.DataBean data) {
        //判断用户是否购买 -》购买

        //判断问题类型单选/多选->提供选择处理

        // -》未购买不展示解析
        //判断用户是否做过，是否展示解析


        //用户是否选择错误
        //题干类型是否是多选


        mTvBType.setText(AnswerCardUtil.getTextType(data.getQuestiontype()));
        mTvBMatter.setText(data.getQuestion());
        mTvBAContent.setText(data.getA());
        mTvBBContent.setText(data.getB());
        mTvBCContent.setText(data.getC());
        mTvBDContent.setText(data.getD());
        if (StringUtil.isEmpty(data.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(data.getE());
        }

        mTvBAnswer.setText(data.getChoiceanswer());
        mTvBRosoleContent.setText(data.getAnalysis());
        mTvBAccuracy.setText(data.getAccuracy());
        mChbBCollect.setChecked(data.isIsfav());
        //正确答案
        mRightItem = data.getChoiceanswer();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_b_more://设置
                showPopwindow();
                break;
            case R.id.ll_b_back://上一题
                //多选

                //单选
                //读取上一题结果 更具结果展示
                //判断用户是否做
                //保存做题结果
                //判断是否正确
                //正确 -显示解析

                //错误 -显示解析

                //判断是否做过
                --mMark;
                bindTextNumberData();
                break;
            case R.id.ll_b_go://下一题
                //保存做题结果
                //判断用户是否做
                //判断是否正确
                //正确 -显示解析
                //错误 -显示解析


                ++mMark;
                bindTextNumberData();
                break;
            case R.id.iv_b_expand://扩展文件夹
                break;
            case R.id.chb_b_collect://收藏
                break;
            case R.id.ll_b_a_select://选择a
                item = A;
                //单选/多选
                setSelectItemBG(true, false, false, false, false, false);
                break;
            case R.id.ll_b_b_select://选择b
                item = B;
                //单选/多选
                setSelectItemBG(false, true, false, false, false, false);
                break;
            case R.id.ll_b_cselect://选择c
                item = C;
                //单选/多选
                setSelectItemBG(false, false, true, false, false, false);
                break;
            case R.id.ll_b_d_select://选择d
                item = D;
                //单选/多选
                setSelectItemBG(false, false, false, true, false, false);
                break;
            case R.id.ll_b_e_select://选择e
                item = E;
                //单选/多选
                setSelectItemBG(false, false, false, false, true, false);
                break;
            default:
            case R.id.btn_b_buy://购买

                break;
        }
    }

    /**
     * 显示pop
     */
    private void showPopwindow() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        // create popup window
        popMore = new CommonPopupWindow(this, R.layout.popw_more_layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                mTvSettring = view.findViewById(R.id.tv_popu_setting);
                mTvShare = view.findViewById(R.id.tv_popi_share);
            }

            @Override
            protected void initEvent() {
                mTvSettring.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupWindow popupWindow = popMore.getPopupWindow();
                        popupWindow.dismiss();
                        showSettring();
                        Toast.makeText(AnswerActivity.this, "2222", Toast.LENGTH_SHORT).show();
                    }
                });
                mTvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupWindow popupWindow = popMore.getPopupWindow();
                        popupWindow.dismiss();
                        Toast.makeText(AnswerActivity.this, "1111", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popMore.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }


    //设置布局
    private void showSettring() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        // create popup window
        popSetting = new CommonPopupWindow(this, R.layout.pop_item_setting, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                mRgSetType = view.findViewById(R.id.rg_setting_type);
                mSwtNext = view.findViewById(R.id.swt_select_next);
            }

            @Override
            protected void initEvent() {
                mRgSetType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rdb_setting_eye://护眼
                                mSelectViewBgZC = mSelectViewBgHY;
                                Toast.makeText(AnswerActivity.this, "护眼", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.rdb_setting_night://夜间
                                mSelectViewBgZC = mSelectViewBgYJ;
                                Toast.makeText(AnswerActivity.this, "夜间", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                mSwtNext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSelectNext = isChecked;
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setBackgroundAlpha(1f, AnswerActivity.this);
                    }
                });
            }
        };
        popSetting.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);

    }


    /**
     * 设置背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);

    }


    private void setSelectItemBG(boolean a, boolean b, boolean c, boolean d, boolean e, boolean day) {
        setImgBg(mIvBA, a, R.drawable.ic_b_a_s, R.drawable.ic_b_a_n);
        setImgBg(mIvBB, b, R.drawable.ic_b_b_s, R.drawable.ic_b_b_n);
        setImgBg(mIvBC, c, R.drawable.ic_b_c_s, R.drawable.ic_b_c_n);
        setImgBg(mIvBD, d, R.drawable.ic_b_d_s, R.drawable.ic_b_d_n);
        setImgBg(mIvBE, e, R.drawable.ic_b_e_s, R.drawable.ic_b_e_n);
        setLibg(mLlBASelect, a);
        setLibg(mLlBBSelect, b);
        setLibg(mLlBCselect, c);
        setLibg(mLlBDSelect, d);
        setLibg(mLlBESelect, e);
    }

    private void setResultItemBG(boolean a, boolean b, boolean c, boolean d, boolean e, boolean day) {
        setImgBg(mIvBA, a, R.drawable.ic_b_a_s, R.drawable.ic_b_a_n);
        setImgBg(mIvBB, b, R.drawable.ic_b_b_s, R.drawable.ic_b_b_n);
        setImgBg(mIvBC, c, R.drawable.ic_b_c_s, R.drawable.ic_b_c_n);
        setImgBg(mIvBD, d, R.drawable.ic_b_d_s, R.drawable.ic_b_d_n);
        setImgBg(mIvBE, e, R.drawable.ic_b_e_s, R.drawable.ic_b_e_n);
        setLibg(mLlBASelect, a);
        setLibg(mLlBBSelect, b);
        setLibg(mLlBCselect, c);
        setLibg(mLlBDSelect, d);
        setLibg(mLlBESelect, e);
    }

    private void setImgBg(ImageView iv, boolean is, int selectid, int unselectid) {
        if (is) {
            iv.setImageDrawable(mContext.getResources().getDrawable(selectid));
        } else {
            iv.setImageDrawable(mContext.getResources().getDrawable(unselectid));
        }
    }

    private void setLibg(LinearLayout la, boolean is) {
        if (is) {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
        } else {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }
}

