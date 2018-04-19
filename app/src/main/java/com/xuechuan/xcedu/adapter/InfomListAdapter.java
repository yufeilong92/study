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

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 列表适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class InfomListAdapter extends BaseRecyclerAdapter<InfomListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<Object> mData;
    private final LayoutInflater mInflater;

    public InfomListAdapter(Context mContext, ArrayList<Object> mData) {
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
        View view = mInflater.inflate(R.layout.item_infomlist_layout, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemInfomlistTitel;
        public ImageView mIvItemInfomlistContent;
        public TextView mTvItemInfomlistTime;
        public TextView mTvItemInfomSource;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemInfomlistTitel = (TextView) itemView.findViewById(R.id.tv_item_infomlist_titel);
            this.mIvItemInfomlistContent = (ImageView) itemView.findViewById(R.id.iv_item_infomlist_content);
            this.mTvItemInfomlistTime = (TextView) itemView.findViewById(R.id.tv_item_infomlist_time);
            this.mTvItemInfomSource = (TextView) itemView.findViewById(R.id.tv_item_infom_source);


        }
    }

}
