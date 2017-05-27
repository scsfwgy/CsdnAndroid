package com.gaoyuan.csdnandroid.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.base.BaseAppCompatActivity;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.ui.fragment.MainSearchFragemnt;
import com.gaoyuan.csdnandroid.ui.fragment.MainExpertFragemnt;
import com.gaoyuan.csdnandroid.ui.fragment.MainMineFragemnt;
import com.gaoyuan.csdnandroid.ui.fragment.MainRecommendFragemnt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.cab_rl_back)
    RelativeLayout mBackBtn;
    @Bind(R.id.cab_rl_container)
    public RelativeLayout mContainer;
    @Bind(R.id.cab_tv_top_title)
    TextView topTitle;
    @Bind(R.id.cab_iv_reght_img)
    public ImageView topReightImg;

    @Bind(R.id.am_fl_fragmentcontainer)
    FrameLayout mAmFlFragmentcontainer;
    @Bind(R.id.cnb_iv_block1)
    ImageView mCnbIvBlock1;
    @Bind(R.id.cnb_tv_block1)
    TextView mCnbTvBlock1;
    @Bind(R.id.cnb_ll_block1)
    public
    LinearLayout mCnbLlBlock1;
    @Bind(R.id.cnb_iv_block2)
    ImageView mCnbIvBlock2;
    @Bind(R.id.cnb_tv_block2)
    TextView mCnbTvBlock2;
    @Bind(R.id.cnb_ll_block2)
    LinearLayout mCnbLlBlock2;
    @Bind(R.id.cnb_iv_block3)
    ImageView mCnbIvBlock3;
    @Bind(R.id.cnb_tv_block3)
    TextView mCnbTvBlock3;
    @Bind(R.id.cnb_ll_block3)
    LinearLayout mCnbLlBlock3;
    @Bind(R.id.cnb_iv_block4)
    ImageView mCnbIvBlock4;
    @Bind(R.id.cnb_tv_block4)
    TextView mCnbTvBlock4;
    @Bind(R.id.cnb_ll_block4)
    LinearLayout mCnbLlBlock4;
    //FragmentManager
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    Fragment mMainRecommendFragment;
    Fragment mMainGradeFragment;
    Fragment mMainDataFragment;
    Fragment mMainMineFragment;
    List<Fragment> mFragmentList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
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
        mBackBtn.setVisibility(View.GONE);

        mMainRecommendFragment = new MainRecommendFragemnt();
        mMainGradeFragment = new MainExpertFragemnt();
        mMainDataFragment = new MainSearchFragemnt();
        mMainMineFragment = new MainMineFragemnt();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mMainRecommendFragment);
        mFragmentList.add(mMainGradeFragment);
        mFragmentList.add(mMainDataFragment);
        mFragmentList.add(mMainMineFragment);
        mFragmentManager = getSupportFragmentManager();
        //默认显示首页
        showFragment(mMainRecommendFragment);

    }

    private void initData() {

    }

    private void initAdapter() {

    }

    private void initListener() {
        mCnbLlBlock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(mMainRecommendFragment);
                doubleKillReflsh();
            }
        });
        mCnbLlBlock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(mMainGradeFragment);
            }
        });
        mCnbLlBlock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(mMainDataFragment);
            }
        });
        mCnbLlBlock4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(mMainMineFragment);
            }
        });
    }

    private long returnTime = 0;

    //双击刷新
    private void doubleKillReflsh() {
        if (returnTime <= 0) {
            returnTime = System.currentTimeMillis();
        } else {
            long nowtime = System.currentTimeMillis();
            if (nowtime - returnTime < 1000) {
                returnTime=0;
                ((MainRecommendFragemnt) mMainRecommendFragment).getFmrXsfFreshload().startRefresh();
            } else {
                returnTime = System.currentTimeMillis();
            }
        }
    }


    private void loadData() {

    }


    //隐藏所有已经add的帧布局
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : mFragmentList) {
            if (fragment != null)
                if (fragment.isAdded()) {
                    mFragmentTransaction.hide(fragment);
                }
        }
    }

    //显示帧布局
    private void showFragment(Fragment fragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideAllFragment(mFragmentTransaction);
        if (fragment.isAdded()) {
            mFragmentTransaction.show(fragment);
        } else {
            mFragmentTransaction.add(R.id.am_fl_fragmentcontainer, fragment);
        }

        //界面变化处理
        hideAllBlockView();
        if (fragment instanceof MainRecommendFragemnt) {
            showBlock1();
        } else if (fragment instanceof MainExpertFragemnt) {
            showBlock2();
        } else if (fragment instanceof MainSearchFragemnt) {
            showBlock3();
        } else if (fragment instanceof MainMineFragemnt) {
            showBlock4();
        }

        mFragmentTransaction.commit();
    }

    private void hideAllBlockView() {
        mCnbTvBlock1.setTextColor(getResources().getColor(R.color.four_btn_color_def));
        mCnbTvBlock2.setTextColor(getResources().getColor(R.color.four_btn_color_def));
        mCnbTvBlock3.setTextColor(getResources().getColor(R.color.four_btn_color_def));
        mCnbTvBlock4.setTextColor(getResources().getColor(R.color.four_btn_color_def));

        mCnbIvBlock1.setBackgroundDrawable(getResources().getDrawable(R.drawable.tuijian_g));
        mCnbIvBlock2.setBackgroundDrawable(getResources().getDrawable(R.drawable.youzhi_g));
        mCnbIvBlock3.setBackgroundDrawable(getResources().getDrawable(R.drawable.common_tab_faxian_n));
        mCnbIvBlock4.setBackgroundDrawable(getResources().getDrawable(R.drawable.common_tab_me_n));

    }

    private void showBlock1() {
        topTitle.setText(getString(R.string.recommend));
        mCnbTvBlock1.setTextColor(getResources().getColor(R.color.four_btn_color_press));
        mCnbIvBlock1.setBackgroundDrawable(getResources().getDrawable(R.drawable.tuijian_b));
    }

    private void showBlock2() {
        topTitle.setText(getString(R.string.grade));

        mCnbTvBlock2.setTextColor(getResources().getColor(R.color.four_btn_color_press));
        mCnbIvBlock2.setBackgroundDrawable(getResources().getDrawable(R.drawable.youzhi_b));
    }

    private void showBlock3() {
        topTitle.setText(getString(R.string.search));

        mCnbTvBlock3.setTextColor(getResources().getColor(R.color.four_btn_color_press));
        mCnbIvBlock3.setBackgroundDrawable(getResources().getDrawable(R.drawable.common_tab_faxian_s));
    }

    private void showBlock4() {
        topTitle.setText(getString(R.string.mine));

        mCnbTvBlock4.setTextColor(getResources().getColor(R.color.four_btn_color_press));
        mCnbIvBlock4.setBackgroundDrawable(getResources().getDrawable(R.drawable.common_tab_me_s));
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.NONE;
    }
}
