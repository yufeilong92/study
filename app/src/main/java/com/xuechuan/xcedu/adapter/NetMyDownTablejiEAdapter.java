package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.ItemSelectVo;
import com.xuechuan.xcedu.vo.SelectVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 节点适配器
 * @author: L-BackPacker
 * @date: 2018/5/15 15:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class NetMyDownTablejiEAdapter extends BaseRecyclerAdapter<NetMyDownTablejiEAdapter.ViewHolder> {
    private final int mFatherPosition;
    private Context mContext;
    private List<VideosBeanVo> mData;
    private final LayoutInflater mInflater;
    private List<SelectVo> mSelect;

    public NetMyDownTablejiEAdapter(Context mContext, List<VideosBeanVo> mData, int postion,
                                    List<SelectVo> mSelect) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        this.mFatherPosition = postion;
        this.mSelect = mSelect;

    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(VideosBeanVo vo, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_net_mydowntable_rlv_jie, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final VideosBeanVo vo = mData.get(position);
        holder.mTvNetTitle.setText(vo.getVideoname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
                }
            }
        });

    }


    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvNetTitle;
        public TextView mTvPoPDownStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_mydowntitle);
            this.mTvPoPDownStatus = (TextView) itemView.findViewById(R.id.tv_pop_down_status);
        }
    }


}
