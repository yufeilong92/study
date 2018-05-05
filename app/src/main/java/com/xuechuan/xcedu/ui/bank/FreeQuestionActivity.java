package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.multilevel.treelist.Node;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AtricleTreeAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.QuestionListModelImpl;
import com.xuechuan.xcedu.mvp.presenter.QuestionListPresenter;
import com.xuechuan.xcedu.mvp.view.QuestionListView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.vo.SkillTextVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: FreeQuestionActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 自由组卷
 * @author: L-BackPacker
 * @date: 2018/5/5 19:28
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/5
 */
public class FreeQuestionActivity extends BaseActivity implements QuestionListView {

    private ImageView mIcPopResult;
    private RadioGroup mRgQuestionNumber;
    private RadioGroup mRgDifficultyGrade;
    private RecyclerView mRlvFreeTitle;
    private QuestionListPresenter mListPresenter;
    private Context mContext;
    /**
     * 科目id
     */
    private String mCourseid;
    //    数据接口
    private ArrayList<Node> mNodeLists;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_question);
        initView();
        initData();

    }
*/
    /**
     * 科目
     */
    private static String COURSEID = "courseid";
    private AlertDialog mDialog;

    public static Intent newInstance(Context context, String courseid) {
        Intent intent = new Intent(context, FreeQuestionActivity.class);
        intent.putExtra(COURSEID, courseid);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_free_question);
        if (getIntent() != null) {
            mCourseid = getIntent().getStringExtra(COURSEID);
        }
        initView();
        initData();
    }

    private void initData() {
        mListPresenter = new QuestionListPresenter(new QuestionListModelImpl(), this);
        if (!StringUtil.isEmpty(mCourseid))
            mListPresenter.requestQuetionList(mContext, mCourseid);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
    }

    private void initView() {
        mContext = this;
        mIcPopResult = (ImageView) findViewById(R.id.ic_pop_result);
        mRgQuestionNumber = (RadioGroup) findViewById(R.id.rg_question_number);
        mRgDifficultyGrade = (RadioGroup) findViewById(R.id.rg_difficulty_grade);
        mRlvFreeTitle = (RecyclerView) findViewById(R.id.rlv_free_title);
    }

    @Override
    public void QuestionListSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        SkillTextVo vo = gson.fromJson(con, SkillTextVo.class);
        if (vo.getStatus().getCode() == 200) {
            addListData(vo.getDatas());
        } else {
            T.showToast(mContext, vo.getStatus().getMessage());
        }

    }

    private void bindAdapterData(List<ChildrenBeanVo> vos) {
        AtricleTreeAdapter treeAdapter = new AtricleTreeAdapter(mRlvFreeTitle, mContext, mNodeLists
                , 0);

    }

    private void addListData(List<SkillTextVo.DatasBean> datas) {
        for (int i = 0; i < datas.size(); i++) {
            SkillTextVo.DatasBean bean = datas.get(i);
            mNodeLists.add(new Node(bean.getId(), bean.getParentid(), bean.getTitle(), bean));
        }
    }

    @Override
    public void QuestionListError(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        L.e(con);
    }
}
