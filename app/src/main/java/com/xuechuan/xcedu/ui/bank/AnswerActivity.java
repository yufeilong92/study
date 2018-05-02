package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.utils.LogUtils;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AnswerTableAdapter;
import com.xuechuan.xcedu.adapter.BaseApdater;
import com.xuechuan.xcedu.adapter.HomeEvaluateAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.AnswerModelImpl;
import com.xuechuan.xcedu.mvp.presenter.AnswerPresnter;
import com.xuechuan.xcedu.mvp.view.AnswerView;
import com.xuechuan.xcedu.utils.AnswerCardUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.TextDetailVo;
import com.xuechuan.xcedu.vo.TitleNumberVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;
import com.xuechuan.xcedu.vo.UserbuyOrInfomVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends BaseActivity implements View.OnClickListener, AnswerView {

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
    private final String mSelectViewBgHY = "hy";
    private final String mSelectViewBgYJ = "yj";
    private CommonPopupWindow popMore;
    private CommonPopupWindow popSetting;
    private CommonPopupWindow popAnswer;
    private TextView mTvSettring;
    private TextView mTvShare;
    private CheckBox mSwtNext;
    private RadioGroup mRgSetType;
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
    private AnswerPresnter mPresnter;
    private HomeEvaluateAdapter adapter;
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
    private ScrollView mSlvRootShow;
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


    @Override
    protected void onStop() {
        super.onStop();
        SharedSeletResultListUtil.getInstance().DeleteUser();
    }

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_answer);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        clearData();
        initData();
        //        获取用户购买情况
        UserbuyOrInfomVo userBuy = SharedUserUtils.getInstance().getUserBuy();
    }

    private void initData() {
        mPresnter = new AnswerPresnter(new AnswerModelImpl(), this);
        mPresnter.getEvaluateCotent(mContext, mOid, 1);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        mPresnter.getTextContent(mContext, mOid);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        mRlvEualeContent.setLayoutManager(layoutManager);
//        adapter = new HomeEvaluateAdapter(mContext, mArray);
        int a = 10;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            list.add("sadasdadsasd");
        }
        BaseApdater apdater = new BaseApdater(mContext, list);

        mRlvEualeContent.setAdapter(apdater);

    }

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
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
     * todo 界面背景选择
     */
    private void selectViewBg() {
        // todo 用户选中是否护眼/夜间/正常
        if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {//夜间

        }
    }

    private void initView() {
        mContext = this;

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
        mSlvRootShow = (ScrollView) findViewById(R.id.slv_root_show);
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
    }


    private void bindViewData(TextDetailVo vo) {
        //用户是做过
        boolean isdo = false;
        //用户选中信息
        UseSelectItemInfomVo item = null;
        this.mTextDetialNew = vo;
        mResultData = vo.getData();

        //todo 判断用户是否做
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
        } else {//未购买 隐藏解析
            mLiBResolveBuy.setVisibility(View.VISIBLE);
            mLlBJiexi.setVisibility(View.GONE);
            mLlBRightLu.setVisibility(View.GONE);
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
                mTitleType = mTitleTypeWrite;
                break;
            default:

        }

        if (isdo) {//用户做过 未做不处理
            mLlBAnswerKey.setVisibility(View.VISIBLE);
            mLlBJiexi.setVisibility(View.VISIBLE);
            mLlBRightLu.setVisibility(View.VISIBLE);
            if (mTitleType.equals(mTitleTypeOnly)) {//单选模式
                String onlyitme = item.getItem();
                setIsClick(false);
                setResultItemBG(onlyitme, mResultData.getChoiceanswer(), mSelectViewBgZC);
            } else if (mTitleType.equals(mTitleTypeMore)) {//多选模式
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                setIsClick(false);
                setResultItemBG(a, b, c, d, e, mResultData.getChoiceanswer(), mSelectViewBgZC);

            } else {//问答

            }
        } else {
            mLlBAnswerKey.setVisibility(View.GONE);
            mLlBJiexi.setVisibility(View.GONE);
            mLlBRightLu.setVisibility(View.GONE);
            setIsClick(true);
        }
        //todo  用户是否选择错误
        //todo 题干类型是否是多选
        mTvBType.setText(AnswerCardUtil.getTextType(mResultData.getQuestiontype()));
        mTvBMatter.setText(mResultData.getQuestion());
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
        mTvBRosoleContent.setText(mResultData.getAnalysis());
        mTvBAccuracy.setText(mResultData.getAccuracy());
        mChbBCollect.setChecked(mResultData.isIsfav());
        //正确答案
        mRightItem = mResultData.getChoiceanswer();

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
            case R.id.chb_b_collect://收藏
                break;
            case R.id.ll_b_a_select://选择a
                //单选/多选
                if (mTitleType.equals(mTitleTypeOnly)) {//单选
                    mSelectOnlyitem = A;
                    setSelectOnlyItemBG(true, false, false, false, false, mSelectViewBgZC);
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
                break;
        }
    }

    private void clearbg() {
        setSelectOnlyItemBG(false, false, false, false, false, mSelectViewBgZC);
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
                } else {//错误
                    vo.setItemStatus("1");
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

            if (mResult.size() > list.size()) {
                vo.setItemStatus("1");
            } else if (mResult.size() == list.size()) {
                boolean b = list.containsAll(mResult);
                if (b) {
                    vo.setItemStatus("0");
                } else {
                    vo.setItemStatus("1");
                }
            }else if (mResult.size()<list.size()){
                  if (list.containsAll(mResult)){
                      vo.setItemStatus("2");
                  }else {
                      vo.setItemStatus("1");
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
    DisplayMetrics metrics = new DisplayMetrics();

    private void showPopwindow() {
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

    //设置布局
    private void showAnswerLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        // create popup window
        //护眼
//夜间
        popAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
            }

            @Override
            protected void initEvent() {
                mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSubmit = true;
                    }
                });

                mTvPopNew.setText(String.valueOf(mMark + 1));
                mTvPopCount.setText(String.valueOf(mTextDetial.size()));
                GridLayoutManager manager = new GridLayoutManager(mContext, 6);
                manager.setOrientation(GridLayoutManager.VERTICAL);
                mRlvPopContent.setLayoutManager(manager);
                AnswerTableAdapter adapter = new AnswerTableAdapter(mContext, mTextDetial);
                mRlvPopContent.setAdapter(adapter);
                adapter.setSubmit(isSubmit, mMark);


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
                return;
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
            return;
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
            } else if (s.equalsIgnoreCase("B")) {
                setImgMiss(mIvBB);
            } else if (s.equalsIgnoreCase("C")) {
                setImgMiss(mIvBC);
            } else if (s.equalsIgnoreCase("D")) {
                setImgMiss(mIvBD);
            } else if (s.equalsIgnoreCase("E")) {
                setImgMiss(mIvBE);
            }
        }
        if (!StringUtil.isEmpty(a)) {//用户选择
            boolean a1 = keyIsRight(key, a);
            if (a1) {//正确
                setImgRight(mIvBA);
            } else {//错误
                setImgError(mIvBA);
            }
        }
        if (!StringUtil.isEmpty(b)) {//用户选择
            boolean b1 = keyIsRight(key, b);
            if (b1) {//正确
                setImgRight(mIvBB);
            } else {//错误
                setImgError(mIvBB);
            }
        }
        if (!StringUtil.isEmpty(c)) {//用户选择
            boolean c1 = keyIsRight(key, c);
            if (c1) {//正确
                setImgRight(mIvBC);
            } else {//错误
                setImgError(mIvBC);
            }
        }
        if (!StringUtil.isEmpty(d)) {//用户选择
            boolean d1 = keyIsRight(key, d);
            if (d1) {//正确
                setImgRight(mIvBD);
            } else {//错误
                setImgError(mIvBD);
            }
        }
        if (!StringUtil.isEmpty(e)) {//用户选择
            boolean e1 = keyIsRight(key, e);
            if (e1) {//正确
                setImgRight(mIvBE);
            } else {//错误
                setImgError(mIvBE);
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
     * 设置不图片
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

    private void setImgRight(ImageView imgRight) {
        imgRight.setImageResource(R.drawable.ic_b_right);
    }

    private void setImgError(ImageView imgError) {
        imgError.setImageResource(R.drawable.ic_b_erro);
    }

    /**
     * 设置图片miss
     *
     * @param imgMiss
     */
    private void setImgMiss(ImageView imgMiss) {
        imgMiss.setImageResource(R.drawable.ic_b_miss);
    }

    private void setLibg(LinearLayout la, boolean is) {
        if (is) {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
        } else {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

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
        addListData(vo.getDatas());

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


}

