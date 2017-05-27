package com.gaoyuan.csdnandroid.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseFragment;
import com.gaoyuan.csdnandroid.ui.adapter.MainGradeFragemntAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：wgyscsf on 2016/12/24 16:47
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MainExpertFragemnt extends BaseFragment {

    @Bind(R.id.fmg_tl_tablayout)
    TabLayout mFmgTlTablayout;
    @Bind(R.id.fmg_vp_viewpager)
    ViewPager mFmgVpViewpager;
    @Bind(R.id.fmg_ll_container)
    LinearLayout mFmgLlContainer;

    private List<String> list_title;
    private List<Fragment> mFragmentList;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main_grade;

    }

    @Override
    protected void initViewsAndEvents() {
        initView();
        initData();
        initAdapter();
        initListener();
        loadData();
    }

    private void initView() {
    }

    private void initData() {
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("移动开发");
        list_title.add("web前端");
        list_title.add("架构设计");
        list_title.add("编程语言");
        list_title.add("互联网");
        list_title.add("数据库");
        list_title.add("系统运维");
        list_title.add("云计算");
        list_title.add("研发管理");

        mFmgTlTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(0)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(1)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(2)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(3)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(4)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(5)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(6)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(7)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(8)));
//        mFmgTlTablayout.addTab(mFmgTlTablayout.newTab().setText(list_title.get(9)));

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ExpertListFragment(0));
        mFragmentList.add(new ExpertListFragment(1));
        mFragmentList.add(new ExpertListFragment(2));
        mFragmentList.add(new ExpertListFragment(3));
        mFragmentList.add(new ExpertListFragment(4));
        mFragmentList.add(new ExpertListFragment(5));
        mFragmentList.add(new ExpertListFragment(6));
        mFragmentList.add(new ExpertListFragment(7));
        mFragmentList.add(new ExpertListFragment(8));
        mFragmentList.add(new ExpertListFragment(9));
    }

    private void initAdapter() {
        mFmgVpViewpager.setAdapter(new MainGradeFragemntAdapter(getFragmentManager(), mFragmentList,list_title));
        mFmgTlTablayout.setupWithViewPager(mFmgVpViewpager);
    }

    private void initListener() {

    }

    private void loadData() {

    }


}
