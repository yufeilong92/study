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

    private String mParam1;
    private String mParam2;
    private TabLayout mTabTitleTab;
    private ViewPager mVpgContent;
    private ArrayList<Fragment> list;


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
//        requestChapters();
        initView(view);
        initData(null);
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
                    initData(datas);
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

    private void initData(List<BookHomeVo.DatasBean> datas) {
        list = new ArrayList<>();
        SkillFragment skillFragment = null;
        ColligateFragment colligateFragment = null;
        CaseFragment caseFragment = null;
     /*   for (int i = 0; i < datas.size(); i++) {
            BookHomeVo.DatasBean datasBean = datas.get(i);
            if (datasBean.getName().equals("技术实务")) {
                skillFragment = SkillFragment.newInstance(String.valueOf(datasBean.getId()));
            } else if (datasBean.getName().equals("综合能力")) {
                colligateFragment = ColligateFragment.newInstance(String.valueOf(datasBean.getId()));

            } else if (datasBean.getName().equals("案例分析")) {
                caseFragment = CaseFragment.newInstance(String.valueOf(datasBean.getId()));
            }
        }*/
        skillFragment = SkillFragment.newInstance("");
        colligateFragment = ColligateFragment.newInstance("");
        caseFragment = CaseFragment.newInstance("");
        list.add(skillFragment);
        list.add(colligateFragment);
        list.add(caseFragment);

        final ArrayList<String> mTabs = ArrayToListUtil.arraytoList(getContext(), R.array.bank_tab);
        FragmentManager manager = getFragmentManager();
        BankFragmentAdapter fragmentAdapter = new BankFragmentAdapter(manager, getContext(), list, mTabs);
        mVpgContent.setAdapter(fragmentAdapter);
        mTabTitleTab.setupWithViewPager(mVpgContent);
        for (int i = 0; i < fragmentAdapter.getCount(); i++) {
            TabLayout.Tab tab = mTabTitleTab.getTabAt(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
            tab.setCustomView(view);
            if (i == 0) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setTextColor(getContext().getResources().getColor(R.color.black));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                ivline.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_tab_line_s));
                tv.setText(mTabs.get(i));

            } else {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setTextColor(getContext().getResources().getColor(R.color.hint_text));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                ivline.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_tab_line_n));
                tv.setText(mTabs.get(i));
            }
        }


        mTabTitleTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setSelected(true);
                tv.setTextColor(getContext().getResources().getColor(R.color.black));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                ivline.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_tab_line_s));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tv = customView.findViewById(R.id.tv_tab_titele);
                tv.setTextColor(getContext().getResources().getColor(R.color.hint_text));
                ImageView ivline = customView.findViewById(R.id.iv_tab_line);
                tv.setSelected(false);
                ivline.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_tab_line_n));
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
    }

    @Override
    public void onClick(View v) {

    }

}
