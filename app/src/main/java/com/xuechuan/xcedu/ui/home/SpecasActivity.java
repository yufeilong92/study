package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.DatasBeanVo;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecasActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 规范页
 * @author: L-BackPacker
 * @date: 2018/4/19 17:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class SpecasActivity extends BaseActivity {

    private RecyclerView mRlvSpecaContent;

    private static String PARAME = "PARAME";
    private static String PARAME1 = "PARAME";
    private String parame;
    private String parame1;
    private Context mContext;

    public static Intent newInstance(Context context, String parame, String parame1) {
        Intent intent = new Intent(context, SpecasActivity.class);
        intent.putExtra(PARAME, parame);
        intent.putExtra(PARAME1, parame1);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specas);
        if (getIntent() != null) {
            parame = getIntent().getStringExtra(PARAME);
            parame1 = getIntent().getStringExtra(PARAME1);
        }
        initView();
        initData();
    }

    private void initData() {
        HomeService service = HomeService.getInstance(mContext);
        service.setIsShowDialog(true);
        service.setDialogContext(null, getStringWithId(R.string.loading));
        service.requestChapterList(1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.body().toString();
                L.e("获取规范章节列表数据" + s);
                Gson gson = new Gson();
                SpecasChapterListVo vo = gson.fromJson(s, SpecasChapterListVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
                    List<DatasBeanVo> datas = vo.getDatas();
                    bindAdapterData(datas);

                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                L.e("获取规范章节错误" + response.message());
            }
        });
    }

    /**
     * 绑定适配器数据
     *
     * @param dataBean
     */
    private void bindAdapterData( List<DatasBeanVo> dataBean) {
        SpecsOrderAdapter adapter = new SpecsOrderAdapter(mContext, dataBean);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvSpecaContent.addItemDecoration(new DividerItemDecoration(mContext, com.xuechuan.xcedu.weight.DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);


    }


    private void initView() {
        mContext = this;
        mRlvSpecaContent = (RecyclerView) findViewById(R.id.rlv_speca_content);

    }
}
