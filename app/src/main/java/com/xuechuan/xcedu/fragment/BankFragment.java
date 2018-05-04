package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.BankFragmentAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BookHomeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BankFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 题库
 * @author: L-BackPacker
 * @date: 2018/4/11 17:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class BankFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    private String mParam1;
    private String mParam2;

    private ArrayList<Fragment> mlist;
    private CheckBox mChbBSkill;
    private ImageView mIvBLineBg;
    private CheckBox mChbBColled;
    private ImageView mIvBLineBg1;
    private CheckBox mChbBCase;
    private ImageView mIvBLineBg2;
    private ViewPager mVpgContent;


    public BankFragment() {
    }

    public static BankFragment newInstance(String param1, String param2) {
        BankFragment fragment = new BankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        requestChapters();
        initView(view);
        initViewData();
    }

    private void initViewData() {
        mlist = new ArrayList<>();
        SkillFragment skillFragment   = SkillFragment.newInstance("1");
        ColligateFragment colligateFragment = ColligateFragment.newInstance("2");
        CaseFragment caseFragment = CaseFragment.newInstance("3");
        mlist.add(skillFragment);
        mlist.add(colligateFragment);
        mlist.add(caseFragment);
        final ArrayList<String> mTabs = ArrayToListUtil.arraytoList(mContext, R.array.bank_tab);
        FragmentManager manager = getFragmentManager();
        BankFragmentAdapter fragmentAdapter = new BankFragmentAdapter(manager, mContext, mlist, mTabs);
        mVpgContent.setAdapter(fragmentAdapter);
        mVpgContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    selectTabBg(true, false, false);
                } else if (position == 1) {
                    selectTabBg(false, true, false);
                } else if (position == 2) {
                    selectTabBg(false, false, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mVpgContent.setCurrentItem(0);
        selectTabBg(true, false, false);
    }


    /**
     * 请求章节题目
     */
    private void requestChapters() {
        HomeService service = new HomeService(getContext());
/*        service.setIsShowDialog(true);
        service.setDialogContext(getContext(), "", getStrWithId(R.string.loading));*/
        service.requestCourse(1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.d("教程首页内容", message);
                Gson gson = new Gson();
                BookHomeVo vo = gson.fromJson(message, BookHomeVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<BookHomeVo.DatasBean> datas = vo.getDatas();
                    bindTab(datas);
                } else {
                    T.showToast(getContext(), vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                L.w(response.message());
            }
        });
    }

    private void bindTab(List<BookHomeVo.DatasBean> datas) {

    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_bank;
    }

    private void initView(View view) {
        mContext = getActivity();

        mChbBSkill = (CheckBox) view.findViewById(R.id.chb_b_skill);
        mChbBSkill.setOnClickListener(this);
        mIvBLineBg = (ImageView) view.findViewById(R.id.iv_b_line_bg);
        mIvBLineBg.setOnClickListener(this);
        mChbBColled = (CheckBox) view.findViewById(R.id.chb_b_Colled);
        mChbBColled.setOnClickListener(this);
        mIvBLineBg1 = (ImageView) view.findViewById(R.id.iv_b_line_bg1);
        mIvBLineBg1.setOnClickListener(this);
        mChbBCase = (CheckBox) view.findViewById(R.id.chb_b_case);
        mChbBCase.setOnClickListener(this);
        mIvBLineBg2 = (ImageView) view.findViewById(R.id.iv_b_line_bg2);
        mIvBLineBg2.setOnClickListener(this);
        mVpgContent = (ViewPager) view.findViewById(R.id.vpg_content);
        mVpgContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chb_b_skill:
                mVpgContent.setCurrentItem(0, true);
                break;
            case R.id.chb_b_Colled:
                mVpgContent.setCurrentItem(1, true);
                break;
            case R.id.chb_b_case:
                mVpgContent.setCurrentItem(2, true);
                break;
            default:

        }
    }

    private void selectTabBg(boolean skill, boolean co, boolean cased) {
        mChbBSkill.setChecked(skill);
        mChbBColled.setChecked(co);
        mChbBCase.setChecked(cased);
        Drawable homeDrawable;
        if (skill) {
            homeDrawable = getResources().getDrawable(R.drawable.ic_tab_line_s);
        } else {
            homeDrawable = getResources().getDrawable(R.drawable.ic_tab_line_n);
        }
        mIvBLineBg.setImageDrawable(homeDrawable);
        Drawable banDrawable;
        if (co) {
            banDrawable = getResources().getDrawable(R.drawable.ic_tab_line_s);
        } else {
            banDrawable = getResources().getDrawable(R.drawable.ic_tab_line_n);
        }
        mIvBLineBg1.setImageDrawable(banDrawable);
        Drawable netDrawable;
        if (cased) {
            netDrawable = getResources().getDrawable(R.drawable.ic_tab_line_s);
        } else {
            netDrawable = getResources().getDrawable(R.drawable.ic_tab_line_n);
        }
        mIvBLineBg2.setImageDrawable(netDrawable);

    }
}
