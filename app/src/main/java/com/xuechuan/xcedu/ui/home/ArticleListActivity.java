package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.ArticleListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ArticleListVo;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ArticleListActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 文章列表
 * @author: L-BackPacker
 * @date: 2018/4/20 9:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/20
 */
public class ArticleListActivity extends BaseActivity {

    private RecyclerView mRlvInfomList;
    private Context mContext;

    private static String SAFFID = "saffid";
    private int mSaffid;

    /**
     * @param context
     * @param saffid  用户id
     */
    public static Intent newInstance(Context context, int saffid) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        intent.putExtra(SAFFID, saffid);
        return intent;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_article_list);
//        initView();
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article_list);
        if (getIntent() != null) {
            mSaffid = getIntent().getIntExtra(SAFFID, 0);
        }
        initView();
        initData();
    }

    private void initData() {
        final HomeService service = HomeService.getInstance(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext("", getStringWithId(R.string.loading));
        service.requestArticleList(mSaffid, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                ArticleListVo vo = gson.fromJson(message, ArticleListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    bindAdapterData( vo.getDatas());
                } else {
                    T.showToast(mContext, status.getMessage());
                }
            }
            @Override
            public void onError(Response<String> response) {
                L.e(response.message());
            }
        });
    }

    private void initView() {
        mContext = this;
        mRlvInfomList = (RecyclerView) findViewById(R.id.rlv_infom_list);
    }

    private void bindAdapterData(List<ArticleVo> list) {
        ArticleListAdapter articleAdapter = new ArticleListAdapter(mContext, list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvInfomList.setLayoutManager(gridLayoutManager);
        mRlvInfomList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvInfomList.setAdapter(articleAdapter);
        articleAdapter.setClickListener(new ArticleListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleVo vo = (ArticleVo) obj;
                InfomActivity.newInstance(mContext, vo.getGourl());
            }
        });

    }
}
