package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveHistoryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.HotKeyVo;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxx
 * @Title: SearchActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 搜索页
 * @author: YFL
 * @date: 2018/4/15  14:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private static String ARG_PARAM1 = "arg_param1";
    private static String ARG_PARAM2 = "arg_param2";
    private EditText mEtSearch;
    private ImageView mIvSearch;
    private TextView mTvSearchClear;
    private RelativeLayout mRlSearchHistory;
    private SaveHistoryUtil mInstance;
    private Context mContext;
    private FlowLayout mFlSearchHistory;
    private FlowLayout mFlSearchHost;
    private LayoutInflater mInflater;


    public static void newInstance(Context context, String param1, String param2) {
        Intent intent = new Intent();
        intent.putExtra(ARG_PARAM1, param1);
        intent.putExtra(ARG_PARAM2, param2);
        context.startActivity(intent);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        initView();
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        initView();
        initData();
        bindHistoryData();
        bingHostData();

    }

    /**
     * 绑定历史
     */
    private void bindHistoryData() {
        ArrayList<String> list = mInstance.getHistoryList(mContext);
        if (list.size() > 0)
            BuildTextViewData(mFlSearchHistory, list);
    }

    private void initData() {
        mInflater = LayoutInflater.from(mContext);
        mInstance = SaveHistoryUtil.getInstance(mContext);
    }

    /**
     *
     * 绑定热搜
     */
    // TODO: 2018/4/19 处理缓存问题 
    private void bingHostData() {
        HomeService service = HomeService.getInstance(mContext);
//        service.setIsShowDialog(true);
//        service.setDialogContext("",getStringWithId(R.string.loading));
        service.requestHost("10", new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String hot = response.body().toString();
                L.d("获取热词数据" + hot);
                Gson gson = new Gson();
                HotKeyVo vo = gson.fromJson(hot, HotKeyVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<String> datas = vo.getDatas();
                    BuildTextViewData(mFlSearchHost, datas);
                } else {
                    T.showToast(mContext, response.message());
                }
            }

            @Override
            public void onError(Response<String> response) {
                T.showToast(mContext, response.message());
            }
        });
//
//        int a = 10;
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < a; i++) {
//            list.add("测" + i);
//        }
//        BuildTextViewData(mFlSearchHost, list);
    }

    /**
     * 创建历史记录按钮
     *
     * @param flowLayout
     * @param list
     */
    private void BuildTextViewData(FlowLayout flowLayout, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv,
                    flowLayout, false);
            tv.setText(list.get(i));
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
                    T.showToast(mContext, str);
                }
            });
            flowLayout.addView(tv);
        }
    }


    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mTvSearchClear = (TextView) findViewById(R.id.tv_search_clear);
        mTvSearchClear.setOnClickListener(this);
        mRlSearchHistory = (RelativeLayout) findViewById(R.id.rl_search_history);
        mIvSearch.setOnClickListener(this);
        mContext = this;
        mFlSearchHistory = (FlowLayout) findViewById(R.id.fl_search_history);
        mFlSearchHistory.setOnClickListener(this);
        mFlSearchHost = (FlowLayout) findViewById(R.id.fl_search_host);
        mFlSearchHost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                String trim = mEtSearch.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, "内容不能为空");
                    return;
                }
                mFlSearchHistory.removeAllViews();
                mInstance.saveHistory(trim);
                bindHistoryData();
                break;
            case R.id.tv_search_clear:
                mInstance.delete(mContext);
                mFlSearchHistory.removeAllViews();
                break;
            default:
                break;
        }
    }
}
