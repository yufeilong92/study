package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

import me.drakeet.materialdialog.MaterialDialog;

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
public class ShowDialog {

    private AlertDialog mDialog;
    private View mContentView = null;
    private static ShowDialog dialog;
    private static Context mContext;

    public interface ViewClickListener {
        public void viewOnClickListener(View view);
    }

    private ViewClickListener clickListener;

    public void setClickListener(ViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static ShowDialog getInstance(Context context) {
        mContext = context;
        if (dialog == null)
            dialog = new ShowDialog();
        return dialog;
    }

    /***
     *
     * @param title 标题
     * @param msg 信息
     * @param view 自定义view
     * @param cancelable 是否取消
     */
    public void Show(String title, String msg, int view, boolean cancelable) {
//        if (mContentView == null) {
        View    mContentView = LayoutInflater.from(mContext).inflate(view, null);
//        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg)
                .setView(mContentView)
                .setTitle(title)
                .setCancelable(cancelable)
                .create();
        if (mDialog == null || !mDialog.isShowing()) {
            mDialog = builder.show();
        }

    }

    /**
     * 设置自定义监听
     */
    public void setOnViewClick() {
        isEmpty();
        if (clickListener != null) {
            clickListener.viewOnClickListener(mContentView);
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        isEmpty();
        mDialog.cancel();
    }


    private void isEmpty() {
        if (mDialog == null) {
            throw new NullPointerException("请选创建dialog");
        }
        /*if (mContentView == null) {
            throw new NullPointerException("自定义视图为空");
        }*/
    }

    /**
     * @param titel 标题
     * @param cont  内容
     * @return
     */
    public AlertDialog showDialog(String titel, String cont) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_dialog, null);
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
        mDialog = builder.show();
        return mDialog;
    }

//
//    /**
//     * @param titel 标题
//     * @param cont  内容
//     * @return
//     */
//    public MaterialDialog showDialog(String titel, String cont) {
//        MaterialDialog dialog = new MaterialDialog(mContext);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_dialog, null);
//        TextView tv_msg = view.findViewById(R.id.tv_msg);
//        TextView tv_title = view.findViewById(R.id.tv_dialog_title);
//
//        if (!StringUtil.isEmpty(titel)) {
//            tv_title.setVisibility(View.VISIBLE);
//            tv_title.setText(titel);
//        } else {
//            tv_title.setVisibility(View.GONE);
//        }
//        tv_title.setText(cont);
//        dialog.setContentView(view);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//        return dialog;
//    }
}
