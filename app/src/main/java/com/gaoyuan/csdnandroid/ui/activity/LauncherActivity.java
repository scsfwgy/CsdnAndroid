package com.gaoyuan.csdnandroid.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseActivity;

import butterknife.Bind;

public class LauncherActivity extends BaseActivity {

    @Bind(R.id.al_iv_plus)
    ImageView plus;
    boolean isBreak;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            go(MainActivity.class);
            finish();
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initViewsAndEvents() {
        //加载动画资源
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_alpha_plus);
        //开启动画
        plus.startAnimation(animation);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isBreak)
                    mHandler.sendMessage(new Message());
            }
        }).start();
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.NONE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isBreak = true;
    }
}
