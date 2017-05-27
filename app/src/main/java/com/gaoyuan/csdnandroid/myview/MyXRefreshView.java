package com.gaoyuan.csdnandroid.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshContentView;
import com.andview.refreshview.XRefreshView;
import com.gaoyuan.csdnandroid.R;

/**
 * 作者：wgyscsf on 2017/4/20 10:24
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyXRefreshView extends XRefreshView {
    Context mContext;

    public MyXRefreshView(Context context) {
        super(context);
        mContext = context;
        setDefStatue();
    }

    public MyXRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setDefStatue();
    }

    //设置初始值
    public void setDefStatue() {
        setPullLoadEnable(true);
        setAutoLoadMore(true);
        setMoveFootWhenDisablePullLoadMore(true);
        setHideFooterWhenComplete(true);

        //显示emptyView
        enableEmptyView(true);//必须调用
        View view = MyAnimationUtil.loadLoadingDataAnim(mContext);
        setEmptyView(view);

    }

    //设置没有数据情况下的加载页面
    public void setNoDataView(boolean showNodata) {
        View view = MyAnimationUtil.loadNoDataAnim(mContext);
        setEmptyView(view);
        enableEmptyView(showNodata);
        //单机加载
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRefresh();
            }
        });
    }

    public static abstract class MyXRefreshViewListener implements XRefreshViewListener {

        @Override
        public void onRefresh(boolean isPullDown) {

        }

        @Override
        public void onRelease(float direction) {

        }

        @Override
        public void onHeaderMove(double headerMovePercent, int offsetY) {

        }
    }
}
