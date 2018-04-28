package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.AnswerCardUtil;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.utils.SharedUserUtils;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.TextDetailVo;
import com.xuechuan.xcedu.vo.TitleNumberVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;
import com.xuechuan.xcedu.vo.UserbuyOrInfomVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBType;
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
    private LinearLayout mLlBESelect;
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


    private String mSelectMorItemA = null;
    private String mSelectMorItemB = null;
    private String mSelectMorItemC = null;
    private String mSelectMorItemD = null;
    private String mSelectMorItemE = null;
    //记录用户是否点击自动下一题
    private boolean mSelectNext;
    //用户选中的item
    private String mRightItem = null;
    //问题题干类型
    private String mTitleType = null;
    private final String mTitleTypeOnly = "only";
    private final String mTitleTypeMore = "more";
    private final String mTitleTypeWrite = "write";


    private String mSelectViewBgZC = "zc";
    private final String mSelectViewBgHY = "hy";
    private final String mSelectViewBgYJ = "yj";
    private CommonPopupWindow popMore;
    private CommonPopupWindow popSetting;
    private TextView mTvSettring;
    private TextView mTvShare;
    private CheckBox mSwtNext;
    private RadioGroup mRgSetType;
    private TextView mTvBAnsewer;
    private TextView mTvBRosoleContent;
    private Button mBtnBBuy;
    private LinearLayout mLiBResolveBuy;
    private TextView mTvBAnswer;
    private TextView mTvBAccuracy;
    private LinearLayout mLlBAnswerKey;
    private TextView mTvRootEmpty;
    private RecyclerView mRlvBAnseswrEvalue;
    private ScrollView mSlvRootShow;
    /**
     * 当前题干信息
     */
    private TextDetailVo mTextDetialNew;
    /**
     * 用户选择结果
     */
    private ArrayList<UseSelectItemInfomVo> mSeletList;
    private LinearLayout mLlBJiexi;
    private LinearLayout mLlBRightLu;
    private Button mBtnBSureKey;

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(COURSEID, courseid);
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
        }
        initView();
        initData();
        //        获取用户购买情况
        UserbuyOrInfomVo userBuy = SharedUserUtils.getInstance().getUserBuy();
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
                    if (vo.getDatas() == null || vo.getDatas().size() == 0) {
                        mSlvRootShow.setVisibility(View.GONE);
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
            public void onError(Response<String> response) {
                T.showToast(mContext, response.message());
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
                requestData(bean);
            }
        }


    }


    private void requestData(TitleNumberVo.DatasBean bean) {
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
                    bindViewData(vo);
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
        // todo 用户选中是否护眼/夜间/正常
        if (mSelectViewBgZC.equals(mSelectViewBgYJ)) {//夜间

        }
    }

    private void initView() {
        mTvBType = (TextView) findViewById(R.id.tv_b_type);
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
        mBtnBBuy = (Button) findViewById(R.id.btn_b_buy);
        mBtnBBuy.setOnClickListener(this);
        mLiBResolveBuy = (LinearLayout) findViewById(R.id.li_b_resolve_buy);
        mLiBResolveBuy.setOnClickListener(this);
        mTvBAnswer = (TextView) findViewById(R.id.tv_b_answer);
        mTvBAnswer.setOnClickListener(this);
        mTvBAccuracy = (TextView) findViewById(R.id.tv_b_accuracy);
        mTvBAccuracy.setOnClickListener(this);
        mLlBAnswerKey = (LinearLayout) findViewById(R.id.ll_b_answer_key);
        mLlBAnswerKey.setOnClickListener(this);
        mTvRootEmpty = (TextView) findViewById(R.id.tv_root_empty);
        mTvRootEmpty.setOnClickListener(this);
        mRlvBAnseswrEvalue = (RecyclerView) findViewById(R.id.rlv_b_anseswr_evalue);
        mRlvBAnseswrEvalue.setOnClickListener(this);
        mSlvRootShow = (ScrollView) findViewById(R.id.slv_root_show);
        mSlvRootShow.setOnClickListener(this);
        mLlBJiexi = (LinearLayout) findViewById(R.id.ll_b_jiexi);
        mLlBJiexi.setOnClickListener(this);
        mLlBRightLu = (LinearLayout) findViewById(R.id.ll_b_right_lu);
        mLlBRightLu.setOnClickListener(this);
        mBtnBSureKey = (Button) findViewById(R.id.btn_b_sure_key);
        mBtnBSureKey.setOnClickListener(this);
    }

    private void bindViewData(TextDetailVo vo) {
        //用户是做过
        boolean isdo = false;
        //用户选中信息
        UseSelectItemInfomVo item = null;
        this.mTextDetialNew = vo;
        TextDetailVo.DataBean data = vo.getData();

        //todo 判断用户是否做
//        获取用户做过信息
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        for (int i = 0; i < user.size(); i++) {
            UseSelectItemInfomVo vo1 = user.get(i);
            if (vo1.getId() == data.getId()) {//做过
                isdo = true;
                item = user.get(i);
                break;
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
        switch (data.getQuestiontype()) {
            case 2://单选
                mTitleType = mTitleTypeOnly;
                break;
            case 3://多选
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
                setResultItemBG(onlyitme, data.getChoiceanswer(), mSelectViewBgZC);
            } else if (mTitleType.equals(mTitleTypeMore)) {//多选模式
                String a = item.getSelectItemA();
                String b = item.getSelectItemB();
                String c = item.getSelectItemC();
                String d = item.getSelectItemD();
                String e = item.getSelectItemE();
                setIsClick(false);
                setResultItemBG(a,b,c,d,e, data.getChoiceanswer(), mSelectViewBgZC);

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

    private void setResultItemBG(String a, String b, String c, String d, String e, String choiceanswer, String mSelectViewBgZC) {





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_b_more://设置
                showPopwindow();
                break;
            case R.id.ll_b_back://上一题
                saveBeforeDate();
                Log.e("yfl", "onClick: " + mMark);
                if (mMark != 0) {
                    --mMark;
                } else if (mMark == 0) {
                    T.showToast(mContext, "已经是第一题");
                    break;
                }
                clearbg();
                bindTextNumberData();
                break;
            case R.id.ll_b_go://下一题
                saveBeforeDate();
                if (mMark <= mTextDetial.size() - 2) {
                    ++mMark;
                } else {//没有题了
                    T.showToast(mContext, "已经是最后一题 ");
                    break;
                }
                clearbg();
                bindTextNumberData();
                break;
            case R.id.iv_b_expand://扩展文件夹
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
                }
                break;
            case R.id.ll_b_b_select://选择b
                if (mTitleType.equals(mTitleTypeOnly)) {
                    mSelectOnlyitem = B;
                    //单选/多选
                    setSelectOnlyItemBG(false, true, false, false, false, mSelectViewBgZC);
                } else if (mTitleType.equals(mTitleTypeMore)) {
                    mSelectMorItemB = B;
                }
                break;
            case R.id.ll_b_cselect://选择c
                if (mTitleType.equals(mTitleTypeOnly)) {
                    mSelectOnlyitem = C;
                    //单选/多选
                    setSelectOnlyItemBG(false, false, true, false, false, mSelectViewBgZC);
                } else if (mTitleType.equals(mTitleTypeMore)) {
                    mSelectMorItemC = C;
                }
                break;
            case R.id.ll_b_d_select://选择d
                if (mTitleType.equals(mTitleTypeOnly)) {
                    //单选/多选
                    mSelectOnlyitem = D;
                    setSelectOnlyItemBG(false, false, false, true, false, mSelectViewBgZC);
                } else if (mTitleType.equals(mTitleTypeMore)) {
                    mSelectMorItemD = D;
                }
                break;
            case R.id.ll_b_e_select://选择e
                if (mTitleType.equals(mTitleTypeOnly)) {
                    mSelectOnlyitem = E;
                    //单选/多选
                    setSelectOnlyItemBG(false, false, false, false, true, mSelectViewBgZC);
                } else if (mTitleType.equals(mTitleTypeMore)) {
                    mSelectMorItemE = E;

                }
                break;
            default:
            case R.id.btn_b_buy://购买

                break;
            case R.id.btn_b_sure_key://多选确认




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
                vo.setItem(mSelectOnlyitem);
            }
            if (!StringUtil.isEmpty(mSelectMorItemA)) {
                vo.setSelectItemA(mSelectMorItemA);
            }
            if (!StringUtil.isEmpty(mSelectMorItemB)) {
                vo.setSelectItemB(mSelectMorItemB);
            }
            if (!StringUtil.isEmpty(mSelectMorItemC)) {
                vo.setSelectItemC(mSelectMorItemC);
            }
            if (!StringUtil.isEmpty(mSelectMorItemD)) {
                vo.setSelectItemD(mSelectMorItemD);
            }
            if (!StringUtil.isEmpty(mSelectMorItemE)) {
                vo.setSelectItemE(mSelectMorItemE);
            }
            mSeletList.add(vo);
            sp.putListAdd(mSeletList);
            //清空选项
            clearSeletItem();
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


    private void setSelectOnlyItemBG(boolean a, boolean b, boolean c, boolean d, boolean e, String day) {
        setImgBg(mIvBA, a, R.drawable.ic_b_a_s, R.drawable.ic_b_a_n);
        setImgBg(mIvBB, b, R.drawable.ic_b_b_s, R.drawable.ic_b_b_n);
        setImgBg(mIvBC, c, R.drawable.ic_b_c_s, R.drawable.ic_b_c_n);
        setImgBg(mIvBD, d, R.drawable.ic_b_d_s, R.drawable.ic_b_d_n);
        setImgBg(mIvBE, e, R.drawable.ic_b_e_s, R.drawable.ic_b_e_n);
/*        setLibg(mLlBASelect, a);
        setLibg(mLlBBSelect, b);
        setLibg(mLlBCselect, c);
        setLibg(mLlBDSelect, d);
        setLibg(mLlBESelect, e);*/
    }


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

    private void setImgBg(ImageView iv, boolean is, int selectid, int unselectid) {
        if (is) {
            iv.setImageResource(selectid);
        } else {
            iv.setImageResource(unselectid);
        }

    }


    private void setLibg(LinearLayout la, boolean is) {
        if (is) {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
        } else {
            la.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedSeletResultListUtil.getInstance().DeleteUser();
    }
}

