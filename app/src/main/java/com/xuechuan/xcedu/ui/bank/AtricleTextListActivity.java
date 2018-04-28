package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.TextBAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BookHomePageVo;
import com.xuechuan.xcedu.vo.SkillTextVo;

import java.util.ArrayList;
import java.util.List;
/**
 * @Title:  AtricleTextListActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description:  章节练习首页
 * @author: L-BackPacker
 * @date:   2018/4/27 16:51
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/27
 */
public class AtricleTextListActivity extends BaseActivity {

    private RecyclerView mRlvTextContent;
    private XRefreshView mXfvTextContent;
    private List mArray;
    private boolean isRefresh;
    private Context mContext;
    /**
     * 科目编号
     */
    private static String COURSEID = "courseid";
    private String mOid;
    private TextBAdapter bAdapter;


    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, AtricleTextListActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_atricle_text);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        clearData();
        setAdapter();
        initData();
    }



    private void setAdapter() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        bAdapter = new TextBAdapter(mContext, mArray, manager);
        mRlvTextContent.setLayoutManager(manager);
        mRlvTextContent.setAdapter(bAdapter);

    }
    private void initData() {
        final BankService service = new BankService(mContext);
        service.requestChapterList(mOid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                String message = response.body().toString();
                L.d(message);
                Gson gson = new Gson();
                SkillTextVo vo = gson.fromJson(message, SkillTextVo.class);
                if (vo.getStatus().getCode() == 200) {
                    List<SkillTextVo.DatasBean> datas = vo.getDatas();
                    clearData();
                    addListData(datas);
                    bAdapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
            }
        });
    }


    private void initView() {
        mContext = this;
        mRlvTextContent = (RecyclerView) findViewById(R.id.rlv_text_content);
    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArray == null) {
            clearData();
        }
        mArray.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }
}
