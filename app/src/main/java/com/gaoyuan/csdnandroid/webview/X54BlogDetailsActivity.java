package com.gaoyuan.csdnandroid.webview;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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

public class X54BlogDetailsActivity extends AppCompatActivity {
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
    private Blog mBlog;
    //pop相关
    MyWxBottomUpPopupWindow mMyWxPopupWindow;
    BasicAdapter<Pop> mPopBasicAdapter;
    List<Pop> mPopList;
    //weview是否正在上滑
    boolean isUpping = false;

    private CompositeSubscription subscriptions;

    private boolean noAd = true;

    private String TAG = "X54BlogDetailsActivity";


    private void getParam() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(Constants.Keys.INTENT_WEB_KEY);
            mBlog = (Blog) extras.getSerializable(Constants.Keys.BLOG);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_x5_webview);
        ButterKnife.bind(this);
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
        mCabRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mCabIvReghtImg.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (mMyWxPopupWindow == null) {
                                                      initPop();
                                                  }
                                                  mMyWxPopupWindow.showPopupWindow();
                                              }

                                              private void initPop() {
                                                  mMyWxPopupWindow = new MyWxBottomUpPopupWindow(X54BlogDetailsActivity.this);
                                                  mPopList = new ArrayList<Pop>();
                                                  mPopBasicAdapter = new BasicAdapter<Pop>(X54BlogDetailsActivity.this, mPopList, R.layout.item_pop_common_blog_details) {
                                                      @Override
                                                      protected void render(ViewHolder holder, Pop item, int position) {
                                                          holder.setText(R.id.ipcbd_tv_text, item.getText());
                                                          GlideImageUtils.setImage(X54BlogDetailsActivity.this, (ImageView) holder.getSubView(R.id.ipcbd_iv_img), item.getImgId());
                                                      }
                                                  };
                                                  mMyWxPopupWindow.getPcdGvGridview().setAdapter(mPopBasicAdapter);

                                                  final Pop pop1 = new Pop();
                                                  pop1.setImgId(R.drawable.m_star);
                                                  pop1.setText("收藏");

                                                  Pop pop2 = new Pop();
                                                  pop2.setImgId(R.drawable.m_ding);
                                                  pop2.setText("好文要顶");

                                                  Pop pop3 = new Pop();
                                                  pop3.setImgId(R.drawable.m_copy);
                                                  pop3.setText("复制链接");

                                                  Pop pop4 = new Pop();
                                                  pop4.setImgId(R.drawable.m_ie);
                                                  pop4.setText("在浏览器打开");

                                                  Pop pop5 = new Pop();
                                                  pop5.setImgId(R.drawable.m_refsh);
                                                  pop5.setText("刷新");

                                                  Pop pop6 = new Pop();
                                                  pop6.setImgId(R.drawable.m_unlike);
                                                  pop6.setText("不感兴趣");

                                                  Pop pop7 = new Pop();
                                                  pop7.setImgId(R.drawable.m_share);
                                                  pop7.setText("分享");

                                                  Pop pop8 = new Pop();
                                                  pop8.setImgId(R.drawable.m_ad);
                                                  pop8.setText("不屏蔽广告");

                                                  mPopList.add(pop1);
                                                  mPopList.add(pop2);
                                                  mPopList.add(pop3);
                                                  mPopList.add(pop4);
                                                  mPopList.add(pop5);
                                                  mPopList.add(pop6);
                                                  mPopList.add(pop7);
                                                  mPopList.add(pop8);
                                                  mPopBasicAdapter.notifyDataSetChanged();
                                                  mMyWxPopupWindow.getHintText().setText("目标地址：" + url);
                                                  mMyWxPopupWindow.getPcdGvGridview()
                                                          .setOnItemClickListener(
                                                                  new AdapterView.OnItemClickListener() {
                                                                      @Override
                                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                          if (position == 0) {
                                                                              if (App.getPUser() == null) {
                                                                                  loginFirst();
                                                                              } else {
                                                                                  addPstar();
                                                                              }
                                                                          } else if (position == 1) {
                                                                              if (App.getPUser() == null) {
                                                                                  loginFirst();
                                                                              } else {
                                                                                  ding();
                                                                              }
                                                                          } else if (position == 2) {
                                                                              ClipboardUtils.copyText(X54BlogDetailsActivity.this, url);
                                                                              MySnackbarUtils.showBlueSnack(continer, "链接复制成功", X54BlogDetailsActivity.this);
                                                                          } else if (position == 3) {
                                                                              ToastUtils.showLongToast(X54BlogDetailsActivity.this, getString(R.string.open_brower));
                                                                              IntentUtils.openBrower(X54BlogDetailsActivity.this, url);
                                                                          } else if (position == 4) {
                                                                              loadUrl();
                                                                          } else if (position == 5) {
                                                                              if (App.getPUser() == null) {
                                                                                  loginFirst();
                                                                              } else {
                                                                                  unLike();
                                                                              }
                                                                          } else if (position == 6) {
                                                                              share();
                                                                          } else if (position == 7) {
                                                                              Pop pop = mPopList.get(7);
                                                                              if (noAd) {
                                                                                  noAd = false;
                                                                                  pop.setText("屏蔽广告");
                                                                                  pop.setImgId(R.drawable.m_nad);
                                                                              } else {
                                                                                  noAd = true;
                                                                                  pop.setText("不屏蔽广告");
                                                                                  pop.setImgId(R.drawable.m_ad);
                                                                              }
                                                                              mPopBasicAdapter.notifyDataSetChanged();
                                                                              loadUrl();
                                                                          }

                                                                          mMyWxPopupWindow.dismiss();


                                                                      }
                                                                  }

                                                          );
                                              }
                                          }

        );
    }

    private void share() {
        MySnackbarUtils.showYellowSnack(continer, "分享功能还未开发~", X54BlogDetailsActivity.this);
    }

    private void unLike() {
        processPreference(2);
    }

    private void ding() {
        processPreference(1);
    }

    private void loginFirst() {
        MySnackbarUtils.showYellowSnack(continer, "请先登录", X54BlogDetailsActivity.this);
    }

    private void processPreference(final int type) {
        Preference recommendReqBean = new Preference();
        recommendReqBean.setId_blog(mBlog.getId_blog());
        recommendReqBean.setId_csdn(App.getPUser().getId_csdn());
        recommendReqBean.setType(type);
        Subscription subscription = ApiClient.call(ApiClient.getApis().userAction_addPreference(recommendReqBean), new MySubscriber<BaseResp<Boolean>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                ToastUtils.showLongToast(X54BlogDetailsActivity.this, getString(R.string.error_conn_service));
            }

            @Override
            public void onNext(BaseResp<Boolean> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    //数据集不为空
                    if (type == 1) {
                        if (resp.data) {
                            MySnackbarUtils.showBlueSnack(continer, "已顶，我们会提高该文章的推荐值", X54BlogDetailsActivity.this);
                        } else {
                            MySnackbarUtils.showBlueSnack(continer, "你之前已经推荐过~", X54BlogDetailsActivity.this);
                        }
                    } else if (type == 2) {
                        if (resp.data) {
                            MySnackbarUtils.showBlueSnack(continer, "不会再给你推荐该文章~", X54BlogDetailsActivity.this);
                        } else {
                            MySnackbarUtils.showBlueSnack(continer, "不会再给你推荐该文章~", X54BlogDetailsActivity.this);

                        }
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
                mMyWxPopupWindow.dismiss();
            }
        });
        subscriptions.add(subscription);
    }

    private void addPstar() {
        PStarReqBean recommendReqBean = new PStarReqBean();
        recommendReqBean.setId_blog(mBlog.getId_blog());
        recommendReqBean.setId_csdn(App.getPUser().getId_csdn());
        Subscription subscription = ApiClient.call(ApiClient.getApis().userAction_addStar(recommendReqBean), new MySubscriber<BaseResp<Boolean>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                ToastUtils.showLongToast(X54BlogDetailsActivity.this, getString(R.string.error_conn_service));
            }

            @Override
            public void onNext(BaseResp<Boolean> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    //数据集不为空
                    if (resp.data) {
                        SnackbarUtils.showLongSnackbar(continer, "收藏成功", getResources().getColor(R.color.white), getResources().getColor(R.color.colorBlue));
                    } else {
                        //返回数据集为空
                        SnackbarUtils.showLongSnackbar(continer, "收藏失败，请稍后再试", getResources().getColor(R.color.white), getResources().getColor(R.color.colorYellow));
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
                mMyWxPopupWindow.dismiss();
            }
        });
        subscriptions.add(subscription);
    }

    private void initView() {
        mCabTvTopTitle.setText("正在加载...");

        mCabIvReghtImg.setVisibility(View.VISIBLE);

    }

    private void initData() {
        subscriptions = new CompositeSubscription();
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
                    if (noAd)
                        webView.loadUrl(Constants.CsdnAdFilter.MOBLE_CLEAR_AD);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                if (title != null)
                    mCabTvTopTitle.setText(title);
                else mCabTvTopTitle.setText(url);
            }

        });
        //这里效果一直调试不好，求完善
        webView.setOnScrollChangedCallback(new MyX5Webview.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
//                if (dy > 40 && !isUpping) {
//                    isUpping = true;
//                    Animation anim = AnimationUtils.loadAnimation(
//                            X54BlogDetailsActivity.this, R.anim.push_top_out);
//                    mCabRlContainer.setVisibility(View.GONE);
//                    mCabRlContainer.startAnimation(anim);
//                }
//                if (dy < -40 && isUpping) {
//                    isUpping = false;
//
//                    Animation anim = AnimationUtils.loadAnimation(
//                            X54BlogDetailsActivity.this, R.anim.push_top_in);
//                    mCabRlContainer.setVisibility(View.VISIBLE);
//                    mCabRlContainer.startAnimation(anim);
//                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
        subscriptions.unsubscribe();
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
}
