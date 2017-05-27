package com.gaoyuan.csdnandroid.webview;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.common.lib.utils.ClipboardUtils;
import com.common.lib.utils.IntentUtils;
import com.common.lib.utils.RegexUtils;
import com.common.lib.utils.SnackbarUtils;
import com.common.lib.utils.ToastUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.bean.Pop;
import com.gaoyuan.csdnandroid.bean.Preference;
import com.gaoyuan.csdnandroid.myview.MyWxBottomUpPopupWindow;
import com.gaoyuan.csdnandroid.myview.MyX5Webview;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.PStarReqBean;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class X54CommonWebViewActivity extends BaseActivity {
    @Bind(R.id.activity_my_x5_webview)
    LinearLayout continer;
    @Bind(R.id.cab_rl_back)
    RelativeLayout mCabRlBack;
    @Bind(R.id.cab_tv_top_title)
    TextView mCabTvTopTitle;
    @Bind(R.id.cab_iv_reght_img)
    ImageView mCabIvReghtImg;
    @Bind(R.id.cab_rl_container)
    RelativeLayout mCabRlContainer;
    @Bind(R.id.amxw_pb_bar)
    ProgressBar mAmxwPbBar;
    @Bind(R.id.amxw_webview_webView)
    MyX5Webview webView;

    private String url;
    private String title;


    private String TAG = "X54CommonWebViewActivity";


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_x5_webview;
    }

    private void getParam() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(Constants.Keys.INTENT_WEB_KEY);
            title = extras.getString(Constants.Keys.INTENT_WEB_TITLE);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //获取上一个页面传递过来的值
        getParam();
        initView();
        initData();
        loadUrl();
        initListener();
    }

    private void initListener() {

    }

    private void initView() {
        mCabTvTopTitle.setText(getResources().getString(R.string.webview_loading));
    }

    private void initData() {
        //首先判断网址是否合法
        if (url == null || url.equals("") || url.length() == 0) {
            ToastUtils.showLongToast(this, "请开发者确认跳转的key是否正确,已安全退出");
            finish();
        }
        if (RegexUtils.isIP(url) || RegexUtils.isURL(url) || url.startsWith("https://") || url.startsWith("http://")) {
            if (url.startsWith("https://") || url.startsWith("http://")) {

            } else {
                url = "http://" + url;
            }

        }
    }

    private void loadUrl() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                Log.i("打印日志", "网页加载失败");
            }
        });

        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mAmxwPbBar.setProgress(newProgress);
                if (newProgress == 100) {
                    Log.i("打印日志", "加载完成");
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView webView, String t) {
                super.onReceivedTitle(webView, t);
                if (title == null)
                    if (t != null)
                        mCabTvTopTitle.setText(t);
                    else mCabTvTopTitle.setText(url);
                else {
                    mCabTvTopTitle.setText(title);
                }
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }
}
