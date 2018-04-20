package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.DatasBeanVo;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;
import com.xuechuan.xcedu.vo.SpecasJieVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/20 18:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SpecsOrderAdapter extends BaseRecyclerAdapter<SpecsOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<DatasBeanVo> mData;
    private final LayoutInflater mInflater;

    public SpecsOrderAdapter(Context mContext, List<DatasBeanVo> mData) {
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
        View view = mInflater.inflate(R.layout.item_specas_order, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, boolean isItem) {
        final DatasBeanVo vo = mData.get(position);
        holder.mTvSpecsorderTitel.setText(vo.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mRlvItemSpecasContent.getVisibility() == View.VISIBLE) {
                    holder.mRlvItemSpecasContent.setVisibility(View.GONE);
                } else {
                    holder.mRlvItemSpecasContent.setVisibility(View.VISIBLE);
                }
                reqeustData(holder, vo);
            }
        });
    }

    private void reqeustData(final ViewHolder holder, DatasBeanVo vo) {
        HomeService homeService = HomeService.getInstance(mContext);
        homeService.requestarticle(String.valueOf(vo.getId()), new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.e(message);
                Gson gson = new Gson();
                SpecasJieVo jieVo = gson.fromJson(message, SpecasJieVo.class);
                if (jieVo.getStatus().getCode() == 200) {
                    bindData(holder, jieVo.getDatas());

                } else {
                    T.showToast(mContext, jieVo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {

            }
        });
    }

    private void bindData(ViewHolder holder, List<SpecasJieVo.DatasBean> datas) {
        SpecsJieAdapter adapter = new SpecsJieAdapter(mContext, datas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvItemSpecasContent.addItemDecoration(new DividerItemDecoration(mContext, com.xuechuan.xcedu.weight.DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        holder.mRlvItemSpecasContent.setLayoutManager(gridLayoutManager);
        holder.mRlvItemSpecasContent.setAdapter(adapter);
        adapter.setClickListener(new SpecsJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                SpecasJieVo.DatasBean vo= (SpecasJieVo.DatasBean) obj;
                InfomActivity.newInstance(mContext,vo.getGourl());
            }
        });


    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvSpecsorderTitel;
        public RecyclerView mRlvItemSpecasContent;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTvSpecsorderTitel = (TextView) rootView.findViewById(R.id.tv_specsorder_titel);
            this.mRlvItemSpecasContent = (RecyclerView) rootView.findViewById(R.id.rlv_item_specas_content);
        }

    }
}
