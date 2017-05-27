package com.gaoyuan.csdnandroid.ui.activity.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.utils.DataCleanManager;
import com.common.lib.utils.SnackbarUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.event.NullPUser;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;
import com.gaoyuan.csdnandroid.utils.MySpUtils;
import com.gaoyuan.csdnandroid.webview.X54BlogDetailsActivity;
import com.gaoyuan.csdnandroid.webview.X54CommonWebViewActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.View.GONE;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.container)
    RelativeLayout container;
    @Bind(R.id.as_rl_line1)
    RelativeLayout mAsRlLine1;
    @Bind(R.id.as_rl_line2)
    RelativeLayout mAsRlLine2;
    @Bind(R.id.as_rl_line3)
    RelativeLayout mAsRlLine3;
    @Bind(R.id.as_rl_line4)
    RelativeLayout mAsRlLine4;
    @Bind(R.id.as_rl_line5)
    RelativeLayout mAsRlLine5;
    @Bind(R.id.as_iv_line6_red_point)
    ImageView mAsIvLine6RedPoint;
    @Bind(R.id.as_iv_line5_red_point)
    ImageView mAsIvLine5RedPoint;
    @Bind(R.id.as_rl_line6)
    RelativeLayout mAsRlLine6;
    @Bind(R.id.as_rl_line7)
    TextView mAsRlLine7;
    MySpUtils mySpUtils = new MySpUtils();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    private void initView() {
        if (App.getPUser() == null) {
            mAsRlLine7.setVisibility(GONE);
        } else {
            mAsRlLine7.setVisibility(View.VISIBLE);
        }
        updatestate();
    }

    private void updatestate() {
        if (mySpUtils.isfirstComeAbout(this)) {
            mAsIvLine6RedPoint.setVisibility(GONE);
        }
        if (mySpUtils.isfirstComeSuppert(this)) {
            mAsIvLine5RedPoint.setVisibility(GONE);
        }
    }

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }

    @OnClick({R.id.as_rl_line1, R.id.as_rl_line2, R.id.as_rl_line3, R.id.as_rl_line4, R.id.as_rl_line5, R.id.as_rl_line6, R.id.as_rl_line7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.as_rl_line1:
                break;
            case R.id.as_rl_line2:
                break;
            case R.id.as_rl_line3:
                clearData();
                break;
            case R.id.as_rl_line4:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Keys.INTENT_WEB_KEY, Constants.Http.UPDATE_PLAN);
                bundle.putString(Constants.Keys.INTENT_WEB_TITLE, "更新计划");
                go(X54CommonWebViewActivity.class, bundle);
                break;
            case R.id.as_rl_line5:
                go(SupportActivity.class);
                firstComeSuppert();
                break;
            case R.id.as_rl_line6:
                go(AboutActivity.class);
                firstAboutSuppert();
                break;
            case R.id.as_rl_line7:
                existLogin();
                break;
        }
    }

    private void clearData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("确认要清空数据吗？")
                .setTitle("提醒")
                .setIcon(R.drawable.me_me_toushu)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        DataCleanManager.cleanApplicationData(getApplicationContext(), new DataCleanManager.OnClearListener() {
                            @Override
                            public void onSuccess() {
                                MySnackbarUtils.showBlueSnack(container, "清空缓存成功", SettingActivity.this);
                            }

                            @Override
                            public void onError(Exception e) {
                                MySnackbarUtils.showYellowSnack(container, "清空缓存失败", SettingActivity.this);
                            }
                        });
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(SettingActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        //show
        dialog.show();
    }

    private void firstAboutSuppert() {
        mAsIvLine6RedPoint.setVisibility(GONE);
        mySpUtils.firstComeAbout(SettingActivity.this);
    }

    private void firstComeSuppert() {
        mAsIvLine5RedPoint.setVisibility(GONE);
        mySpUtils.firstComeSuppert(SettingActivity.this);
    }

    private void existLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("确认要退出登录吗？")
                .setTitle("提醒")
                .setIcon(R.drawable.me_me_toushu)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        MySpUtils.clearPuser(SettingActivity.this);
                        EventBus.getDefault().post(new NullPUser());
                        finish();
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(SettingActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        //show
        dialog.show();
    }
}
