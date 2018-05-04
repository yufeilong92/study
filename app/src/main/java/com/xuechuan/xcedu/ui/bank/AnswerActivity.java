package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AnswerEvaluateAdapter;
import com.xuechuan.xcedu.adapter.AnswerTableAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.AnswerModelImpl;
import com.xuechuan.xcedu.mvp.model.EvalueModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AnswerPresenter;
import com.xuechuan.xcedu.mvp.presenter.EvaluePresenter;
import com.xuechuan.xcedu.mvp.view.AnswerView;
import com.xuechuan.xcedu.mvp.view.EvalueView;
import com.xuechuan.xcedu.utils.AdvancedCountdownTimer;
import com.xuechuan.xcedu.utils.AnswerCardUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.TextDetailVo;
import com.xuechuan.xcedu.vo.TitleNumberVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;
import com.xuechuan.xcedu.vo.UserbuyOrInfomVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.SmartScrollView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnswerActivity extends BaseActivity implements View.OnClickListener, AnswerView, EvalueView {

    private Context mContext;
    /**
     * 科目编号
     */
    private static String COURSEID = "courseid";

    private String mOid;

    // 第几题
    private int mMark = 0;
    //题目数据
    private List<TitleNumberVo.DatasBean> mTextDetial;
    private AlertDialog dialog;

    //用户是否点击下一题
    private boolean isNext;
    private String A = "a";
    private String B = "b";
    private String C = "c";
    private String D = "d";
    private String E = "e";
    //记录用户选中的选项 （用于单选）
    private String mSelectOnlyitem = null;
    //多选状况
    private String mSelectMorItemA = null;
    private String mSelectMorItemB = null;
    private String mSelectMorItemC = null;
    private String mSelectMorItemD = null;
    private String mSelectMorItemE = null;
    //记录用户是否点击选项
    private boolean isClickA;
    private boolean isClickB;
    private boolean isClickC;
    private boolean isClickD;
    private boolean isClickE;
    //用户是否确认多选
    private boolean isSure = true;
    //记录用户是否点击自动下一题
    private boolean mSelectNext;
    //用户选中的item
    private String mRightItem = null;
    //问题题干类型
    private String mTitleType = null;
    private final String mTitleTypeOnly = "only";
    private final String mTitleTypeMore = "more";
    private final String mTitleTypeWrite = "write";
    //评价数据集合
    private List mArray;

    private String mSelectViewBgZC = "zc";
    public static final String mSelectViewBgHY = "hy";
    public static final String mSelectViewBgYJ = "yj";
    private CommonPopupWindow popMore;
    private CommonPopupWindow popSetting;
    private CommonPopupWindow popAnswer;

    private CheckBox mSwtNext;
    //用户是否提交
    private boolean isSubmit;
    /**
     * 当前题干信息
     */
    private TextDetailVo mTextDetialNew;
    /**
     * 用户选择结果
     */
    private ArrayList<UseSelectItemInfomVo> mSeletList;
    //剪切答案集合
    private ArrayList<String> list;
    private AnswerPresenter mPresnter;
    private AnswerEvaluateAdapter adapter;
    private ImageView mIvBMore;
    private TextView mTvRootEmpty;
    private TextView mTvBType;
    private TextView mTvBMatter;
    private ImageView mIvBA;
    private TextView mTvBAContent;
    private LinearLayout mLlBASelect;
    private ImageView mIvBB;
    private TextView mTvBBContent;
    private LinearLayout mLlBBSelect;
    private ImageView mIvBC;
    private TextView mTvBCContent;
    private LinearLayout mLlBCselect;
    private ImageView mIvBD;
    private TextView mTvBDContent;
    private LinearLayout mLlBDSelect;
    private ImageView mIvBE;
    private TextView mTvBEContent;
    private LinearLayout mLlBESelect;
    private Button mBtnBSureKey;
    private TextView mTvBAnswer;
    private LinearLayout mLlBAnswerKey;
    private TextView mTvBRosoleContent;
    private LinearLayout mLlBJiexi;
    private TextView mTvBAccuracy;
    private LinearLayout mLlBRightLu;
    private Button mBtnBBuy;
    private LinearLayout mLiBResolveBuy;
    private RecyclerView mRlvEualeContent;
    private SmartScrollView mSlvRootShow;
    private LinearLayout mLlBBack;
    private LinearLayout mLlBGo;
    private ImageView mIvBExpand;
    private TextView mTvBNew;
    private TextView mTvBCount;
    private CheckBox mChbBCollect;
    private LinearLayout mLlRootLayout;

    private TextView mTvPopNew;
    private TextView mTvPopCount;
    private RecyclerView mRlvPopContent;
    private Button mBtnSubmit;
    //当前结果
    private TextDetailVo.DataBean mResultData;
    private ImageView mIvBStar1;
    private ImageView mIvBStar2;
    private ImageView mIvBStar3;
    private ImageView mIvBStar4;
    private ImageView mIvBStar5;
    private LinearLayout mLlMoreData;
    private LinearLayout mLiXia;
    //加载数据
    private boolean isMoreData = true;
    private TextView mTvAnswerNumberevlue;
    private TextView mTvAnswerAddevlua;
    private EditText mEtBSubmit;
    private ImageView mIvBSubmitSend;
    private LinearLayout mLlBSubmitEvalue;
    private EvaluePresenter mEvaluePresenter;
    private AlertDialog mSubmitDialog;
    private ImageView mIvTitleBack;
    private TextView mActivityTitleText;
    private LinearLayout mLlTitleBar;
    private TextView mTvAnswerAnswer;
    private TextView mTvAnswerJiexi;
    //护眼
    private boolean isEye = false;
    //夜间
    private boolean isNight = false;
    private View mVLine;
    private View mVLine2;
    private View mVLine3;
    private View mVLine1;
    private RelativeLayout mRlRootLayout;
    private Button mBtnLookWenAnswer;
    private View mVLine4;
    private TextView mTvLookAnswerCan;
    private TextView mTvLookAnswerWen;
    private LinearLayout mLlLookWenDa;
    private LinearLayout mLlSelectRootLayout;
    private RelativeLayout mRlLookEvalue;
    private static String TYPEMARK = "typemark";
    private static String MARKNUMBER = "number";

    private CommonPopupWindow popShare;
    private String mTypeMark;
    //是否是模拟考试 ture 隐藏提交答题按钮
    private boolean isExam;
    //查看解析后的数据
    private String mResultMark;
    private CommonPopupWindow popResult;

    @Override
    protected void onStop() {
        super.onStop();
        SharedSeletResultListUtil.getInstance().DeleteUser();
    }

    public static Intent newInstance(Context context, String courseid, String type) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(COURSEID, courseid);
        intent.putExtra(TYPEMARK, type);
        return intent;
    }

    public static Intent newInstance(Context mContext, int mark) {
        Intent intent = new Intent(mContext, AnswerActivity.class);
        intent.putExtra(MARKNUMBER, mark);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_answer);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
            mTypeMark = getIntent().getStringExtra(TYPEMARK);
            mResultMark = getIntent().getStringExtra(MARKNUMBER);
        }
        initView();
        clearData();
        initData();
        setLayoutBg();
        //        获取用户购买情况
        initShowSet();

    }

    private void initShowSet() {
        UserbuyOrInfomVo userBuy = SharedUserUtils.getInstance().getUserBuy();
        String type = userBuy.getUserSelectShowType();
        if (!StringUtil.isEmpty(type)) {
            mSelectViewBgZC = type;
            if (mSelectViewBgZC.equals(mSelectViewBgHY)) {
                isNight = false;
                isEye = true;
                setHuYanLayout();
            } else if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {
                isNight = true;
                isEye = false;
                setNightLayout();
            } else {
                isEye = false;
                isNight = false;
                setZhLayout();
            }
        }
        String go = userBuy.getUserNextGo();
        if (!StringUtil.isEmpty(go)) {
            mSelectNext = true;
        }
        if (mTypeMark.equals(DataMessageVo.MARKTYPECASE)) {//案例
            isExam = false;
            init180Time();
        } else if (mTypeMark.equals(DataMessageVo.MARKTYPESKILL)) {//技术
            isExam = false;
            init150Time();
        } else if (mTypeMark.equals(DataMessageVo.MARKTYPECOLLORT)) {//综合
            isExam = false;
            init150Time();
        } else if (mTypeMark.equals(DataMessageVo.MARKTYPEORDER)) {//章节
            isExam = true;
        }
        if (!StringUtil.isEmpty(mResultMark)) {
            mMark = Integer.parseInt(mResultMark);
            bindTextNumberData();
        }


    }

    private void init150Time() {
        long hour = Long.parseLong("02");
        long minute = Long.parseLong("30");
        long second = Long.parseLong("00");
        long time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
        MyCount count = new MyCount(time, 1000);
        mActivityTitleText.setText("00：00：00");
        mActivityTitleText.setTextSize(15);
        count.start();
    }

    private void init180Time() {
        long hour = Long.parseLong("03");
        long minute = Long.parseLong("00");
        long second = Long.parseLong("00");
        long time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
        MyCount count = new MyCount(time, 1000);
        mActivityTitleText.setText("00：00：00");
        mActivityTitleText.setTextSize(15);
        count.start();
    }

    /**
     * 设置布局背景颜色
     */
    private void setLayoutBg() {
        if (mSelectViewBgZC.equals(mSelectViewBgHY)) {
            setHuYanLayout();
        } else if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {
            setNightLayout();
        } else {
            setZhLayout();
        }
    }

    private void initData() {
        mEvaluePresenter = new EvaluePresenter(new EvalueModelImpl(), this);
        mPresnter = new AnswerPresenter(new AnswerModelImpl(), this);
        mPresnter.getEvaluateCotent(mContext, mOid, 1);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresnter.getTextContent(mContext, mOid);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        mRlvEualeContent.setLayoutManager(layoutManager);
        adapter = new AnswerEvaluateAdapter(mContext, mArray);
        mRlvEualeContent.setAdapter(adapter);
        adapter.setBGLayout(mSelectViewBgZC);
        mChbBCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mResultData != null) {
                    mPresnter.submit0ollect(mContext, String.valueOf(mResultData.getId()), isChecked);
                }
            }
        });
        mRlvEualeContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    L.e("加载数据+++++++++++");
                    if (isMoreData) {
                        mPresnter.getEvaluateCotent(mContext, mOid, getNowPage() + 1);
                        mLlMoreData.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


    }


    /**
     * 绑定数据
     */
    private void bindTextNumberData() {
        if (mTextDetial != null) {
            mTvBNew.setText(String.valueOf(mMark + 1));
            if (mMark < mTextDetial.size()) {
                TitleNumberVo.DatasBean bean = mTextDetial.get(mMark);
                mPresnter.getTextDetailContent(mContext, String.valueOf(bean.getId()));
            }
        }


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

    private void initView() {
        mContext = this;
        mRlRootLayout = findViewById(R.id.rl_root_layout);
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mIvBMore.setOnClickListener(this);
        mTvRootEmpty = (TextView) findViewById(R.id.tv_root_empty);
        mTvRootEmpty.setOnClickListener(this);
        mTvBType = (TextView) findViewById(R.id.tv_b_type);
        mTvBType.setOnClickListener(this);
        mTvBMatter = (TextView) findViewById(R.id.tv_b_matter);
        mTvBMatter.setOnClickListener(this);
        mIvBA = (ImageView) findViewById(R.id.iv_b_a);
        mTvBAContent = (TextView) findViewById(R.id.tv_b_a_content);
        mLlBASelect = (LinearLayout) findViewById(R.id.ll_b_a_select);
        mLlBASelect.setOnClickListener(this);
        mIvBB = (ImageView) findViewById(R.id.iv_b_b);
        mTvBBContent = (TextView) findViewById(R.id.tv_b_b_content);
        mLlBBSelect = (LinearLayout) findViewById(R.id.ll_b_b_select);
        mLlBBSelect.setOnClickListener(this);
        mIvBC = (ImageView) findViewById(R.id.iv_b_c);
        mTvBCContent = (TextView) findViewById(R.id.tv_b_c_content);
        mLlBCselect = (LinearLayout) findViewById(R.id.ll_b_cselect);
        mLlBCselect.setOnClickListener(this);
        mIvBD = (ImageView) findViewById(R.id.iv_b_d);
        mTvBDContent = (TextView) findViewById(R.id.tv_b_d_content);
        mLlBDSelect = (LinearLayout) findViewById(R.id.ll_b_d_select);
        mLlBDSelect.setOnClickListener(this);
        mIvBE = (ImageView) findViewById(R.id.iv_b_e);
        mTvBEContent = (TextView) findViewById(R.id.tv_b_e_content);
        mLlBESelect = (LinearLayout) findViewById(R.id.ll_b_e_select);
        mLlBESelect.setOnClickListener(this);
        mBtnBSureKey = (Button) findViewById(R.id.btn_b_sure_key);
        mBtnBSureKey.setOnClickListener(this);
        mTvBAnswer = (TextView) findViewById(R.id.tv_b_answer);
        mTvBAnswer.setOnClickListener(this);
        mLlBAnswerKey = (LinearLayout) findViewById(R.id.ll_b_answer_key);
        mLlBAnswerKey.setOnClickListener(this);
        mTvBRosoleContent = (TextView) findViewById(R.id.tv_b_rosole_content);
        mTvBRosoleContent.setOnClickListener(this);
        mLlBJiexi = (LinearLayout) findViewById(R.id.ll_b_jiexi);
        mLlBJiexi.setOnClickListener(this);
        mTvBAccuracy = (TextView) findViewById(R.id.tv_b_accuracy);
        mTvBAccuracy.setOnClickListener(this);
        mLlBRightLu = (LinearLayout) findViewById(R.id.ll_b_right_lu);
        mLlBRightLu.setOnClickListener(this);
        mBtnBBuy = (Button) findViewById(R.id.btn_b_buy);
        mBtnBBuy.setOnClickListener(this);
        mLiBResolveBuy = (LinearLayout) findViewById(R.id.li_b_resolve_buy);
        mLiBResolveBuy.setOnClickListener(this);
        mRlvEualeContent = (RecyclerView) findViewById(R.id.rlv_euale_content);
        mRlvEualeContent.setOnClickListener(this);
        mSlvRootShow = (SmartScrollView) findViewById(R.id.slv_root_show);
        mSlvRootShow.setOnClickListener(this);
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
        mLlRootLayout = (LinearLayout) findViewById(R.id.ll_root_layout);
        mLlRootLayout.setOnClickListener(this);
        mIvBStar1 = (ImageView) findViewById(R.id.iv_b_star1);
        mIvBStar1.setOnClickListener(this);
        mIvBStar2 = (ImageView) findViewById(R.id.iv_b_star2);
        mIvBStar2.setOnClickListener(this);
        mIvBStar3 = (ImageView) findViewById(R.id.iv_b_star3);
        mIvBStar3.setOnClickListener(this);
        mIvBStar4 = (ImageView) findViewById(R.id.iv_b_star4);
        mIvBStar4.setOnClickListener(this);
        mIvBStar5 = (ImageView) findViewById(R.id.iv_b_star5);
        mIvBStar5.setOnClickListener(this);
        mLlMoreData = (LinearLayout) findViewById(R.id.ll_more_data);
        mLlMoreData.setOnClickListener(this);
        mLiXia = (LinearLayout) findViewById(R.id.li_xia);
        mLiXia.setOnClickListener(this);
        mTvAnswerNumberevlue = (TextView) findViewById(R.id.tv_answer_numberevlue);
        mTvAnswerNumberevlue.setOnClickListener(this);
        mTvAnswerAddevlua = (TextView) findViewById(R.id.tv_answer_addevlua);
        mTvAnswerAddevlua.setOnClickListener(this);
        mEtBSubmit = (EditText) findViewById(R.id.et_b_submit);
        mEtBSubmit.setOnClickListener(this);
        mIvBSubmitSend = (ImageView) findViewById(R.id.iv_b_submit_send);
        mIvBSubmitSend.setOnClickListener(this);
        mLlBSubmitEvalue = (LinearLayout) findViewById(R.id.ll_b_submit_evalue);
        mLlBSubmitEvalue.setOnClickListener(this);
        mIvTitleBack = (ImageView) findViewById(R.id.iv_title_back);
        mIvTitleBack.setOnClickListener(this);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mActivityTitleText.setOnClickListener(this);
        mLlTitleBar = (LinearLayout) findViewById(R.id.ll_title_bar);
        mLlTitleBar.setOnClickListener(this);
        mTvAnswerAnswer = (TextView) findViewById(R.id.tv_answer_answer);
        mTvAnswerAnswer.setOnClickListener(this);
        mTvAnswerJiexi = (TextView) findViewById(R.id.tv_answer_jiexi);
        mTvAnswerJiexi.setOnClickListener(this);
        mVLine = (View) findViewById(R.id.v_line);
        mVLine.setOnClickListener(this);
        mVLine2 = (View) findViewById(R.id.v_line2);
        mVLine2.setOnClickListener(this);
        mVLine3 = (View) findViewById(R.id.v_line3);
        mVLine3.setOnClickListener(this);
        mVLine1 = (View) findViewById(R.id.v_line1);
        mVLine1.setOnClickListener(this);
        mBtnLookWenAnswer = (Button) findViewById(R.id.btn_look_wen_answer);
        mBtnLookWenAnswer.setOnClickListener(this);
        mVLine4 = (View) findViewById(R.id.v_line4);
        mVLine4.setOnClickListener(this);
        mTvLookAnswerCan = (TextView) findViewById(R.id.tv_look_answer_can);
        mTvLookAnswerCan.setOnClickListener(this);
        mTvLookAnswerWen = (TextView) findViewById(R.id.tv_look_answer_wen);
        mTvLookAnswerWen.setOnClickListener(this);
        mLlLookWenDa = (LinearLayout) findViewById(R.id.ll_look_wen_da);
        mLlLookWenDa.setOnClickListener(this);
        mLlSelectRootLayout = (LinearLayout) findViewById(R.id.ll_select_root_layout);
        mLlSelectRootLayout.setOnClickListener(this);
        mRlLookEvalue = (RelativeLayout) findViewById(R.id.rl_look_evalue);
        mRlLookEvalue.setOnClickListener(this);
    }


    private void bindViewData(TextDetailVo vo) {
        setLayoutBg();
        //用户是做过
        boolean isdo = false;
        //用户选中信息
        UseSelectItemInfomVo item = null;
        this.mTextDetialNew = vo;
        mResultData = vo.getData();

//        获取用户做过信息
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        if (user != null && !user.isEmpty()) {
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo vo1 = user.get(i);
                if (vo1.getId() == mResultData.getId()) {//做过
                    isdo = true;
                    item = user.get(i);
                    break;
                }
            }

        }
        //todo 判断是否正确
        //todo 正确 -显示解析
        //todo 错误 -显示解析(显示答案)
        //赋值
        if (true) {// 已购买显示解析
            mLiBResolveBuy.setVisibility(View.GONE);
            mLlBJiexi.setVisibility(View.VISIBLE);
            mLlBRightLu.setVisibility(View.VISIBLE);
            mRlLookEvalue.setVisibility(View.GONE);
            mRlvEualeContent.setVisibility(View.VISIBLE);
        } else {//未购买 隐藏解析
            mLiBResolveBuy.setVisibility(View.VISIBLE);
            mLlBJiexi.setVisibility(View.GONE);
            mLlBRightLu.setVisibility(View.GONE);
            mRlLookEvalue.setVisibility(View.GONE);
            mRlvEualeContent.setVisibility(View.GONE);
        }
        //  判断问题类型单选/多选->提供选择处理
        switch (mResultData.getQuestiontype()) {
            case 2://单选
                mBtnBSureKey.setVisibility(View.GONE);
                mTitleType = mTitleTypeOnly;
                break;
            case 3://多选
                mBtnBSureKey.setVisibility(View.VISIBLE);
                mBtnBSureKey.setClickable(false);
                mTitleType = mTitleTypeMore;
                break;
            case 4://问答
                mBtnBSureKey.setVisibility(View.GONE);
                mTitleType = mTitleTypeWrite;
                break;
            default:

        }

        if (isdo) {//用户做过 未做不处理
            mLlBAnswerKey.setVisibility(View.VISIBLE);
            mLlBJiexi.setVisibility(View.VISIBLE);
            mLlBRightLu.setVisibility(View.VISIBLE);
            mRlLookEvalue.setVisibility(View.VISIBLE);
            mLlSelectRootLayout.setVisibility(View.VISIBLE);
            if (mTitleType.equals(mTitleTypeOnly)) {//单选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String onlyitme = item.getItem();
                setIsClick(false);
                setResultItemBG(onlyitme, mResultData.getChoiceanswer(), mSelectViewBgZC);
            } else if (mTitleType.equals(mTitleTypeMore)) {//多选模式
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                setIsClick(false);
                setResultItemBG(a, b, c, d, e, mResultData.getChoiceanswer(), mSelectViewBgZC);

            } else if (mTitleType.equals(mTitleTypeWrite)) {//问答
                mLlSelectRootLayout.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.VISIBLE);
            }
        } else {
            if (mTitleType.equals(mTitleTypeWrite)) {
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.VISIBLE);
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.GONE);
            } else {
                mLlBAnswerKey.setVisibility(View.GONE);
                mLlBJiexi.setVisibility(View.GONE);
                mLlLookWenDa.setVisibility(View.GONE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                mLlBRightLu.setVisibility(View.GONE);
                mRlLookEvalue.setVisibility(View.GONE);
                mLlSelectRootLayout.setVisibility(View.VISIBLE);
            }
            setIsClick(true);
        }

        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        Spanned html = Html.fromHtml(mResultData.getQuestion());
//        mTvBMatter.setText(mResultData.getQuestion());
        mTvBMatter.setText(html);
        mTvBAContent.setText(mResultData.getA());
        mTvBBContent.setText(mResultData.getB());
        mTvBCContent.setText(mResultData.getC());
        mTvBDContent.setText(mResultData.getD());
        if (StringUtil.isEmpty(mResultData.getE())) {//是否有e选项
            mIvBE.setVisibility(View.GONE);
            mTvBEContent.setVisibility(View.GONE);
        } else {
            mIvBE.setVisibility(View.VISIBLE);
            mTvBEContent.setVisibility(View.VISIBLE);
            mTvBEContent.setText(mResultData.getE());
        }

        mTvBAnswer.setText(mResultData.getChoiceanswer());
        Spanned spanned = Html.fromHtml(mResultData.getAnalysis());
        mTvBRosoleContent.setText(spanned);
//        mTvBRosoleContent.setText(mResultData.getAnalysis());
        mTvBAccuracy.setText(mResultData.getAccuracy());
        mChbBCollect.setChecked(mResultData.isIsfav());
        //正确答案
        mRightItem = mResultData.getChoiceanswer();
        Spanned fromHtml = Html.fromHtml(mResultData.getAnalysis());
        mTvLookAnswerWen.setText(fromHtml);
//        mTvLookAnswerWen.setText(mResultData.getAnalysis());
        int i = mResultData.getDifficultydegreee();
        setStarNumber(i);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_b_more://设置
                showPopwindow();
                break;
            case R.id.ll_b_back://上一题
                if (mTitleType.equals(mTitleTypeMore)) {//多选
                    if (!isSure) {
                        T.showToast(mContext, "请点击确认");
                        return;
                    }
                }
                saveBeforeDate();
                if (mMark != 0) {
                    --mMark;
                } else if (mMark == 0) {
                    T.showToast(mContext, "已经是第一题");
                    break;
                }
                clearbg();
                //清空选项
                clearSeletItem();
                bindTextNumberData();
                break;
            case R.id.ll_b_go://下一题

                if (mTitleType.equals(mTitleTypeMore)) {//多选
                    if (!isSure) {
                        T.showToast(mContext, "请点击确认");
                        return;
                    }
                }
                saveBeforeDate();
                if (mMark <= mTextDetial.size() - 2) {
                    ++mMark;
                } else {//没有题了
                    T.showToast(mContext, "已经是最后一题 ");
                    break;
                }
                clearbg();
                //清空选项
                clearSeletItem();
                bindTextNumberData();

                break;
            case R.id.iv_b_expand://扩展文件夹
                showAnswerLayout();
                break;
            case R.id.ll_b_a_select://选择a
                //单选/多选
                if (mTitleType.equals(mTitleTypeOnly)) {//单选
                    mSelectOnlyitem = A;
                    setSelectOnlyItemBG(true, false, false, false, false, mSelectViewBgZC);
                    setGoNextDan();
                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemA = A;
                    if (isClickA) {
                        isClickA = false;
                    } else {
                        isClickA = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(0, isClickA, mSelectViewBgZC);

                }

                break;
            case R.id.ll_b_b_select://选择b
                if (mTitleType.equals(mTitleTypeOnly)) {             //单选
                    mSelectOnlyitem = B;
                    setSelectOnlyItemBG(false, true, false, false, false, mSelectViewBgZC);
                    setGoNextDan();

                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemB = B;
                    if (isClickB) {
                        isClickB = false;
                    } else {
                        isClickB = true;

                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(1, isClickB, mSelectViewBgZC);

                }
                break;
            case R.id.ll_b_cselect://选择c
                if (mTitleType.equals(mTitleTypeOnly)) {     //单选
                    mSelectOnlyitem = C;
                    setSelectOnlyItemBG(false, false, true, false, false, mSelectViewBgZC);
                    setGoNextDan();

                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemC = C;
                    if (isClickC) {
                        isClickC = false;
                    } else {
                        isClickC = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(2, isClickC, mSelectViewBgZC);
                }
                break;
            case R.id.ll_b_d_select://选择d
                if (mTitleType.equals(mTitleTypeOnly)) {//单选
                    mSelectOnlyitem = D;
                    setSelectOnlyItemBG(false, false, false, true, false, mSelectViewBgZC);
                    setGoNextDan();

                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemD = D;
                    if (isClickD) {
                        isClickD = false;
                    } else {
                        isClickD = true;

                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(3, isClickD, mSelectViewBgZC);

                }
                break;
            case R.id.ll_b_e_select://选择e
                if (mTitleType.equals(mTitleTypeOnly)) {        //单选
                    mSelectOnlyitem = E;
                    setSelectOnlyItemBG(false, false, false, false, true, mSelectViewBgZC);
                    setGoNextDan();

                } else if (mTitleType.equals(mTitleTypeMore)) {//多选
                    mSelectMorItemE = E;
                    if (isClickE) {
                        isClickE = false;
                    } else {
                        isClickE = true;
                    }
                    setSureKeyBg();
                    setSelectMoreItemBG(4, isClickE, mSelectViewBgZC);
                }
                break;
            default:
            case R.id.btn_b_buy://购买

                break;
            case R.id.btn_b_sure_key://多选确认
                isSure = true;
                setGoNextDan();
                break;
            case R.id.tv_answer_addevlua://添加评价
                mLiXia.setVisibility(View.GONE);
                Utils.showSoftInputFromWindow(AnswerActivity.this, mEtBSubmit);
                mLlBSubmitEvalue.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_b_submit_send:
                submitEvalue();
                break;
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.btn_look_wen_answer:
                mLlLookWenDa.setVisibility(View.VISIBLE);
                mBtnLookWenAnswer.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 是否自动跳转下一题
     */
    private void setGoNextDan() {
        saveBeforeDate();
        //正确自动
        if (mTitleType.equals(mTitleTypeOnly)) {//单选模式
            if (mResultData.getChoiceanswer().equalsIgnoreCase(mSelectOnlyitem)) {//正确
                goNext();
            } else {//错误
                bindViewData(mTextDetialNew);
            }
        } else if (mTitleType.equals(mTitleTypeMore)) {//多选模式
            String cont = null;
            List<String> list = getAnswerKeyList(mResultData.getChoiceanswer());
            ArrayList<String> mResult = new ArrayList<>();

            if (!StringUtil.isEmpty(mSelectMorItemA)) {
                mResult.add(mSelectMorItemA);
            }
            if (!StringUtil.isEmpty(mSelectMorItemB)) {
                mResult.add(mSelectMorItemB);
            }

            if (!StringUtil.isEmpty(mSelectMorItemC)) {
                mResult.add(mSelectMorItemC);
            }
            if (!StringUtil.isEmpty(mSelectMorItemD)) {
                mResult.add(mSelectMorItemD);
            }

            if (!StringUtil.isEmpty(mSelectMorItemE)) {
                mResult.add(mSelectMorItemE);
            }
            if (mTitleType.equals(mTitleTypeMore)) {
                if (mResult.size() > list.size()) {
                    cont = "1";
                } else if (mResult.size() == list.size()) {
                    boolean b = list.containsAll(mResult);
                    if (b) {
                        cont = "0";
                    } else {
                        cont = "1";
                    }
                } else if (mResult.size() < list.size()) {
                    if (list.containsAll(mResult)) {
                        cont = "2";
                    } else {
                        cont = "1";
                    }
                }
            }
            if (cont.equals("0")) {
                goNext();
            } else {
                bindViewData(mTextDetialNew);
            }
        }
    }

    private void goNext() {
        if (mSelectNext) {//是否自动跳
            if (!isSure) {
                T.showToast(mContext, "请点击确认");
                return;
            }
            saveBeforeDate();
            if (mMark <= mTextDetial.size() - 2) {
                ++mMark;
            } else {//没有题了
                T.showToast(mContext, "已经是最后一题 ");
                return;
            }
            clearbg();
            //清空选项
            clearSeletItem();
            bindTextNumberData();
        }
    }

    /**
     * 提交评价
     */
    private void submitEvalue() {
        String str = getTextStr(mEtBSubmit);
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, getStringWithId(R.string.content_is_empty));
            return;
        }
        ArrayList<EvalueVo.DatasBean> beans = new ArrayList<>();
        EvalueVo.DatasBean bean = new EvalueVo.DatasBean();
        bean.setContent(str);
        bean.setCreatetime(TimeUtil.dateToString(new Date()));
        beans.add(bean);
        addListData(beans);
        mEvaluePresenter.submitContent(mContext, String.valueOf(mResultData.getId()), str, null, DataMessageVo.QUESTION);
        mSubmitDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit));
        adapter.notifyDataSetChanged();
        Utils.hideInputMethod(mContext, mEtBSubmit);

    }

    /***
     * 清空任何背景
     */
    private void clearbg() {
        setSelectOnlyItemBG(false, false, false, false, false, mSelectViewBgZC);
        setTvColor(mTvBAContent);
        setTvColor(mTvBBContent);
        setTvColor(mTvBCContent);
        setTvColor(mTvBDContent);
        setTvColor(mTvBEContent);

    }


    private void saveBeforeDate() {

        //用户是否选中 没有选中不保存
        if (mTitleType.equals(mTitleTypeOnly)) {
            if (StringUtil.isEmpty(mSelectOnlyitem)) {
                return;
            }
        } else if (mTitleType.equals(mTitleTypeMore)) {
            if (StringUtil.isEmpty(mSelectMorItemB) && StringUtil.isEmpty(mSelectMorItemA) &&
                    StringUtil.isEmpty(mSelectMorItemC) && StringUtil.isEmpty(mSelectMorItemD)
                    && StringUtil.isEmpty(mSelectMorItemE)) {
                return;
            }
        }
        UseSelectItemInfomVo vo = new UseSelectItemInfomVo();
        vo.setType(mTitleType);

        if (mTextDetialNew != null) {
            SharedSeletResultListUtil sp = SharedSeletResultListUtil.getInstance();
            int id = mTextDetialNew.getData().getId();
            //保存用户选中结果
            if (mSeletList == null) {
                mSeletList = new ArrayList<>();
            }
            //获取上次保存内容
            List<UseSelectItemInfomVo> user = sp.getUser();
            if (user != null && !user.isEmpty() && user.size() != 0) {
                int size = user.size();
                for (int i = 0; i < size; i++) {
                    if (vo.getId() == mTextDetialNew.getData().getId()) {
                        return;
                    }
                }
            }
//            问题id
            vo.setId(id);
            if (!StringUtil.isEmpty(mSelectOnlyitem)) {
                if (mSelectOnlyitem.equalsIgnoreCase(mResultData.getChoiceanswer())) {//正确
                    vo.setItemStatus("0");
                    mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), true);
                } else {//错误
                    vo.setItemStatus("1");
                    mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), false);
                }
                vo.setItem(mSelectOnlyitem);
            }
            List<String> list = getAnswerKeyList(mResultData.getChoiceanswer());
            ArrayList<String> mResult = new ArrayList<>();

            if (!StringUtil.isEmpty(mSelectMorItemA)) {
                vo.setSelectItemA(mSelectMorItemA);
                mResult.add(mSelectMorItemA);
            }
            if (!StringUtil.isEmpty(mSelectMorItemB)) {
                vo.setSelectItemB(mSelectMorItemB);
                mResult.add(mSelectMorItemB);
            }

            if (!StringUtil.isEmpty(mSelectMorItemC)) {
                vo.setSelectItemC(mSelectMorItemC);
                mResult.add(mSelectMorItemC);
            }
            if (!StringUtil.isEmpty(mSelectMorItemD)) {
                vo.setSelectItemD(mSelectMorItemD);
                mResult.add(mSelectMorItemD);
            }

            if (!StringUtil.isEmpty(mSelectMorItemE)) {
                vo.setSelectItemE(mSelectMorItemE);
                mResult.add(mSelectMorItemE);
            }
            if (mTitleType.equals(mTitleTypeMore)) {
                if (mResult.size() > list.size()) {
                    vo.setItemStatus("1");
                    mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), false);
                } else if (mResult.size() == list.size()) {
                    boolean b = list.containsAll(mResult);
                    if (b) {
                        vo.setItemStatus("0");
                        mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), true);
                    } else {
                        vo.setItemStatus("1");
                        mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), false);
                    }
                } else if (mResult.size() < list.size()) {
                    if (list.containsAll(mResult)) {
                        vo.setItemStatus("2");
                    } else {
                        vo.setItemStatus("1");
                        mPresnter.submitDoRecord(mContext, String.valueOf(mResultData.getId()), false);
                    }
                }
            }
            vo.setItemSelect(mMark);
            mSeletList.add(vo);
            sp.putListAdd(mSeletList);

        }
    }


    /**
     * 清空选项
     */
    private void clearSeletItem() {
        mSelectOnlyitem = null;
        mSelectMorItemA = null;
        mSelectMorItemB = null;
        mSelectMorItemC = null;
        mSelectMorItemD = null;
        mSelectMorItemE = null;
        isMoreData = true;
        clearClick();
    }

    /**
     * 设置是否能点击
     */
    private void setIsClick(boolean isClick) {
        mLlBASelect.setClickable(isClick);
        mLlBBSelect.setClickable(isClick);
        mLlBCselect.setClickable(isClick);
        mLlBDSelect.setClickable(isClick);
        mLlBESelect.setClickable(isClick);

    }

    /**
     * 显示pop
     */
    private void showPopwindow() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popMore = new CommonPopupWindow(this, R.layout.popw_more_layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private TextView mTvSettring;
            private TextView mTvShare;

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
                    }
                });
                mTvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupWindow popupWindow = popMore.getPopupWindow();
                        popupWindow.dismiss();
                        showShareLayout();

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


    /**
     * 设置白夜布局
     */
    private void showSettring() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        // create popup window
        popSetting = new CommonPopupWindow(this, R.layout.pop_item_setting, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {

            private RadioGroup mRgContentDelete;
            private CheckBox mChbSetNight;
            private CheckBox mChbSetEye;


            @Override
            protected void initView() {
                View view = getContentView();
                mChbSetEye = view.findViewById(R.id.rdb_setting_eye);
                mChbSetNight = view.findViewById(R.id.rdb_setting_night);
                mSwtNext = view.findViewById(R.id.swt_select_next);
                mRgContentDelete = view.findViewById(R.id.rg_content_delete);
            }

            @Override

            protected void initEvent() {
                mRgContentDelete.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            default:

                        }

                    }
                });
                mChbSetNight.setChecked(isNight);
                mChbSetEye.setChecked(isEye);
                mChbSetEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isEye = isChecked;
                        if (isChecked) {
                            if (isNight) {
                                isNight = false;
                                mChbSetNight.setChecked(false);
                            }
                            mSelectViewBgZC = mSelectViewBgHY;

                            setLayoutBg();
                        } else {
                            mSelectViewBgZC = mSelectViewBgZC;
                            setZhLayout();
                        }
                        UserbuyOrInfomVo buy = SharedUserUtils.getInstance().getUserBuy();
                        SharedUserUtils.getInstance().delectUserVo();
                        buy.setUserSelectShowType(mSelectViewBgZC);
                        SharedUserUtils.getInstance().putUserBuyVo(buy);

                    }
                });
                mChbSetNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isNight = isChecked;
                        if (isChecked) {
                            if (isEye) {
                                isEye = false;
                                mChbSetEye.setChecked(false);
                            }
                            mSelectViewBgZC = mSelectViewBgYJ;
                            setLayoutBg();
                        } else {
                            mSelectViewBgZC = mSelectViewBgZC;
                            setZhLayout();
                        }
                        UserbuyOrInfomVo buy = SharedUserUtils.getInstance().getUserBuy();
                        SharedUserUtils.getInstance().delectUserVo();
                        buy.setUserSelectShowType(mSelectViewBgZC);
                        SharedUserUtils.getInstance().putUserBuyVo(buy);

                    }
                });
                mSwtNext.setChecked(mSelectNext);
                mSwtNext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSelectNext = isChecked;
                        UserbuyOrInfomVo buy = SharedUserUtils.getInstance().getUserBuy();
                        SharedUserUtils.getInstance().delectUserVo();
                        if (isChecked) {
                            buy.setUserNextGo("go");
                        } else {
                            buy.setUserNextGo("");
                        }
                        SharedUserUtils.getInstance().putUserBuyVo(buy);
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
     * 设置答题卡布局
     */
    private void showAnswerLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
                if (isExam) {
                    mBtnSubmit.setVisibility(View.GONE);
                } else {
                    mBtnSubmit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void initEvent() {
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSubmit = true;
                        showShareLayout();
                    }
                });
                mTvPopNew.setText(String.valueOf(mMark + 1));
                mTvPopCount.setText(String.valueOf(mTextDetial.size()));
                bindAdapter();


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
        popAnswer.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);

    }

    /**
     * 绑定答题卡表适配器
     */
    private void bindAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 6);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvPopContent.setLayoutManager(manager);
        AnswerTableAdapter adapter = new AnswerTableAdapter(mContext, mTextDetial);
        mRlvPopContent.setAdapter(adapter);
        adapter.setSubmit(isSubmit, mMark);
        adapter.setClickListener(new AnswerTableAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                TitleNumberVo.DatasBean bean = (TitleNumberVo.DatasBean) obj;
                mMark = position;
                popAnswer.getPopupWindow().dismiss();
                bindTextNumberData();
            }
        });

    }

    /**
     * 分享布局
     */
    private void showResultLayout() {
        popResult = new CommonPopupWindow(this, R.layout.activity_answer_result, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) {
            private TextView mTvResultNumber;
            private TextView mTvAnswerTime;
            private TextView mTvAnswerLv;
            private RecyclerView mRlvAnswerResultBag;
            private Button mBtnAnswerAgain;
            private Button mBtnAnswerJiexi;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvResultNumber = (TextView) view.findViewById(R.id.tv_result_number);
                mTvAnswerTime = (TextView) view.findViewById(R.id.tv_answer_time);
                mTvAnswerLv = (TextView) view.findViewById(R.id.tv_answer_lv);
                mRlvAnswerResultBag = (RecyclerView) view.findViewById(R.id.rlv_answer_result_bag);
                mBtnAnswerAgain = (Button) view.findViewById(R.id.btn_answer_again);
                mBtnAnswerJiexi = (Button) view.findViewById(R.id.btn_answer_jiexi);
            }

            @Override
            protected void initEvent() {
                initAdapter();

            }

            private void initAdapter() {
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                manager.setOrientation(GridLayoutManager.VERTICAL);
                AnswerTableAdapter adapter = new AnswerTableAdapter(mContext,mTextDetial );
                mRlvAnswerResultBag.setLayoutManager(manager);
                mRlvAnswerResultBag.setAdapter(adapter);
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
        popResult.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f, AnswerActivity.this);
    }

    /**
     * 分享布局
     */
    private void showShareLayout() {
        popShare = new CommonPopupWindow(this, R.layout.pop_item_share, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {

            private TextView qqzon;
            private TextView qq;
            private TextView weibo;
            private TextView circle;
            private TextView weixin;

            @Override
            protected void initView() {
                View view = getContentView();
                weixin = (TextView) view.findViewById(R.id.tv_pop_weixin_share);
                circle = (TextView) view.findViewById(R.id.tv_pop_crile_share);
                weibo = (TextView) view.findViewById(R.id.tv_pop_weibo_share);
                qq = (TextView) view.findViewById(R.id.tv_pop_qq_share);
                qqzon = (TextView) view.findViewById(R.id.tv_pop_qqzon_share);
            }

            @Override
            protected void initEvent() {


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
        popShare.showAtLocation(mLlRootLayout, Gravity.BOTTOM, 0, 0);
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

    /**
     * 单选结果展示
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param day
     */
    private void setSelectOnlyItemBG(boolean a, boolean b, boolean c, boolean d,
                                     boolean e, String day) {
        setImgBg(mIvBA, a, R.drawable.ic_b_a_s, R.drawable.ic_b_a_n);
        setImgBg(mIvBB, b, R.drawable.ic_b_b_s, R.drawable.ic_b_b_n);
        setImgBg(mIvBC, c, R.drawable.ic_b_c_s, R.drawable.ic_b_c_n);
        setImgBg(mIvBD, d, R.drawable.ic_b_d_s, R.drawable.ic_b_d_n);
        setImgBg(mIvBE, e, R.drawable.ic_b_e_s, R.drawable.ic_b_e_n);
    }

    /**
     * 多选结果展示
     *
     * @param id  0a,1b,2c,3d,4e
     * @param day
     */
    private void setSelectMoreItemBG(int id, boolean isSelect, String day) {
        if (id == 0) {
            setImgBg(mIvBA, isSelect, R.drawable.ic_b_a_s, R.drawable.ic_b_a_n);
        } else if (id == 1) {
            setImgBg(mIvBB, isSelect, R.drawable.ic_b_b_s, R.drawable.ic_b_b_n);
        } else if (id == 2) {
            setImgBg(mIvBC, isSelect, R.drawable.ic_b_c_s, R.drawable.ic_b_c_n);
        } else if (id == 3) {
            setImgBg(mIvBD, isSelect, R.drawable.ic_b_d_s, R.drawable.ic_b_d_n);
        } else if (id == 4) {
            setImgBg(mIvBE, isSelect, R.drawable.ic_b_e_s, R.drawable.ic_b_e_n);
        }

    }

    /**
     * 单选
     *
     * @param select
     * @param answer
     * @param day
     */
    private void setResultItemBG(String select, String answer, String day) {
        if (select.equalsIgnoreCase(answer)) {//选项正确
            ImageView imageView0 = selectItemName(select);
            if (imageView0 != null) {
                imageView0.setImageResource(R.drawable.ic_b_right);
            }
            TextView textView = selectTextView(select);
            if (textView != null) {
                textView.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
            }
        } else {
            ImageView imageView1 = selectItemName(select);
            if (imageView1 != null) {
                imageView1.setImageResource(R.drawable.ic_b_erro);
            }
            ImageView imageView2 = selectItemName(answer);
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.ic_b_right);
            }
            TextView textView = selectTextView(select);
            if (textView != null)
                textView.setTextColor(mContext.getResources().getColor(R.color.text_color_error));
            TextView textView1 = selectTextView(answer);
            if (textView1 != null) {
                textView1.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
            }
        }


    }

    /**
     * 多选结果展示
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param choiceanswer
     * @param mSelectViewBgZC
     */
    private void setResultItemBG(String a, String b, String c, String d, String e, String
            choiceanswer, String mSelectViewBgZC) {
        List<String> key = getAnswerKeyList(choiceanswer);
        for (String s : key) {
            if (s.equalsIgnoreCase("A")) {
                setImgMiss(mIvBA);
                setTvMiss(mTvBAContent);
            } else if (s.equalsIgnoreCase("B")) {
                setImgMiss(mIvBB);
                setTvMiss(mTvBBContent);
            } else if (s.equalsIgnoreCase("C")) {
                setImgMiss(mIvBC);
                setTvMiss(mTvBCContent);
            } else if (s.equalsIgnoreCase("D")) {
                setImgMiss(mIvBD);
                setTvMiss(mTvBDContent);
            } else if (s.equalsIgnoreCase("E")) {
                setImgMiss(mIvBE);
                setTvMiss(mTvBEContent);
            }
        }
        if (!StringUtil.isEmpty(a)) {//用户选择
            boolean a1 = keyIsRight(key, a);
            if (a1) {//正确
                setImgRight(mIvBA);
                setTvRight(mTvBAContent);
            } else {//错误
                setImgError(mIvBA);
                setTvError(mTvBAContent);
            }
        }
        if (!StringUtil.isEmpty(b)) {//用户选择
            boolean b1 = keyIsRight(key, b);
            if (b1) {//正确
                setImgRight(mIvBB);
                setTvRight(mTvBBContent);
            } else {//错误
                setImgError(mIvBB);
                setTvError(mTvBBContent);
            }
        }
        if (!StringUtil.isEmpty(c)) {//用户选择
            boolean c1 = keyIsRight(key, c);
            if (c1) {//正确
                setImgRight(mIvBC);
                setTvRight(mTvBCContent);
            } else {//错误
                setImgError(mIvBC);
                setTvError(mTvBCContent);
            }
        }
        if (!StringUtil.isEmpty(d)) {//用户选择
            boolean d1 = keyIsRight(key, d);
            if (d1) {//正确
                setImgRight(mIvBD);
                setTvRight(mTvBDContent);
            } else {//错误
                setImgError(mIvBD);
                setTvError(mTvBDContent);
            }
        }
        if (!StringUtil.isEmpty(e)) {//用户选择
            boolean e1 = keyIsRight(key, e);
            if (e1) {//正确
                setImgRight(mIvBE);
                setTvRight(mTvBEContent);
            } else {//错误
                setImgError(mIvBE);
                setTvError(mTvBEContent);
            }
        }


    }

    private boolean keyIsRight(List<String> listkey, String key) {
        for (String s : listkey) {
            if (s.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 设置正确图片
     *
     * @param select
     * @return
     */
    private ImageView selectItemName(String select) {
        if (select.equalsIgnoreCase("A")) {
            return mIvBA;
        }
        if (select.equalsIgnoreCase("B")) {
            return mIvBB;
        }
        if (select.equalsIgnoreCase("C")) {
            return mIvBC;
        }
        if (select.equalsIgnoreCase("D")) {
            return mIvBD;
        }
        if (select.equalsIgnoreCase("E")) {
            return mIvBE;
        }
        return null;
    }

    /**
     * 设置文体文字
     *
     * @param select
     * @return
     */
    private TextView selectTextView(String select) {
        if (select.equalsIgnoreCase("A")) {
            return mTvBAContent;
        }
        if (select.equalsIgnoreCase("B")) {
            return mTvBBContent;
        }
        if (select.equalsIgnoreCase("C")) {
            return mTvBCContent;
        }
        if (select.equalsIgnoreCase("D")) {
            return mTvBDContent;
        }
        if (select.equalsIgnoreCase("E")) {
            return mTvBEContent;
        }
        return null;
    }

    /**
     * 设置背景
     *
     * @param iv
     * @param is
     * @param selectid
     * @param unselectid
     */
    private void setImgBg(ImageView iv, boolean is, int selectid, int unselectid) {
        if (is) {
            iv.setImageResource(selectid);
        } else {
            iv.setImageResource(unselectid);
        }
    }


    private void setTvColor(TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(R.color.black));
    }

    /**
     * 设置正确图片
     *
     * @param imgRight
     */
    private void setImgRight(ImageView imgRight) {
        imgRight.setImageResource(R.drawable.ic_b_right);
    }

    /**
     * 设置正确文字图片
     *
     * @param tv
     */
    private void setTvRight(TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_right));
    }

    /**
     * 设置错图片
     *
     * @param imgError
     */
    private void setImgError(ImageView imgError) {
        imgError.setImageResource(R.drawable.ic_b_erro);
    }

    /**
     * 设置错文字
     *
     * @param tv
     */
    private void setTvError(TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_error));
    }

    /**
     * 设置图片miss
     *
     * @param imgMiss
     */
    private void setImgMiss(ImageView imgMiss) {
        imgMiss.setImageResource(R.drawable.ic_b_miss);
    }

    /**
     * 设置字体miss
     *
     * @param tv
     */
    private void setTvMiss(TextView tv) {
        tv.setTextColor(mContext.getResources().getColor(R.color.text_color_woring));
    }

    /**
     * 截取
     *
     * @param key
     * @return
     */
    private List<String> getAnswerKeyList(String key) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        getAnswer(key);
        return list;
    }


    public void getAnswer(String key) {
        int length = key.length();
        if (length > 1) {
            String substring = key.substring(0, 1);
            list.add(substring);
            key = key.substring(1, length);
            getAnswer(key);
        } else {
            list.add(key);
        }
    }

    /**
     * 设置确认按钮
     */
    private void setSureKeyBg() {
        if (isClickD || isClickB || isClickA || isClickE || isClickC) {
            mBtnBSureKey.setClickable(true);
            isSure = false;
            mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_s);
        } else {
            isSure = true;
            mBtnBSureKey.setClickable(false);
            mBtnBSureKey.setBackgroundResource(R.drawable.btn_b_sure_n);
        }
    }

    /**
     * 清空用户点击
     */
    private void clearClick() {
        isClickA = false;
        isClickB = false;
        isClickC = false;
        isClickD = false;
        isClickE = false;
    }

    /**
     * 评价内容
     *
     * @param con
     */
    @Override
    public void EvalueSuccess(String con) {
        L.e("习题评价" + con);
        Gson gson = new Gson();
        EvalueVo vo = gson.fromJson(con, EvalueVo.class);
        if (vo.getDatas() == null) {
            mLlMoreData.setVisibility(View.GONE);
        } else {
            //判断是否能整除
            addListData(vo.getDatas());
            mTvAnswerNumberevlue.setText("评论(" + vo.getTotal().getTotal() + ")");
            if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                mLlMoreData.setVisibility(View.GONE);
                isMoreData = true;
            } else {
                isMoreData = false;
                mLlMoreData.setVisibility(View.GONE);
            }

            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 评价错误
     *
     * @param error
     */
    @Override
    public void EvalueError(String error) {
        L.e("习题评价错误" + error);
    }

    @Override
    public void TextSuccess(String msg) {
        L.e("TextSuccess");
        Gson gson = new Gson();
        TitleNumberVo vo = gson.fromJson(msg, TitleNumberVo.class);
        if (vo.getStatus().getCode() == 200) {
            mRlRootLayout.setVisibility(View.VISIBLE);
            if (vo.getDatas() == null || vo.getDatas().size() == 0) {
                mSlvRootShow.setVisibility(View.GONE);
                dialog.dismiss();
                T.showToast(mContext, "该文章没有练习题，请重新选择");
                mTvRootEmpty.setVisibility(View.VISIBLE);
                return;
            } else {
                mTvRootEmpty.setVisibility(View.GONE);
                mSlvRootShow.setVisibility(View.VISIBLE);
            }
            mTextDetial = vo.getDatas();
            mTvBCount.setText(String.valueOf(mTextDetial.size()));
            bindTextNumberData();

        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void TextError(String con) {
        T.showToast(mContext, con);
    }

    @Override
    public void TextDetailSuccess(String message) {
        if (dialog != null) {
            dialog.dismiss();
        }
        L.d("题库详情", message);
        Gson gson = new Gson();
        TextDetailVo vo = gson.fromJson(message, TextDetailVo.class);
        if (vo.getStatus().getCode() == 200) {
            bindViewData(vo);
        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }
    }

    @Override
    public void TextDetailError(String con) {
        T.showToast(mContext, con);
    }


    @Override
    public void SumbitCollectSuccess(String con) {
        L.e("yfl提交" + con);
    }

    @Override
    public void SumbitCollectError(String con) {
        L.e("yfl提交" + con);
    }

    @Override
    public void DoResultSuccess(String con) {
        L.e("做题结果" + con);
    }

    @Override
    public void DoResultError(String con) {
        L.e("做题结果" + con);
    }

    //设置难度星星
    private void setStarNumber(int number) {
        ArrayList<ImageView> list = new ArrayList<>();
        list.add(mIvBStar1);
        list.add(mIvBStar2);
        list.add(mIvBStar3);
        list.add(mIvBStar4);
        list.add(mIvBStar5);
        for (int i = 0; i < number; i++) {
            ImageView imageView = list.get(i);
            imageView.setImageResource(R.drawable.ic_b_difficulty_s);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedSeletResultListUtil.getInstance().DeleteUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedSeletResultListUtil.getInstance().DeleteUser();
    }


    @Override
    public void submitEvalueSuccess(String con) {
        if (mSubmitDialog != null) {
            mSubmitDialog.dismiss();
        }
        mLiXia.setVisibility(View.VISIBLE);
        mLlBSubmitEvalue.setVisibility(View.GONE);
    }

    @Override
    public void submitEvalueError(String con) {
        if (mSubmitDialog != null) {
            mSubmitDialog.dismiss();
        }
        mLiXia.setVisibility(View.VISIBLE);
        mLlBSubmitEvalue.setVisibility(View.GONE);
    }

    @Override
    public void GetEvalueSuccess(String con) {

    }

    @Override
    public void GetEvalueError(String con) {

    }

    /**
     * 设置夜间
     */
    private void setNightLayout() {
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.night_title_bar));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        setTvColor(mTvBType, R.color.night_text_color);
        setTvColor(mTvBMatter, R.color.night_text_color);
        setTvColor(mTvBAContent, R.color.night_text_color);
        setTvColor(mTvBBContent, R.color.night_text_color);
        setTvColor(mTvBCContent, R.color.night_text_color);
        setTvColor(mTvBDContent, R.color.night_text_color);
        setTvColor(mTvBEContent, R.color.night_text_color);
        setTvColor(mTvAnswerJiexi, R.color.night_text_color);
        setTvColor(mTvAnswerAnswer, R.color.night_text_color);
        setTvColor(mTvBRosoleContent, R.color.night_text_color);
        setTvColor(mTvLookAnswerCan, R.color.night_text_color);
        setTvColor(mTvBAnswer, R.color.night_text_color);
        setTvColor(mTvAnswerNumberevlue, R.color.night_text_color);
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.night_layout_bg));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.night_title_bar));
        setLinebg(mVLine, R.color.night_line_bg);
        setLinebg(mVLine1, R.color.night_line_bg);
        setLinebg(mVLine2, R.color.night_line_bg);
        setLinebg(mVLine3, R.color.night_line_bg);
        setLinebg(mVLine4, R.color.night_line_bg);


    }


    /**
     * 设置护眼
     */
    private void setHuYanLayout() {
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.eye_layout_bg));
        setLinebg(mVLine, R.color.eye_line_bg);
        setLinebg(mVLine1, R.color.eye_line_bg);
        setLinebg(mVLine2, R.color.eye_line_bg);
        setLinebg(mVLine3, R.color.eye_line_bg);
        setLinebg(mVLine4, R.color.eye_line_bg);
    }

    /**
     * 设置正常
     */

    private void setZhLayout() {
        mLlTitleBar.setBackgroundColor(getLayoutColor(R.color.white));
        mLlRootLayout.setBackgroundColor(getLayoutColor(R.color.white));
        mLlLookWenDa.setBackgroundColor(getLayoutColor(R.color.white));
        setTvColor(mTvBType, R.color.text_fu_color);
        setTvColor(mTvBMatter, R.color.black);
        setTvColor(mTvBAContent, R.color.black);
        setTvColor(mTvBBContent, R.color.black);
        setTvColor(mTvBCContent, R.color.black);
        setTvColor(mTvBDContent, R.color.black);
        setTvColor(mTvBEContent, R.color.black);
        setTvColor(mTvAnswerJiexi, R.color.black);
        setTvColor(mTvAnswerAnswer, R.color.black);
        setTvColor(mTvBRosoleContent, R.color.black);
        setTvColor(mTvBAnswer, R.color.black);
        setTvColor(mTvAnswerNumberevlue, R.color.black);
        mLlBSubmitEvalue.setBackgroundColor(getLayoutColor(R.color.white));
        mLiXia.setBackgroundColor(getLayoutColor(R.color.white));
        setLinebg(mVLine, R.color.input_bg);
        setLinebg(mVLine1, R.color.input_bg);
        setLinebg(mVLine2, R.color.input_bg);
        setLinebg(mVLine3, R.color.input_bg);
        setLinebg(mVLine4, R.color.input_bg);
    }

    private int getLayoutColor(int id) {
        return mContext.getResources().getColor(id);
    }

    private void setTvColor(TextView tv, int id) {
        tv.setTextColor(getLayoutColor(id));
    }

    private void setLinebg(View v, int id) {
        v.setBackgroundColor(getLayoutColor(id));
    }

    /**
     * 实现倒计时功能的类
     */
    class MyCount extends AdvancedCountdownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {  //这两个参数在AdvancedCountdownTimer.java中均有(在“构造函数”中).
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mActivityTitleText.setText("00：00：00");
//            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
//            startActivity(intent);
//            finish();


        }

        //更新剩余时间
        String a = null;

        @Override
        public void onTick(long millisUntilFinished, int percent) {
            long myhour = (millisUntilFinished / 1000) / 3600;
            long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
            long mysecond = millisUntilFinished / 1000 - myhour * 3600
                    - myminute * 60;
            if (mysecond < 10) {
                a = "0" + mysecond;
                mActivityTitleText.setText("0" + myhour + ":" + "0" + myminute + ":" + a);
            } else if (myminute < 10) {
                mActivityTitleText.setText("0" + myhour + ":" + "0" + myminute + ":" + mysecond);
            } else if (myhour < 10) {
                mActivityTitleText.setText("0" + myhour + ":" + "" + myminute + ":" + mysecond);
            } else {
                mActivityTitleText.setText("" + myhour + ":" + "" + myminute + ":" + mysecond);

            }

            mActivityTitleText.setTextSize(15);
        }

    }

}

