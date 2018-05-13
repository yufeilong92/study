package com.xuechuan.xcedu.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 网课首页适配器
 * @author: L-BackPacker
 * @date: 2018/5/13 16:09
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetHomeAdapter extends RecyclerView.Adapter<NetHomeAdapter.ViewHoler> {
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ViewHoler(View itemView) {
            super(itemView);
        }
    }
}
