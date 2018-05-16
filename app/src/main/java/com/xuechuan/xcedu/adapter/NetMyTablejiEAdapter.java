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
import com.xuechuan.xcedu.Event.NetPlayEvent;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import org.greenrobot.eventbus.EventBus;

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
public class NetMyTablejiEAdapter extends BaseRecyclerAdapter<NetMyTablejiEAdapter.ViewHolder> {
    private final int faterperason;
    private Context mContext;
    private List<VideosBeanVo> mData;
    private final LayoutInflater mInflater;
    private CheckBox mChbNetPlay;
    private TextView mTvNetTitle;
    private ImageView mIvNetGoorbuy;
    private HashMap<Integer, HashMap<Integer, Boolean>> mSelectMap;
    private  NetMyTableAdapter netMyTableAdapter;

    public NetMyTablejiEAdapter(Context mContext, NetMyTableAdapter netMyTableAdapter, List<VideosBeanVo> mData, int postion, HashMap<Integer, HashMap<Integer, Boolean>> mSelectMap) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        this.mSelectMap = mSelectMap;
        this.faterperason = postion;
        this.netMyTableAdapter=netMyTableAdapter;

    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_net_mytable_rlv_jie, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final VideosBeanVo vo = mData.get(position);
        holder.mChbNetPlay.setVisibility(View.VISIBLE);
        holder.mTvNetTitle.setText(vo.getVideoname());
        final HashMap<Integer, Boolean> map = mSelectMap.get(faterperason);
        if (map.get(position)) {
            holder.mChbNetPlay.setChecked(true);
        } else {
            holder.mChbNetPlay.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mChbNetPlay.setChecked(true);
                map.put(position,true);
                mSelectMap.put(faterperason,map);
                netMyTableAdapter.setOnClick(mSelectMap);
//                EventBus.getDefault().postSticky(new NetMyPlayEvent(vo));
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

        public ViewHolder(View itemView) {
            super(itemView);
            this.mChbNetPlay = (CheckBox) itemView.findViewById(R.id.chb_net_myplay);
            this.mTvNetTitle = (TextView) itemView.findViewById(R.id.tv_net_mytitle);
        }
    }


}
