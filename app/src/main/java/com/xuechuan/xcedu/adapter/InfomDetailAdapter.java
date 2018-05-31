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
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.vo.EvalueVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/26 8:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class InfomDetailAdapter extends BaseRecyclerAdapter<InfomDetailAdapter.ViewHoler> implements View.OnClickListener {
    private Context mContext;
    private List<EvalueVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    private onItemClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener!=null){
            clickListener.onClickListener(v.getTag(),v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public InfomDetailAdapter(Context mContext, List<EvalueVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHoler getViewHolder(View view) {
        return new ViewHoler(view);
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_h_evalua, null);
        ViewHoler holer = new ViewHoler(view);
        view.setOnClickListener(this);
        return holer;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position, boolean isItem) {
        EvalueVo.DatasBean bean = mData.get(position);
        holder.mTvEvalueUserName.setText(bean.getNickname());
        if (bean.isIssupport()) {
            holder.mTvEvalueSuppernumber.setText(bean.getSupportcount() + "");
        } else {
            holder.mTvEvalueSuppernumber.setText("赞");
        }
        holder.mChbEvaluaIssupper.setChecked(bean.isIssupport());
        holder.mTvEvalueContent.setText(bean.getContent());
        String ymdt = TimeUtil.getYMDT(bean.getCreatetime());
        String stamp = TimeSampUtil.getStringTimeStamp(bean.getCreatetime());
        L.e(stamp);
        holder.mTvEvalueTime.setText(stamp);
        if (!StringUtil.isEmpty(bean.getHeadicon())) {
            MyAppliction.getInstance().displayImages(holder.mIvEvaluateHear,bean.getHeadicon(), true);
        }
        holder.mTvEvalueEvalue.setText(bean.getCommentcount() + "");
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        public ImageView mIvEvaluateHear;
        public TextView mTvEvalueUserName;
        public TextView mTvEvalueContent;
        public TextView mTvEvalueTime;
        public TextView mTvEvalueEvalue;
        public CheckBox mChbEvaluaIssupper;
        public TextView mTvEvalueSuppernumber;

        public ViewHoler(View itemView) {
            super(itemView);

            this.mIvEvaluateHear = (ImageView) itemView.findViewById(R.id.iv_evaluate_hear);
            this.mTvEvalueUserName = (TextView) itemView.findViewById(R.id.tv_evalue_user_name);
            this.mTvEvalueContent = (TextView) itemView.findViewById(R.id.tv_evalue_content);
            this.mTvEvalueTime = (TextView) itemView.findViewById(R.id.tv_evalue_time);
            this.mTvEvalueEvalue = (TextView) itemView.findViewById(R.id.tv_evalue_evalue);
            this.mChbEvaluaIssupper = (CheckBox) itemView.findViewById(R.id.chb_evalua_issupper);
            this.mTvEvalueSuppernumber = (TextView) itemView.findViewById(R.id.tv_evalue_suppernumber);
        }
    }


}

