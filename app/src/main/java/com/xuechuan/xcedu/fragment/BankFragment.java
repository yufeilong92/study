package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.BankFragmentAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.utils.ArrayToListUtil;

import java.util.ArrayList;

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

    private String mParam1;
    private String mParam2;
    private TabLayout mTabTitleTab;
    private ViewPager mVpgContent;
    private ArrayList<Fragment> list;
    private Context mContext;


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

    }

    @Override
    protected void initViewCreate(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();

        SkillFragment skillFragment = new SkillFragment();
        ColligateFragment colligateFragment = new ColligateFragment();
        CaseFragment caseFragment = new CaseFragment();
        list.add(skillFragment);
        list.add(colligateFragment);
        list.add(caseFragment);
        final ArrayList<String> mTabs = ArrayToListUtil.arraytoList(mContext, R.array.bank_tab);


        FragmentManager manager = getFragmentManager();
        BankFragmentAdapter fragmentAdapter = new BankFragmentAdapter(manager, mContext, list, mTabs);
        mVpgContent.setAdapter(fragmentAdapter);
        mTabTitleTab.setupWithViewPager(mVpgContent);
        for (int i = 0; i < fragmentAdapter.getCount(); i++) {
            TabLayout.Tab tab = mTabTitleTab.getTabAt(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
            tab.setCustomView(view);
            if (i == 0) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setTextColor(mContext.getResources().getColor(R.color.black));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                ivline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_tab_line_s));
                tv.setText(mTabs.get(i));

            }else {
            View customView = tab.getCustomView();
            TextView tv = customView.findViewById(R.id.tv_tab_titele);
            tv.setTextColor(mContext.getResources().getColor(R.color.hint_text));
            ImageView ivline = customView.findViewById(R.id.iv_tab_line);
            ivline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_tab_line_n));
            tv.setText(mTabs.get(i));
            }
        }


        mTabTitleTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setSelected(true);
                tv.setTextColor(mContext.getResources().getColor(R.color.black));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                ivline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_tab_line_s));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setTextColor(mContext.getResources().getColor(R.color.hint_text));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                tv.setSelected(false);
                ivline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_tab_line_n));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVpgContent.setCurrentItem(0);
    }


    @Override
    protected int initInflateView() {
        return R.layout.fragment_bank;
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_bank, null);
        initView(view);
        return view;
    }*/

    private void initView(View view) {
        mTabTitleTab = (TabLayout) view.findViewById(R.id.tab_title_tab);
        mTabTitleTab.setOnClickListener(this);
        mVpgContent = (ViewPager) view.findViewById(R.id.vpg_content);
        mVpgContent.setOnClickListener(this);
        mContext = getActivity();
    }

    @Override
    public void onClick(View v) {

    }

}
