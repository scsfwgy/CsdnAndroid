package com.gaoyuan.csdnandroid.myview;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * 作者：wgyscsf on 2017/4/20 21:15
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyX5Webview extends WebView {
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public MyX5Webview(Context context) {
        super(context);
    }

    public MyX5Webview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyX5Webview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MyX5Webview(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public MyX5Webview(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy);
    }

}
