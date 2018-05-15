package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.Event.NetPlayEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetTablejiEAdapter extends BaseRecyclerAdapter<NetTablejiEAdapter.ViewHolder> {
    private Context mContext;
    private List<VideosBeanVo> mData;
    private final LayoutInflater mInflater;
    private CheckBox mChbNetPlay;
    private TextView mTvNetTitle;
    private ImageView mIvNetGoorbuy;

    public NetTablejiEAdapter(Context mContext, List<VideosBeanVo> mData) {
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
        View view = mInflater.inflate(R.layout.item_net_table_rlv_jie, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final VideosBeanVo vo = mData.get(position);
        if (vo.isIstrysee()) {
            holder.mIvNetGoorbuy.setVisibility(View.GONE);
        } else {
            holder.mIvNetGoorbuy.setImageResource(R.mipmap.ic_login_password);
        }
        holder.mChbNetPlay.setChecked(false);
        holder.mChbNetPlay.setVisibility(View.VISIBLE);
        holder.mTvNetTitle.setText(vo.getVideoname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mChbNetPlay.setChecked(true);
                EventBus.getDefault().postSticky(new NetPlayEvent(vo));
            }
        });



    }


    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mChbNetPlay;
        public TextView mTvNetTitle;
        public ImageView mIvNetGoorbuy;
        public RecyclerView mRlvNetBookJie;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mRlvNetBookJie = (RecyclerView) itemView.findViewById(R.id.rlv_net_book_jie);
            this.mChbNetPlay = (CheckBox) itemView.findViewById(R.id.chb_net_play);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_title);
            this.mIvNetGoorbuy = (ImageView) itemView.findViewById(R.id.iv_net_goorbuy);
        }
    }


}
