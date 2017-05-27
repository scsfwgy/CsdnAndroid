package com.gaoyuan.csdnandroid.ui.activity.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.lib.utils.KeyboardUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.myview.MyWxPayPopupWindow;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;
import razerdp.util.InputMethodUtils;

import static android.view.View.GONE;

public class SupportActivity extends BaseActivity {


    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.as_tv_1)
    TextView mAsTv1;
    @Bind(R.id.as_tv_2)
    TextView mAsTv2;
    @Bind(R.id.as_tv_3)
    TextView mAsTv3;
    @Bind(R.id.as_tv_4)
    TextView mAsTv4;
    @Bind(R.id.as_tv_5)
    TextView mAsTv5;
    @Bind(R.id.as_tv_6)
    TextView mAsTv6;
    @Bind(R.id.as_tv_othermoney)
    TextView mAsTvOthermoney;
    @Bind(R.id.as_et_input)
    EditText mAsEtInput;
    @Bind(R.id.as_b_ok)
    Button mAsBOk;
    @Bind(R.id.as_ll_inputBlock)
    LinearLayout mAsLlInputBlock;

    boolean oherMoneyShow = false;
    MyWxPayPopupWindow mMyWxPayPopupWindow;
    double chooseMoney;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_support;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setText(mAsTv1);
        setText(mAsTv2);
        setText(mAsTv3);
        setText(mAsTv4);
        setText(mAsTv5);
        setText(mAsTv6);

    }

    private void initData() {

    }

    private void initListener() {

    }

    private void setText(TextView textView) {
        Spannable span = new SpannableString(textView.getText());
        span.setSpan(new AbsoluteSizeSpan(30), textView.getText().length() - 1, textView.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    @OnClick({R.id.as_tv_1, R.id.as_tv_2, R.id.as_tv_3, R.id.as_tv_4, R.id.as_tv_5, R.id.as_tv_6, R.id.as_tv_othermoney, R.id.as_et_input, R.id.as_b_ok, R.id.as_ll_inputBlock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.as_tv_1:
                chooseMoney = 1.1;
                choosePay();
                break;
            case R.id.as_tv_2:
                chooseMoney = 2.2;
                choosePay();
                break;
            case R.id.as_tv_3:
                chooseMoney = 5.21;
                choosePay();
                break;
            case R.id.as_tv_4:
                chooseMoney = 10;
                choosePay();

                break;
            case R.id.as_tv_5:
                chooseMoney = 20;
                choosePay();

                break;
            case R.id.as_tv_6:
                chooseMoney = 52.1;
                choosePay();

                break;
            case R.id.as_tv_othermoney:
                if (!oherMoneyShow) {
                    oherMoneyShow = true;
                    mAsLlInputBlock.setVisibility(View.VISIBLE);
                } else {
                    oherMoneyShow = false;
                    mAsLlInputBlock.setVisibility(GONE);
                }
                break;
            case R.id.as_b_ok:
                chexck();
                break;

        }
    }

    private void choosePay() {
        if (mMyWxPayPopupWindow == null) {
            mMyWxPayPopupWindow = new MyWxPayPopupWindow(SupportActivity.this);
            mMyWxPayPopupWindow.setDismissWhenTouchOuside(false);
        }
        mMyWxPayPopupWindow.showPopupWindow();
        mMyWxPayPopupWindow.getWx().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("正在发起赞赏...");
                mMyWxPayPopupWindow.dismiss();
                pay(false, chooseMoney);
            }
        });
        mMyWxPayPopupWindow.getAli().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("正在发起赞赏...");
                mMyWxPayPopupWindow.dismiss();
                pay(true, chooseMoney);
            }
        });
    }

    private void chexck() {
        KeyboardUtils.hideSoftInput(SupportActivity.this);
        String trim = mAsEtInput.getText().toString().trim();
        int money;
        try {
            money = Integer.parseInt(trim);
            if (money <= 0 || money > 521) {
                MySnackbarUtils.showYellowSnack(container, "金额不合法", SupportActivity.this);
                return;
            }
        } catch (Exception e) {
            MySnackbarUtils.showYellowSnack(container, "金额不合法", SupportActivity.this);
            return;
        }
        chooseMoney = money;
        choosePay();
    }

    // 此为微信支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    private void pay(boolean isAli, double money) {
        // 有微信客户端，看看有无微信支付插件
        int pluginVersion = BP.getPluginVersion(this);
        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
            // 否则就是支付插件的版本低于官方最新版
            Toast.makeText(
                    SupportActivity.this,
                    pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                            : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                    Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");

            installApk("bp.db");
            return;
        }
        String id = null;
        final PUser pUser = App.getPUser();
        if (pUser != null)
            id = pUser.getId_csdn();
        BP.pay("赞赏" + money + "元", "相信技术的力量,来自:" + id, money, isAli, new PListener() {
            @Override
            public void orderId(String s) {
                Log.e(TAG, "orderId: " + s);
            }

            @Override
            public void succeed() {
                MySnackbarUtils.showBlueSnack(container, "我们已收到您的支持，我们会做的更好。谢谢！", SupportActivity.this);
                PUser pUser1 = App.getPUser();
                if (pUser1 != null) {
                    pUser1.setPraise(1);
                    updatePUser();
                }

            }

            @Override
            public void fail(int i, String s) {
                Log. e(TAG, "fail: " + i + "--" + s);
            }

            @Override
            public void unknow() {
                Log.e(TAG, "unknow: ");
            }
        });
    }

    private void updatePUser() {
        call(ApiClient.getApis().userAction_updatePUser(App.getPUser()), new MySubscriber<BaseResp<Boolean>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(BaseResp<Boolean> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    //数据集不为空
                    if (resp.data) {
                        Log.e(TAG, "onNext: " + resp.toString());
                        //登录成功
                    } else {
                        Log.e(TAG, "onNext: null。");
                    }
                } else if (resp.code == Constants.Http.REQUEST_ERROR) {
                    Log.e(TAG, "onNext: 请求参数错误。" + resp.toString());
                } else if (resp.code == Constants.Http.SERVICE_ERROR) {
                    Log.e(TAG, "onNext: 服务器异常。" + resp.toString());
                } else {
                    Log.e(TAG, "onNext: 未知返回类型。");
                }
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(SupportActivity.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }

    private static final int REQUESTPERMISSION = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installBmobPayPlugin("bp.db");
                } else {
                    //提示没有权限，安装不了
                    Toast.makeText(SupportActivity.this, "您拒绝了权限，这样无法安装支付插件", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
