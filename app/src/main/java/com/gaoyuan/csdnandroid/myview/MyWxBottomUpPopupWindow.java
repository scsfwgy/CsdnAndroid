package com.gaoyuan.csdnandroid.myview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.TextView;

import com.gaoyuan.csdnandroid.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 作者：wgyscsf on 2017/5/1 09:46
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyWxBottomUpPopupWindow extends BasePopupWindow {

    private View popupView;
    private GridView pcdGvGridview;
    private TextView hintText;

    public MyWxBottomUpPopupWindow(Activity context) {
        super(context);
        bindEvent();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    public View getClickToDismissView() {
        return popupView.findViewById(R.id.psfb_rl_outer);
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_wx_bottom_up_windows, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.psfb_ll_inner);
    }

    private void bindEvent() {
        if (popupView != null) {
            pcdGvGridview = (GridView) popupView.findViewById(R.id.psfb_gv_gridview);
            hintText = (TextView) popupView.findViewById(R.id.psfb_tv_hint);
        }
    }

    public View getPopupView() {
        return popupView;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }

    public GridView getPcdGvGridview() {
        return pcdGvGridview;
    }

    public void setPcdGvGridview(GridView pcdGvGridview) {
        this.pcdGvGridview = pcdGvGridview;
    }

    public TextView getHintText() {
        return hintText;
    }

    public void setHintText(TextView hintText) {
        this.hintText = hintText;
    }
}