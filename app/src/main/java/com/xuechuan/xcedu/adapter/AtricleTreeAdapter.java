package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;
import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleTreeAdapter.java
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 树状列表
 * @author: YFL
 * @date: 2018/5/1 14:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AtricleTreeAdapter extends TreeRecyclerAdapter {
    public AtricleTreeAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
    }

    public AtricleTreeAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public void onBindViewHolder(Node node, RecyclerView.ViewHolder holder, int position) {
        TreeViewHolder viewHolder = (TreeViewHolder) holder;
        viewHolder.mTvAtricleTree.setText(node.getName());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TreeViewHolder holder = new TreeViewHolder(View.inflate(mContext, R.layout.item_atricle_tree, null));
        return holder;
    }

    public class TreeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvAtricleTree;

        public TreeViewHolder(View itemView) {
            super(itemView);
            this.mTvAtricleTree = (TextView) itemView.findViewById(R.id.tv_atricle_tree);

        }
    }

}
