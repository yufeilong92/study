package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/18 20:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeContentAdapter extends BaseRecyclerAdapter<HomeContentAdapter.ViewHolder> {
    
    private ArrayList<Object> mData;
    private Context mContext;
    /**
     *
     * @param mData 数据
     * @param mContext
     */
    public HomeContentAdapter(Context mContext,ArrayList<Object> mData ) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public HomeContentAdapter.ViewHolder getViewHolder(View view) {
        return null;
    }

    @Override
    public HomeContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return null;
    }

    @Override
    public void onBindViewHolder(HomeContentAdapter.ViewHolder holder, int position, boolean isItem) {

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
