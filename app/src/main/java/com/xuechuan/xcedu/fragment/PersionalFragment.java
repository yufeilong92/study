package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.ui.net.NetBookDownActivity;
import com.xuechuan.xcedu.ui.net.NetBookDownOverActivity;
import com.xuechuan.xcedu.ui.user.AddVauleActivity;
import com.xuechuan.xcedu.ui.user.FeedBackActivity;
import com.xuechuan.xcedu.ui.user.GenuineActivity;
import com.xuechuan.xcedu.ui.user.PersionActivity;
import com.xuechuan.xcedu.ui.user.SettingActivity;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PersionalFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 我的信息
 * @author: L-BackPacker
 * @date: 2018/4/11 17:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class PersionalFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageView mIvMHear;
    private TextView mTvMName;
    private ImageView mIvMEdith;
    private TextView mTvMPhone;
    private LinearLayout mLlMHear;
    private LinearLayout mLlMMyMsg;
    private LinearLayout mLlMDown;
    private LinearLayout mLlMOrder;
    private LinearLayout mLlMAddvaluer;
    private LinearLayout mLlMGenuine;
    private LinearLayout mLlMFeedback;
    private LinearLayout mLlMNotice;
    private LinearLayout mLlMSetting;
    private Context mContext;

    public PersionalFragment() {
    }

    public static PersionalFragment newInstance(String param1, String param2) {
        PersionalFragment fragment = new PersionalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_persional, container, false);
//        initView(view);
//        return view;
//    }


    @Override
    protected int initInflateView() {
        return R.layout.fragment_persional;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            return;
        }
        UserBean user = userInfom.getData().getUser();
        mTvMName.setText(user.getNickname());
        mTvMPhone.setText(user.getPhone());
        if (!StringUtil.isEmpty(user.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvMHear, user.getHeadicon(), true);
        }
    }

    private void initView(View view) {
        mIvMHear = (ImageView) view.findViewById(R.id.iv_m_hear);
        mIvMHear.setOnClickListener(this);
        mTvMName = (TextView) view.findViewById(R.id.tv_m_name);
        mIvMEdith = (ImageView) view.findViewById(R.id.iv_m_edith);
        mIvMEdith.setOnClickListener(this);
        mTvMPhone = (TextView) view.findViewById(R.id.tv_m_phone);
        mLlMHear = (LinearLayout) view.findViewById(R.id.ll_m_hear);
        mLlMHear.setOnClickListener(this);
        mLlMMyMsg = (LinearLayout) view.findViewById(R.id.ll_m_my_msg);
        mLlMMyMsg.setOnClickListener(this);
        mLlMDown = (LinearLayout) view.findViewById(R.id.ll_m_down);
        mLlMDown.setOnClickListener(this);
        mLlMOrder = (LinearLayout) view.findViewById(R.id.ll_m_order);
        mLlMOrder.setOnClickListener(this);
        mLlMAddvaluer = (LinearLayout) view.findViewById(R.id.ll_m_addvaluer);
        mLlMAddvaluer.setOnClickListener(this);
        mLlMGenuine = (LinearLayout) view.findViewById(R.id.ll_m_genuine);
        mLlMGenuine.setOnClickListener(this);
        mLlMFeedback = (LinearLayout) view.findViewById(R.id.ll_m_feedback);
        mLlMFeedback.setOnClickListener(this);
        mLlMNotice = (LinearLayout) view.findViewById(R.id.ll_m_notice);
        mLlMNotice.setOnClickListener(this);
        mLlMSetting = (LinearLayout) view.findViewById(R.id.ll_m_setting);
        mLlMSetting.setOnClickListener(this);
        mContext = getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_m_edith://编辑
                Intent intent = new Intent(mContext, PersionActivity.class);
                intent.putExtra(PersionActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.persion));
                startActivity(intent);
                break;
            case R.id.ll_m_my_msg://我的信息
                break;
            case R.id.ll_m_down://我的下载
                startActivity(new Intent(mContext, NetBookDownActivity.class));
                break;
            case R.id.ll_m_order://我的订单

                break;
            case R.id.ll_m_addvaluer://增值服务
                Intent intent1 = new Intent(mContext, AddVauleActivity.class);
                intent1.putExtra(AddVauleActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.addvuale));
                startActivity(intent1);
                break;
            case R.id.ll_m_genuine://正版验证
                Intent intent2 = new Intent(mContext, GenuineActivity.class);
                intent2.putExtra(GenuineActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.authentic_verification));
                startActivity(intent2);
                break;
            case R.id.ll_m_feedback://用户反馈
                Intent intent3 = new Intent(mContext, FeedBackActivity.class);
                intent3.putExtra(FeedBackActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.feedbBack));
                startActivity(intent3);
                break;
            case R.id.ll_m_notice://系统通告
                break;
            case R.id.ll_m_setting://设置
                Intent intent4 = new Intent(mContext, SettingActivity.class);
                intent4.putExtra(SettingActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.setting));
                startActivity(intent4);
                break;
            default:

        }
    }
}
