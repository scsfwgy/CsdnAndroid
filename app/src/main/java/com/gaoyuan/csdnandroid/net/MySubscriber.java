package com.gaoyuan.csdnandroid.net;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by timor.fan on 2016/9/27.
 * *项目名：CZF
 * 类描述：
 */
public abstract class MySubscriber<T> extends Subscriber<T> {
    private static final String TAG = "MySubscriber";

    @Override
    public void onCompleted() {
        Log.e(TAG, "onCompleted: ");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
    }
}
