package com.gaoyuan.csdnandroid.myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.gaoyuan.csdnandroid.R;

/**
 * 作者：wgyscsf on 2017/4/20 21:28
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MyAnimationUtil {
    private static final String TAG = MyAnimationUtil.class.getSimpleName();

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    public static View loadLoadingDataAnim(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_loading_data, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.cld_iv_loading);
        //加载动画资源
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_common_loading);
        //开启动画
        imageView.startAnimation(animation);
        return view;
    }

    public static View loadNoDataAnim(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_empty_data, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.ced_iv_nodata);
        //加载动画资源
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_common_nodata);
        //开启动画
        imageView.startAnimation(animation);
        return view;
    }
}
