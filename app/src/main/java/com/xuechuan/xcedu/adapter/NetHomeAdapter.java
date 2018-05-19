package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.CoursesBeanVo;

import java.util.List;

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
public class NetHomeAdapter extends RecyclerView.Adapter<NetHomeAdapter.ViewHoler> implements View.OnClickListener {

    private Context mContext;
    private List<CoursesBeanVo> mData;
    private final LayoutInflater mInflater;

    public NetHomeAdapter(Context mContext, List<CoursesBeanVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(CoursesBeanVo vo, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_net_my_home, null);
        view.setOnClickListener(this);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, final int position) {
        final CoursesBeanVo vo = mData.get(position);
        if (!StringUtil.isEmpty(vo.getCoverimg())) {
            MyAppliction.getInstance().displayImages(holder.mIvNetMyitemTitleImg, vo.getCoverimg(), false);
        }

        holder.mTvNetMyhomeTitle.setText(vo.getName());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
        // TODO: 2018/5/14 上次看到是时间

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((CoursesBeanVo) v.getTag(), v.getId());
        }
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ImageView mIvNetMyitemTitleImg;
        public TextView mTvNetMyhomeTitle;
        public TextView mTvNetMyhomePricle;

        public ViewHoler(View itemView) {
            super(itemView);
            this.mIvNetMyitemTitleImg = (ImageView) itemView.findViewById(R.id.iv_net_myitem_title_img);
            this.mTvNetMyhomeTitle = (TextView) itemView.findViewById(R.id.tv_net_myhome_title);
            this.mTvNetMyhomePricle = (TextView) itemView.findViewById(R.id.tv_net_myhome_pricle);

        }
    }

}