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
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.MyMsgVo;

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
        MyMsgVo.DatasBean datas = (MyMsgVo.DatasBean) mData.get(position);
        int notifytype = datas.getNotifytype();
        if (notifytype == 1) {
            holder.mTvMPType.setText("回复了你");
            holder.mTvMPLook.setVisibility(View.VISIBLE);
        } else if (notifytype == 2) {
            holder.mTvMPType.setText("赞了你");
            holder.mTvMPLook.setVisibility(View.GONE);
        }
        if (!StringUtil.isEmpty(datas.getMemberheadicon()))
            MyAppliction.getInstance().displayImages(holder.mIvMyMsgImg, datas.getMemberheadicon(), true);
        holder.mTvMyMsgName.setText(datas.getMembername());
        holder.mTvMyMsgContent.setText(datas.getContent());
        holder.mTvMyMsgTime.setText(TimeUtil.getYMDT(datas.getCreatetime()));


    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvMyMsgImg;
        public TextView mTvMyMsgName;
        public TextView mTvMPType;
        public TextView mTvMyMsgTime;
        public TextView mTvMyMsgContent;
        public TextView mTvMPLook;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvMyMsgImg = (ImageView) itemView.findViewById(R.id.iv_my_msg_img);
            this.mTvMyMsgName = (TextView) itemView.findViewById(R.id.tv_my_msg_name);
            this.mTvMPType = (TextView) itemView.findViewById(R.id.tv_m_p_type);
            this.mTvMyMsgTime = (TextView) itemView.findViewById(R.id.tv_my_msg_time);
            this.mTvMyMsgContent = (TextView) itemView.findViewById(R.id.tv_my_msg_content);
            this.mTvMPLook = (TextView) itemView.findViewById(R.id.tv_m_p_look);
        }
    }


}
