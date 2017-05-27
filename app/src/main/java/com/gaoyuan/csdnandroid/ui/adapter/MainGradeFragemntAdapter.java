package com.gaoyuan.csdnandroid.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * 作者：wgyscsf on 2017/4/29 22:08
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MainGradeFragemntAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    List<String> mList_title;

    public MainGradeFragemntAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list_title) {
        super(fm);
        mFragmentList = fragmentList;
        mList_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList_title.get(position);
    }
}
