package com.xuechuan.xcedu.ui.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.weight.CommonPopupWindow;

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
    private CommonPopupWindow popupWindow;
    private CommonPopupWindow.LayoutGravity gravity;

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(courseid, courseid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_answer);
//        initView();
//        initData();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_answer);
        initView();
        initData();
    }

    private void initData() {
        requestMatter();
    }

    //请求题干
    private void requestMatter() {
        BankService bankService = new BankService(mContext);

    }


    private void initView() {
        mTvBType = (TextView) findViewById(R.id.tv_b_type);
        mTvBNumber = (TextView) findViewById(R.id.tv_b_number);
        mTvBMatter = (TextView) findViewById(R.id.tv_b_matter);
        mContext = this;
        mIvBMore = (ImageView) findViewById(R.id.iv_b_more);
        mIvBMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_b_more://设置
                showPopwindow();
                break;
            default:

        }
    }

    /**
     * 显示pop
     */
    private void showPopwindow() {
        setBackgroundAlpha(0.5f,AnswerActivity.this);
        popupWindow = new CommonPopupWindow(mContext, R.layout.popw_setting_layout, 400, WindowManager.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View contentView = getContentView();
                tv_settiong = contentView.findViewById(R.id.tv_b_setting);
                tv_share = contentView.findViewById(R.id.tv_b_share);
            }

            @Override
            protected void initEvent() {
                tv_settiong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.getPopupWindow().dismiss();
                         Toast.makeText(AnswerActivity.this,tv_settiong.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                    }
                });
                tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.getPopupWindow().dismiss();
                        Toast.makeText(AnswerActivity.this,tv_share.getText().toString().trim(),Toast.LENGTH_SHORT).show();

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
                        setBackgroundAlpha(1.0f,AnswerActivity.this);
                    }
                });
            }
        };
        gravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
        popupWindow.showBashOfAnchor(mIvBMore, gravity, 0, 0);
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
}

