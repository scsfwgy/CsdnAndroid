package com.gaoyuan.csdnandroid.myview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gaoyuan.csdnandroid.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 作者：wgyscsf on 2017/5/1 09:46
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyWxPayPopupWindow extends BasePopupWindow {

    private View popupView;
    private ImageView wx;
    private ImageView ali;

    public MyWxPayPopupWindow(Activity context) {
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
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_pay_windows, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.psfb_ll_inner);
    }

    private void bindEvent() {
        if (popupView != null) {
            wx = (ImageView) popupView.findViewById(R.id.wx);
            ali = (ImageView) popupView.findViewById(R.id.ali);
        }
    }

    public View getPopupView() {
        return popupView;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }

    public ImageView getWx() {
        return wx;
    }

    public void setWx(ImageView wx) {
        this.wx = wx;
    }

    public ImageView getAli() {
        return ali;
    }

    public void setAli(ImageView ali) {
        this.ali = ali;
    }

}