package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuechuan.xcedu.R;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: 弹窗
 * @author: L-BackPacker
 * @date: 2018/4/12 16:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DialogUtil {

    private static DialogUtil dialogUtil;

    public DialogUtil() {
    }

    public static DialogUtil getInstance() {
        if (dialogUtil == null)
            dialogUtil = new DialogUtil();
        return dialogUtil;
    }

    /**
     * @param context
     * @param titel   标题
     * @param cont    内容
     * @return
     */
    public static AlertDialog showDialog(Context context, String titel, String cont) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_dialog, null);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        TextView tv_title = view.findViewById(R.id.tv_dialog_title);

        if (!StringUtil.isEmpty(titel)) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(titel);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        tv_msg.setText(cont);
        builder.setView(view)
                .setCancelable(false)
                .create();
        return builder.show();
    }

    /**
     * 继续答题
     */
    private onContincueClickListener continueClickListener;

    public interface onContincueClickListener {
        public void onCancelClickListener();

        public void onNextClickListener();
    }

    public void setContinueClickListener(onContincueClickListener continueClickListener) {
        this.continueClickListener = continueClickListener;
    }

    /**
     * 交卷
     */
    private onSubmitClickListener submitclickListener;

    public interface onSubmitClickListener {
        public void onCancelClickListener();

        public void oSubmitClickListener();
    }

    public void setSubmitClickListener(onSubmitClickListener clickListener) {
        this.submitclickListener = clickListener;
    }

    /**
     * 交卷
     */
    private onStopClickListener stopclicklistener;

    public interface onStopClickListener {

        public void onNextClickListener();
    }

    public void setStopClickListener(onStopClickListener clickListener) {
        this.stopclicklistener = clickListener;
    }

    /**
     * 继续答题
     *
     * @param context
     * @param page    第几题
     * @return
     */
    public void showContinueDialog(Context context, String page) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_continue, null);
        TextView tv = view.findViewById(R.id.tv_number);
        tv.setText(page);
        builder.setView(view)
                .setCancelable(false)
                .create();
        final AlertDialog show = builder.show();
        Button btnAgain = view.findViewById(R.id.btn_dialog_again);
        Button butGo = view.findViewById(R.id.btn_dialog_next);
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueClickListener != null)
                    show.dismiss();
                continueClickListener.onCancelClickListener();

            }
        });
        butGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueClickListener != null) {
                    show.dismiss();
                    continueClickListener.onNextClickListener();
                }
            }
        });


    }

    /**
     * 交卷
     *
     * @param context
     * @return
     */
    public void showSubmitDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_submit, null);
        builder.setView(view)
                .setCancelable(true)
                .create();
        final AlertDialog dialog = builder.show();
        Button btnAgain = view.findViewById(R.id.btn_dialog_cancel);
        Button butGo = view.findViewById(R.id.btn_dialog_submit);
        btnAgain.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (submitclickListener != null) {
                    dialog.dismiss();
                    submitclickListener.onCancelClickListener();
                }

            }
        });
        butGo.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {
                if (submitclickListener != null) {
                    dialog.dismiss();
                    submitclickListener.oSubmitClickListener();
                }
            }
        });


    }

    /**
     * 时间暂停
     *
     * @param context
     * @return
     */
    public void showStopDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_time, null);
        builder.setView(view)
                .setCancelable(false)
                .create();
        final AlertDialog dialog = builder.show();
        Button butGo = view.findViewById(R.id.btn_stop_next);
        butGo.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (stopclicklistener != null) {
                    dialog.dismiss();
                    stopclicklistener.onNextClickListener();
                }

            }
        });


    }

    private onTitleClickListener titelclickListener;

    public interface onTitleClickListener {
        public void onSureClickListener();

        public void onCancelClickListener();
    }

    public void setTitleClickListener(onTitleClickListener clickListener) {
        this.titelclickListener = clickListener;
    }

    /**
     * 普通标题
     *
     * @param context
     * @return
     */
    public void showTitleDialog(Context context, String title, String btnSure, String cancale,boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_title, null);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(title+"?");
        Button sure = view.findViewById(R.id.btn_sure);
        sure.setText(btnSure);
        Button cancel = view.findViewById(R.id.btn_cancal);
        cancel.setText(cancale);
        builder.setView(view)
                .setCancelable(cancelable)
                .create();
        final AlertDialog show = builder.show();
        sure.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    show.dismiss();
                    titelclickListener.onSureClickListener();

                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {
                if (titelclickListener != null) {
                    show.dismiss();
                    titelclickListener.onCancelClickListener();
                }
            }
        });
    }


}
