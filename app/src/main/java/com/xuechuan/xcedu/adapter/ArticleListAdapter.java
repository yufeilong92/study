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
import com.xuechuan.xcedu.vo.ArticleVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 文章列表适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 19:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ArticleListAdapter extends BaseRecyclerAdapter<ArticleListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ArticleVo> mData;
    private final LayoutInflater mInflater;

    public ArticleListAdapter(Context mContext, List<ArticleVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
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
        View view = mInflater.inflate(R.layout.item_home_all_layout, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        ArticleVo vo = mData.get(position);
        if (StringUtil.isEmpty(vo.getThumbnailimg())) {
            holder.mIvItemHomeAll.setVisibility(View.GONE);
        } else {
            holder.mIvItemHomeAll.setVisibility(View.VISIBLE);
            MyAppliction.getInstance().displayImages(holder.mIvItemHomeAll,vo.getThumbnailimg(),false);
        }
        String titel="中兴遭美国“封杀”强烈震动了中国社会，舆论场上给出诸多反应，既包括对美国的做法很愤怒，又有些人认为中兴“遭此报应活该”，既有强大声音主张中国须以此为鉴，真正把本国半导体产业做强做优，又有不少很悲观的声音，认为中国“不可能斗得过美国”。";
        holder.mTvItemHomeLookAll.setText(vo.getStringViewcount());
        holder.mTvItemHomeTitleAll.setText(vo.getTitle());
        holder.mTvItemHomeTitleAll.setText(titel);
        holder.mTvItemHomeLaudAll.setText(vo.getSupportcount());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemHomeTitleAll;
        public ImageView mIvItemHomeAll;
        public TextView mTvItemHomeLookAll;
        public TextView mTvItemHomeLaudAll;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemHomeTitleAll = (TextView) itemView.findViewById(R.id.tv_item_home_title_all);
            this.mIvItemHomeAll = (ImageView) itemView.findViewById(R.id.iv_item_home_all);
            this.mTvItemHomeLookAll = (TextView) itemView.findViewById(R.id.tv_item_home_look_all);
            this.mTvItemHomeLaudAll = (TextView) itemView.findViewById(R.id.tv_item_home_laud_all);
        }
    }
}
