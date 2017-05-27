package com.gaoyuan.csdnandroid.myview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoyuan.csdnandroid.R;

import org.w3c.dom.Text;

import butterknife.Bind;
import razerdp.basepopup.BasePopupWindow;

/**
 * 作者：wgyscsf on 2017/5/1 09:46
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyWxProfilePopupWindow extends BasePopupWindow {

    ImageView mPwpwIvHeadImg;
    TextView mPwpwTvNickName;
    TextView mPwpwTvId;
    TextView mPwpwTvPhone;
    TextView mPwpwTvEmail;
    TextView mPwpwTvBolg;
    TextView mPwpwTvAdd;
    @Bind(R.id.psfb_ll_inner)
    LinearLayout mPsfbLlInner;
    @Bind(R.id.psfb_rl_outer)
    RelativeLayout mPsfbRlOuter;
    private View popupView;

    public MyWxProfilePopupWindow(Activity context) {
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
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_wx_profile_windows, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.psfb_ll_inner);
    }

    private void bindEvent() {
        if (popupView != null) {
            mPwpwIvHeadImg = (ImageView) popupView.findViewById(R.id.pwpw_iv_headImg);
            mPwpwTvNickName = (TextView) popupView.findViewById(R.id.pwpw_tv_nickName);
            mPwpwTvId = (TextView) popupView.findViewById(R.id.pwpw_tv_id);
            mPwpwTvPhone = (TextView) popupView.findViewById(R.id.pwpw_tv_phone);
            mPwpwTvEmail = (TextView) popupView.findViewById(R.id.pwpw_tv_email);
            mPwpwTvBolg = (TextView) popupView.findViewById(R.id.pwpw_tv_bolg);
            mPwpwTvAdd = (TextView) popupView.findViewById(R.id.pwpw_tv_add);
            mPsfbLlInner = (LinearLayout) popupView.findViewById(R.id.psfb_ll_inner);
            mPsfbRlOuter = (RelativeLayout) popupView.findViewById(R.id.psfb_rl_outer);
        }
    }

    public View getPopupView() {
        return popupView;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }

    public ImageView getPwpwIvHeadImg() {
        return mPwpwIvHeadImg;
    }

    public void setPwpwIvHeadImg(ImageView pwpwIvHeadImg) {
        mPwpwIvHeadImg = pwpwIvHeadImg;
    }

    public TextView getPwpwTvNickName() {
        return mPwpwTvNickName;
    }

    public void setPwpwTvNickName(TextView pwpwTvNickName) {
        mPwpwTvNickName = pwpwTvNickName;
    }

    public TextView getPwpwTvId() {
        return mPwpwTvId;
    }

    public void setPwpwTvId(TextView pwpwTvId) {
        mPwpwTvId = pwpwTvId;
    }

    public TextView getPwpwTvPhone() {
        return mPwpwTvPhone;
    }

    public void setPwpwTvPhone(TextView pwpwTvPhone) {
        mPwpwTvPhone = pwpwTvPhone;
    }

    public TextView getPwpwTvEmail() {
        return mPwpwTvEmail;
    }

    public void setPwpwTvEmail(TextView pwpwTvEmail) {
        mPwpwTvEmail = pwpwTvEmail;
    }

    public TextView getPwpwTvBolg() {
        return mPwpwTvBolg;
    }

    public void setPwpwTvBolg(TextView pwpwTvBolg) {
        mPwpwTvBolg = pwpwTvBolg;
    }

    public TextView getPwpwTvAdd() {
        return mPwpwTvAdd;
    }

    public void setPwpwTvAdd(TextView pwpwTvAdd) {
        mPwpwTvAdd = pwpwTvAdd;
    }

    public LinearLayout getPsfbLlInner() {
        return mPsfbLlInner;
    }

    public void setPsfbLlInner(LinearLayout psfbLlInner) {
        mPsfbLlInner = psfbLlInner;
    }

    public RelativeLayout getPsfbRlOuter() {
        return mPsfbRlOuter;
    }

    public void setPsfbRlOuter(RelativeLayout psfbRlOuter) {
        mPsfbRlOuter = psfbRlOuter;
    }
}