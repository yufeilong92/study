package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 我的消息
 * @author: L-BackPacker
 * @date: 2018/5/30 18:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyMsgAdapter extends BaseRecyclerAdapter<MyMsgAdapter.ViewHolder> {

    private Context mContext;
    private List mData;
    private final LayoutInflater mInflater;

    public MyMsgAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_my_msg, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {

    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvMyMsgImg;
        public TextView mTvMyMsgTitle;
        public TextView mTvMyMsgTime;
        public TextView mTvMyMsgFrom;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvMyMsgImg = (ImageView) itemView.findViewById(R.id.iv_my_msg_img);
            this.mTvMyMsgTitle = (TextView) itemView.findViewById(R.id.tv_my_msg_title);
            this.mTvMyMsgTime = (TextView) itemView.findViewById(R.id.tv_my_msg_time);
            this.mTvMyMsgFrom = (TextView) itemView.findViewById(R.id.tv_my_msg_from);
        }
    }


}
