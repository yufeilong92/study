package com.xuechuan.xcedu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

/**
 * @Title:  GuideActivity
 * @Package com.xuechuan.xcedu
 * @Description: 引导页
 * @author: L-BackPacker
 * @date:   2018/6/4 18:41
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/6/4
 */
public class GuideActivity extends AppCompatActivity {
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private Button startBtn;

    // 引导页图片资源
    private static final int[] pics = { R.mipmap.bpage1,
            R.mipmap.bpage2, R.mipmap.bpage3 };

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }


}
