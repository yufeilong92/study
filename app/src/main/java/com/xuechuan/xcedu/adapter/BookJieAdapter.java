package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.ChildrenBeanXVo;

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
public class BookJieAdapter extends RecyclerView.Adapter<BookJieAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ChildrenBeanXVo> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;


    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BookJieAdapter(Context mContext, List<ChildrenBeanXVo> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ChildrenBeanXVo vo = mData.get(position);
        holder.mTvItemJieTitle.setText(vo.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isend = vo.isIsend();
                if (!isend) {
                    int visibility = holder.mRlvJieContent.getVisibility();
                    if (visibility == View.GONE) {
                        holder.mRlvJieContent.setVisibility(View.VISIBLE);
                        requestJieData(holder, vo);
                    } else {
                        holder.mRlvJieContent.setVisibility(View.GONE);
                    }
                }
            }
        });
//
//        holder.itemView.setId(position);
//        holder.itemView.setTag(vo);

    }

    /**
     * 请求节点下是否有数据
     *
     * @param holder
     * @param vo
     */
    private void requestJieData(ViewHolder holder, ChildrenBeanXVo vo) {
        BookJieJieAdapter jieJieAdapter = new BookJieJieAdapter(mContext, vo.getChildren());
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvJieContent.setLayoutManager(manager);
        holder.mRlvJieContent.setAdapter(jieJieAdapter);
        jieJieAdapter.setClickListener(new BookJieJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ChildrenBeanVo vo = (ChildrenBeanVo) obj;
                String gourl = vo.getGourl();
                InfomActivity.newInstance(mContext, gourl);
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
