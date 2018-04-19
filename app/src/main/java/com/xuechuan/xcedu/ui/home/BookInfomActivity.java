package com.xuechuan.xcedu.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.xuechuan.xcedu.R;
/**
 * @Title:  BookInfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 教材详情页
 * @author: L-BackPacker
 * @date:   2018/4/19 16:44
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class BookInfomActivity extends AppCompatActivity {
    /**
     * 章
     */
    private ListView mLvBookinfomZhang;
    /**
     * 节
     */
    private ListView mLvBookinfomJie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_infom);
        initView();
        initData();
        initZhang();
        initJie();
    }

    private void initJie() {

    }

    private void initZhang() {

    }

    private void initData() {

    }

    private void initView() {
        mLvBookinfomZhang = (ListView) findViewById(R.id.lv_bookinfom_zhang);
        mLvBookinfomJie = (ListView) findViewById(R.id.lv_bookinfom_jie);
    }

}
