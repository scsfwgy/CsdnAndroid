package com.gaoyuan.csdnandroid.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.lib.utils.IntentUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {


    @Bind(R.id.wgyscsf_csdn)
    TextView mWgyscsfCsdn;
    @Bind(R.id.wgyscsf_github)
    TextView mWgyscsfGithub;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }


    @OnClick({R.id.wgyscsf_csdn, R.id.wgyscsf_github})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wgyscsf_csdn:
                showToast(getString(R.string.open_brower));
                IntentUtils.openBrower(this, "http://blog.csdn.net/wgyscsf");
                break;
            case R.id.wgyscsf_github:
                IntentUtils.openBrower(this, "https://github.com/scsfwgy");
                showToast("正在打开浏览器，请稍后~");
                break;
        }
    }
}
