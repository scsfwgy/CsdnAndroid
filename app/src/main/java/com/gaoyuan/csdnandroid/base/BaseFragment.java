package com.gaoyuan.csdnandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.lib.base.BaseAppCompatFragment;
import com.gaoyuan.csdnandroid.net.MySubscriber;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseFragment extends BaseAppCompatFragment {
    private static final int NON_CODE = -1;
    protected Context mContext;
    protected View rootView;
    protected float mScreenDensity;
    protected int mScreenHeight;
    protected int mScreenWidth;
    protected boolean isVisible;
    private CompositeSubscription subscriptions;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            getExtraArguments(getArguments());
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewLayout(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        initSubscription();
        initViewsAndEvents();
    }
    protected  void initSubscription(){
        subscriptions = new CompositeSubscription();
    }
    protected abstract int getContentViewLayout();

    protected void getExtraArguments(Bundle arguments) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {

    }


    protected boolean isBindEventBusHere(){
        return false;
    }

    protected abstract void initViewsAndEvents();

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field mChildFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            mChildFragmentManager.setAccessible(true);
            mChildFragmentManager.set(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void call(Observable observable, MySubscriber subscriber) {
        Subscription subscription = null;
        try {
            subscription = observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            subscriptions.add(subscription);
        } catch (Exception e) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            //Log.e("error", e.getMessage());
        } finally {

        }
    }
    @Override
    public void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }
}
