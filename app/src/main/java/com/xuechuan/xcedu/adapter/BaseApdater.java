package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 16:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BaseApdater extends RecyclerView.Adapter<BaseApdater.ViewhHOLE> {
    private Context mContext;
    private List<String> mData;
    private final LayoutInflater inflater;

    public BaseApdater(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewhHOLE onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewhHOLE hole = new ViewhHOLE(inflater.inflate(R.layout.item, null));
        return hole;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewhHOLE holder, int position) {
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewhHOLE extends RecyclerView.ViewHolder {
        public TextView mTv;

        public ViewhHOLE(View itemView) {
            super(itemView);
            this.mTv = (TextView) itemView.findViewById(R.id.tv);

        }
    }


}
