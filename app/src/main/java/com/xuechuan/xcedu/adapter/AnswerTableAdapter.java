package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.utils.SharedSeletResultListUtil;
import com.xuechuan.xcedu.vo.TitleNumberVo;
import com.xuechuan.xcedu.vo.UseSelectItemInfomVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/2 18:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerTableAdapter extends RecyclerView.Adapter<AnswerTableAdapter.ViewHolder> {

    private Context mContext;
    private List<TitleNumberVo.DatasBean> mData;
    private final LayoutInflater mInflater;
    private onItemClickListener clickListener;
    private int submit;
    private boolean isSubmit;
    private int select = -1;

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AnswerTableAdapter(Context mContext, List<TitleNumberVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setSubmit(boolean submit, int position) {
        this.isSubmit = submit;
        this.select = position;
        notifyDataSetChanged();
    }

    public void setSubmitResult(int submit) {
        this.submit = submit;
        notifyDataSetChanged();
    }

    public void setSelectItem(int position) {
        this.select = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.answer_layout_table, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TitleNumberVo.DatasBean bean = mData.get(position);
        List<UseSelectItemInfomVo> user = SharedSeletResultListUtil.getInstance().getUser();
        holder.mTvPopAnswerSelect.setText((position+1) + "");
        if (isSubmit) {//提交
            String id = String.valueOf(bean.getId());
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo vo = user.get(i);
//            判断是否做过
                if (id.equalsIgnoreCase(String.valueOf(vo.getId()))) {//做过
                    String status = vo.getItemStatus();
                    if (status.equals("0")) {//正确
                        holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_n);
                    } else if (status.equals("1")) {//错误
                        holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_miss_error);
                        holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.red_text));
                    } else if (status.equals("2")) {
                        holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_miss);
                        holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_tab_miss_color));
                    } else {
                        holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                        holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                    }
                    break;

                }else {//没有
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                }
            }

        } else {//未提交
            if (select == position) {//显示当前题
                holder.mTvPopAnswerSelect.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            String id = String.valueOf(bean.getId());
            for (int i = 0; i < user.size(); i++) {
                UseSelectItemInfomVo vo = user.get(i);
                String id1 = String.valueOf(vo.getId());
                if (id.equals(id1)){//有
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_su);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.black));
                    break;
                }else {//没有
                    holder.mTvPopAnswerSelect.setBackgroundResource(R.drawable.bg_select_answer_btn_ss);
                    holder.mTvPopAnswerSelect.setTextColor(mContext.getResources().getColor(R.color.text_fu_color));
                }
            }

        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvPopAnswerSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvPopAnswerSelect = (TextView) itemView.findViewById(R.id.tv_pop_answer_select);

        }
    }


}
