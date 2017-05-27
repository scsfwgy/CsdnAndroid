package com.gaoyuan.csdnandroid.ui.activity.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.common.lib.utils.IntentUtils;
import com.common.lib.utils.TimeUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.bean.PStar;
import com.gaoyuan.csdnandroid.myview.MyAnimationUtil;
import com.gaoyuan.csdnandroid.myview.MyWxMiddlePopupWindow;
import com.gaoyuan.csdnandroid.myview.MyXRefreshView;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.ExpertBolgsReqBean;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;
import com.gaoyuan.csdnandroid.webview.X54BlogDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CollectionActivity extends BaseActivity {
    @Bind(R.id.continer)
    LinearLayout continer;
    @Bind(R.id.ac_lv_listview)
    ListView mAcLvListview;
    @Bind(R.id.fmr_xsf_freshload)
    MyXRefreshView mFmrXsfFreshload;
    private BasicAdapter<Blog> mBlogBasicAdapter;
    private List<Blog> mBlogList;

    //弹出框相关
    MyWxMiddlePopupWindow mMyWxMiddlePopupWindow;
    private BasicAdapter<String> mStringBasicAdapter;
    private List<String> mStringList;
    private int currentPos;//当前长按的位置

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
        initData();
        initAdapter();
        initListener();
        loadData();
    }

    private void initView() {
    }

    private void initData() {
        mBlogList = new ArrayList<>();
    }

    private void initAdapter() {
        mBlogBasicAdapter = new BasicAdapter<Blog>(CollectionActivity.this, mBlogList, R.layout.item_collection) {
            @Override
            protected void render(ViewHolder holder, Blog item, int position) {
                holder.setText(R.id.ilaeb_tv_title, item.getTitle());
                holder.setText(R.id.ilaeb_tv_url, item.getDetailsUrl());
                GlideImageUtils.setConrnerImage(CollectionActivity.this, (ImageView) holder.getSubView(R.id.ilaeb_iv_headImg), item.getHeadImg());
                holder.setText(R.id.ilaeb_tv_author, item.getAuthor() == null ? item.getId_author() : item.getAuthor());
                holder.setText(R.id.ilaeb_tv_publish_date, item.getPublishDateTime());
                holder.setText(R.id.ilaeb_tv_view, "读：" + item.getViewNums());
                holder.setText(R.id.ilaeb_tv_comment, "评：" + item.getCommentNums());
                holder.setText(R.id.ilaeb_tv_collTime, TimeUtils.getFriendlyTimeSpanByNow(item.getpStar().getCreateTime()));
            }
        };
        mAcLvListview.setAdapter(mBlogBasicAdapter);
    }

    private void initListener() {
        mFmrXsfFreshload.setXRefreshViewListener(new MyXRefreshView.MyXRefreshViewListener() {
            @Override
            public void onRefresh() {
                nowPager = 0;
                loadData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadData();
            }
        });

        mAcLvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Keys.INTENT_WEB_KEY, mBlogList.get(position).getDetailsUrl());
                bundle.putSerializable(Constants.Keys.BLOG, mBlogList.get(position));
                go(X54BlogDetailsActivity.class, bundle);
            }
        });
        mAcLvListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentPos = position;
                if (mMyWxMiddlePopupWindow == null) {
                    initWxMiddlePopWindows();
                }
                mMyWxMiddlePopupWindow.showPopupWindow();
                return true;
            }

        });
    }

    private void loadData() {
        ExpertBolgsReqBean recommendReqBean = new ExpertBolgsReqBean();
        recommendReqBean.setNowPager(nowPager);
        recommendReqBean.setId_author(App.getPUser().getId_csdn());
        call(ApiClient.getApis().userAction_getPStarByCsdnId(recommendReqBean), new MySubscriber<BaseResp<List<Blog>>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                showToast(getString(R.string.error_conn_service));
            }

            @Override
            public void onNext(BaseResp<List<Blog>> resp) {
                Log.e(TAG, "onNext: " + resp.toString() + "---" + nowPager);
                if (resp.code == Constants.Http.SUCCESS) {
                    mFmrXsfFreshload.enableEmptyView(false);
                    //数据集不为空
                    if (resp.size != 0) {
                        //第一页。
                        if (nowPager == 0) {
                            mBlogList.clear();//主要考虑到刷新情况。
                        }
                        mBlogList.addAll(resp.data);
                        //最后一页
                        if (resp.size < pagerSize) {
                            Log.e(TAG, "onNext:----->>>");
                            mFmrXsfFreshload.setLoadComplete(true);
                        } else
                            nowPager++;
                    } else {
                        if (nowPager == 0 && resp.size == 0) {
                            mFmrXsfFreshload.setNoDataView(true);
                        }
                        //返回数据集为空
                        mFmrXsfFreshload.setLoadComplete(true);
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
                if (mFmrXsfFreshload.mPullRefreshing)
                    mFmrXsfFreshload.stopRefresh();
                mFmrXsfFreshload.stopLoadMore();
                mBlogBasicAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initWxMiddlePopWindows() {
        mStringList = new ArrayList<>();
        mMyWxMiddlePopupWindow = new MyWxMiddlePopupWindow(CollectionActivity.this);
        mStringBasicAdapter = new BasicAdapter<String>(CollectionActivity.this, mStringList, R.layout.item_wx_middle_pop) {
            @Override
            protected void render(ViewHolder holder, String item, int position) {
                holder.setText(R.id.ilaeb_tv_title, item);
            }
        };
        mMyWxMiddlePopupWindow.getPcdGvGridview().setAdapter(mStringBasicAdapter);

        mStringList.add("删除");
        mStringList.add("分享");
        mStringList.add("在浏览器打开");
        mStringBasicAdapter.notifyDataSetChanged();

        mMyWxMiddlePopupWindow.getPcdGvGridview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Blog blog = mBlogList.get(currentPos);
                if (position == 0) {
                    delCollById(blog.getpStar());
                } else if (position == 1) {
                    MySnackbarUtils.showYellowSnack(continer, "还未开发~", CollectionActivity.this);
                } else if (position == 2) {
                    showToast(getString(R.string.open_brower));
                    IntentUtils.openBrower(CollectionActivity.this, blog.getDetailsUrl());
                } else {
                    MySnackbarUtils.showYellowSnack(continer, "还未开发~", CollectionActivity.this);
                }

                mMyWxMiddlePopupWindow.dismiss();
            }
        });

    }

    private void delCollById(PStar pStar) {
        call(ApiClient.getApis().userAction_delStar(pStar), new MySubscriber<BaseResp<Boolean>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                showToast(getString(R.string.error_conn_service));
            }

            @Override
            public void onNext(BaseResp<Boolean> resp) {
                Log.e(TAG, "onNext: " + resp.toString());
                if (resp.code == Constants.Http.SUCCESS) {
                    if (resp.data) {
                        mBlogList.remove(currentPos);
                        mBlogBasicAdapter.notifyDataSetChanged();
                        nowPager = 0;
                        mFmrXsfFreshload.startRefresh();
                    } else {
                        MySnackbarUtils.showYellowSnack(continer, getResources().getString(R.string.error_request_service), CollectionActivity.this);
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

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }
}
