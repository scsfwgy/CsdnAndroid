package com.gaoyuan.csdnandroid.base;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.base.BaseAppCompatActivity;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.MySubscriber;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends BaseAppCompatActivity {

    public  int nowPager;
    public  int pagerSize=Constants.Pager.DEF_PERPAGER_SIZE;
    private CompositeSubscription subscriptions;

    @Override
    protected boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    protected void onNetworkDisconnect() {

    }

    @Override
    protected void onNetworkConnected() {

    }

    @Override
    protected boolean hasTitlebar() {
        return findViewById(R.id.cab_rl_container) != null;
    }


    @Override
    protected void setCustomTitle(CharSequence title) {
        if (hasTitlebar()) {
            TextView titleView = ButterKnife.findById(this, R.id.cab_tv_top_title);
            if (titleView != null) {
                titleView.setText(title);
                setTitle("");
            }
        }
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onNavigateClick() {
        if (hasTitlebar()) {
            RelativeLayout backView = ButterKnife.findById(this, R.id.cab_rl_back);
            if (backView != null) {
                backView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //当点击退出时，如果输入法弹起，隐藏。by wgy
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                        }

                        finish();
                    }
                });
            }
        }
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.RIGHT;
    }


    public void call(Observable observable, MySubscriber subscriber) {
        Subscription subscription = ApiClient.call(observable, subscriber);
        subscriptions.add(subscription);
    }

    @Override
    protected void initSubscription() {
        subscriptions = new CompositeSubscription();
    }
}
