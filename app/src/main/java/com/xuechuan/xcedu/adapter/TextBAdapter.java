package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.bank.AnswerActivity;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.SkillTextVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 教程节适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 16:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class TextBAdapter extends RecyclerView.Adapter<TextBAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private   List<SkillTextVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private GridLayoutManager manager;
    private onItemClickListener clickListener;
    private ArrayList<Integer> mClickList =null;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public TextBAdapter(Context mContext, List<SkillTextVo.DatasBean>mData, GridLayoutManager manager) {
        this.mContext = mContext;
        this.mData = mData;
        this.manager = manager;
        mClickList=new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_jie_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
     final    SkillTextVo.DatasBean bean = mData.get(position);
        holder.mTvItemJieTitle.setText(bean.getTitle());
        if (mClickList.contains(position)) {
            holder.mRlvJieContent.setVisibility(View.VISIBLE);
            BindJieData(holder, bean.getChildren());
        } else {
            holder.mRlvJieContent.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isend = bean.isIsend();
                if (isend) {
//                    return;
                }
                if (!mClickList.contains(position)) {
                    mClickList.add(position);
                    holder.mRlvJieContent.setVisibility(View.VISIBLE);
                    BindJieData(holder, bean.getChildren());
                } else {
                    for (int i = 0; i < mClickList.size(); i++) {
                        if (mClickList.get(i) == position) {
                            mClickList.remove(i);
                            holder.mRlvJieContent.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });


    }

    /**
     * 请求节点下是否有数据
     *
     * @param holder
     * @param vo
     */
    private void BindJieData(ViewHolder holder, List<ChildrenBeanVo>vo) {
        BookJieJieAdapter jieJieAdapter = new BookJieJieAdapter(mContext, vo);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvJieContent.setLayoutManager(manager);
        holder.mRlvJieContent.setAdapter(jieJieAdapter);
        jieJieAdapter.setClickListener(new BookJieJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ChildrenBeanVo vo = (ChildrenBeanVo) obj;
                Intent intent = AnswerActivity.newInstance(mContext, String.valueOf(vo.getId())
                , DataMessageVo.MARKTYPEORDER);
                mContext.startActivity(intent);
            }
        });



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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemJieTitle;
        public RecyclerView mRlvJieContent;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTvItemJieTitle = (TextView) rootView.findViewById(R.id.tv_item_bookjie_title);
            this.mRlvJieContent = (RecyclerView) rootView.findViewById(R.id.rlv_bookjie_content);
        }

    }
}
