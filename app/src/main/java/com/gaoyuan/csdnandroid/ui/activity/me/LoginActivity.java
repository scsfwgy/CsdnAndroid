package com.gaoyuan.csdnandroid.ui.activity.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.utils.EmptyUtils;
import com.common.lib.utils.IntentUtils;
import com.common.lib.utils.KeyboardUtils;
import com.common.lib.utils.SnackbarUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.event.NullPUser;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.LoginReqBean;
import com.gaoyuan.csdnandroid.net.req.RecommendReqBean;
import com.gaoyuan.csdnandroid.utils.MySpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import razerdp.util.InputMethodUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.cab_rl_back)
    RelativeLayout mCabRlBack;
    @Bind(R.id.cab_tv_top_title)
    TextView mCabTvTopTitle;
    @Bind(R.id.al_et_user)
    EditText mAlEtUser;
    @Bind(R.id.al_et_psw)
    EditText mAlEtPsw;
    @Bind(R.id.al_b_login)
    Button mAlBLogin;
    @Bind(R.id.al_tv_prave)
    TextView mAlTvPrave;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }

    @OnClick({R.id.cab_rl_back, R.id.al_b_login, R.id.al_tv_prave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cab_rl_back:
                finish();
                break;
            case R.id.al_b_login:
                loginChick();
                break;
            case R.id.al_tv_prave:
                prave();
                break;
        }
    }

    private void prave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setMessage("我们不会保存你的密码，使用你的账号密码仅做验证身份处理。保存的信息如下：csdn的id、手机号、电邮、昵称、头像，请须知。")
                .setTitle("声明")
                .setIcon(R.drawable.me_me_toushu)
                .setCancelable(false)
                .setPositiveButton("我已明白", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        //show
        dialog.show();
    }

    private void loginChick() {
        //校验参数
        if (EmptyUtils.isEmpty(mAlEtUser.getText().toString()) || EmptyUtils.isEmpty(mAlEtPsw.getText().toString())) {
            showToast("账号或者密码为空，请完善后提交");
            return;
        }
        //开始提交
        checking();

        LoginReqBean recommendReqBean = new LoginReqBean();
        recommendReqBean.setAccount(mAlEtUser.getText().toString());
        recommendReqBean.setPassword(mAlEtPsw.getText().toString());
        call(ApiClient.getApis().userAction_login(recommendReqBean), new MySubscriber<BaseResp<PUser>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                showToast(getString(R.string.error_conn_service));
                loginError();
            }

            @Override
            public void onNext(BaseResp<PUser> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    //数据集不为空
                    if (resp.data != null) {
                        Log.e(TAG, "onNext: " + resp.toString());
                        //登录成功
                        loginOk(resp.data);
                    } else {
                        Log.e(TAG, "onNext: null。");
                        loginError();
                    }
                } else if (resp.code == Constants.Http.REQUEST_ERROR) {
                    Log.e(TAG, "onNext: 请求参数错误。" + resp.toString());
                    loginError();

                } else if (resp.code == Constants.Http.SERVICE_ERROR) {
                    Log.e(TAG, "onNext: 服务器异常。" + resp.toString());
                    loginError();
                } else {
                    Log.e(TAG, "onNext: 未知返回类型。");
                    loginError();

                }
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

    private void loginOk(PUser pUser) {
        MySpUtils.addPUser(LoginActivity.this, pUser);
        App.setPUser(MySpUtils.getPUser(LoginActivity.this));
        EventBus.getDefault().post(pUser);
        finish();
    }

    private void loginError() {
        //开始提交
        SnackbarUtils.showLongSnackbar(container, "验证失败，请检查账号和密码", getResources().getColor(R.color.white), getResources().getColor(R.color.colorYellow));
        mAlBLogin.setText("验 证");
        mAlBLogin.setClickable(true);
        mAlBLogin.setBackgroundColor(getResources().getColor(R.color.colorBlue));
    }

    private void checking() {
        //开始提交
        mAlBLogin.setText("正在验证...");
        mAlBLogin.setClickable(false);
        mAlBLogin.setBackgroundColor(getResources().getColor(R.color.colorHint));
        KeyboardUtils.hideSoftInput(this);
    }
}

