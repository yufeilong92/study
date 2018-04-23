package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.BookHomePageVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 教程章
 * @author: L-BackPacker
 * @date: 2018/4/19 16:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookOrderAdapter extends RecyclerView.Adapter<BookOrderAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<BookHomePageVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BookOrderAdapter(Context mContext, List<BookHomePageVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bookhome_order_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookHomePageVo.DatasBean datasBean = mData.get(position);
        holder.mTvBookhomeOrder.setText( datasBean.getTitle());

        holder.itemView.setTag(datasBean);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvBookhomeOrder;
//        public TextView mTvBookhomeOrderContent;
       public ViewHolder(View itemView) {
            super(itemView);
           this.mTvBookhomeOrder = (TextView) itemView.findViewById(R.id.tv_bookhome_order);
//           this.mTvBookhomeOrderContent = (TextView) itemView.findViewById(R.id.tv_bookhome_order_content);
        }
    }

  
}
