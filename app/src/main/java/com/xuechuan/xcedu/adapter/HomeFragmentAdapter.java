package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 主界面切换适配器
 * @author: L-BackPacker
 * @date: 2018/4/11 17:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mData;
    private Context mContext;
    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public HomeFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mData, Context mContext) {
        super(fm);
        this.mData = mData;
        this.mContext = mContext;
    }
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
