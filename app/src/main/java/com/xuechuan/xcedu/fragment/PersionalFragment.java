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

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.mvp.contract.PersionInfomContract;
import com.xuechuan.xcedu.mvp.model.PersionInfomModel;
import com.xuechuan.xcedu.mvp.presenter.PersionInfomPresenter;
import com.xuechuan.xcedu.ui.me.AddVauleActivity;
import com.xuechuan.xcedu.ui.me.FeedBackActivity;
import com.xuechuan.xcedu.ui.me.GenuineActivity;
import com.xuechuan.xcedu.ui.me.MyMsgActivity;
import com.xuechuan.xcedu.ui.me.MyOrderActivity;
import com.xuechuan.xcedu.ui.me.PersionActivity;
import com.xuechuan.xcedu.ui.me.SettingActivity;
import com.xuechuan.xcedu.ui.me.SystemMsgActivity;
import com.xuechuan.xcedu.ui.net.NetBookDownActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.PerInfomVo;

import java.util.List;

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
public class PersionalFragment extends BaseFragment implements View.OnClickListener, PersionInfomContract.View {
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
    private PerInfomVo.DataBean mDataInfom;
    private PersionInfomPresenter mPresenter;
    private ImageView mIvPersionImg;
    private ImageView mIvMPSystem;
    private ImageView mIvMPTiShi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persional, container, false);
        initView(view);
        return view;
    }
*/

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
        /*UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
        if (userInfom == null) {
            return;
        }
        UserBean user = userInfom.getData().getUser();
        mTvMName.setText(user.getNickname());
        mTvMPhone.setText(user.getPhone());
        if (!StringUtil.isEmpty(user.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvMHear, user.getHeadicon(), true);
        }*/
        if (mPresenter == null) {
            mPresenter = new PersionInfomPresenter();
            mPresenter.basePresenter(new PersionInfomModel(), this);
        }
    }

    private void initView(View view) {
        mContext = getActivity();
        mIvMHear = (ImageView) view.findViewById(R.id.iv_m_hear);
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

        mIvPersionImg = (ImageView) view.findViewById(R.id.iv_persion_img);
        mIvMPSystem = (ImageView) view.findViewById(R.id.iv_m_p_system);
        mIvMPSystem.setOnClickListener(this);
        mIvMPTiShi = (ImageView) view.findViewById(R.id.iv_m_p_ti_shi);
        mIvMPTiShi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_m_hear://编辑
                Intent intent = new Intent(mContext, PersionActivity.class);
                intent.putExtra(PersionActivity.PERINFOM, mDataInfom);
                intent.putExtra(PersionActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.persion));
                startActivity(intent);
                break;
            case R.id.ll_m_my_msg://我的信息
                Intent intent6 = new Intent(mContext, MyMsgActivity.class);
                intent6.putExtra(MyMsgActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.mymsg_notice));
                startActivity(intent6);
                break;
            case R.id.ll_m_down://我的下载
                startActivity(new Intent(mContext, NetBookDownActivity.class));
                break;
            case R.id.ll_m_order://我的订单
                startActivity(new Intent(mContext, MyOrderActivity.class));
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
                Intent intent5 = new Intent(mContext, SystemMsgActivity.class);
                intent5.putExtra(SystemMsgActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.system_notice));
                startActivity(intent5);
                break;
            case R.id.ll_m_setting://设置
                Intent intent4 = new Intent(mContext, SettingActivity.class);
                if (mDataInfom != null) {
                    List<PerInfomVo.DataBean.ThirdaccountBean> thirdaccount = mDataInfom.getThirdaccount();
                    if (thirdaccount != null && !thirdaccount.isEmpty()) {
                        intent4.putExtra(SettingActivity.WEIXINNAME, mDataInfom.getThirdaccount().get(0).getNickname());
                    }
                }
                intent4.putExtra(SettingActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.setting));
                startActivity(intent4);
                break;
            default:

        }
    }

    @Override
    public void InfomSuccess(String cont) {
        L.d(cont);
        Gson gson = new Gson();
        PerInfomVo vo = gson.fromJson(cont, PerInfomVo.class);
        if (vo == null) return;
        if (vo.getStatus()!=null&&vo.getStatus().getCode() == 200) {
            mDataInfom = vo.getData();
            MyAppliction.getInstance().setUserData(vo);
            bindViewData(mDataInfom);
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }

    }

    private void bindViewData(PerInfomVo.DataBean data) {
        mTvMName.setText(data.getNickname());
        mTvMPhone.setText(Utils.phoneData(data.getPhone()));
        if (!StringUtil.isEmpty(data.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvMHear, data.getHeadicon(), true);
        }
        if (data.isIshavemembernotify()) {
            mIvPersionImg.setImageResource(R.mipmap.m_icon_massage_n);
        } else {
            mIvPersionImg.setImageResource(R.mipmap.ic_m_massage);
        }

        if (data.isIshavesystemnotify()) {
            mIvMPTiShi.setImageResource(R.mipmap.common_rp);
        } else {
            mIvMPTiShi.setVisibility(View.GONE);
        }

    }

    @Override
    public void InfomError(String cont) {
        L.d(cont);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.reqeustMInfo(mContext);
    }
}
