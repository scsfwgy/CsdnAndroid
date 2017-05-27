package com.gaoyuan.csdnandroid.ui.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseActivity;

public class SecondActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_second;
    }

    @Override
    protected void initViewsAndEvents() {

    }
    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }
}
