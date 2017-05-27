package com.gaoyuan.csdnandroid.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.common.lib.utils.EmptyUtils;
import com.common.lib.utils.TimeUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseFragment;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.event.RefreshHome;
import com.gaoyuan.csdnandroid.net.req.RecommendReqBean;
import com.gaoyuan.csdnandroid.myview.MyXRefreshView;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.ui.activity.MainActivity;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;
import com.gaoyuan.csdnandroid.webview.X54BlogDetailsActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainRecommendFragemnt extends BaseFragment {

    @Bind(R.id.fmr_lv_listview)
    ListView mFmrLvListview;
    @Bind(R.id.fmr_xsf_freshload)
    MyXRefreshView mFmrXsfFreshload;

    private int nowPager;
    private BasicAdapter<Blog> mBlogBasicAdapter;
    private List<Blog> mBlogList;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main_recommend;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
        initData();
        initAdapter();
        initListener();
        loadData();
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    private void initView() {
    }

    private void initData() {
        mBlogList = new ArrayList<>();
    }

    private void initAdapter() {
        mBlogBasicAdapter = new BasicAdapter<Blog>(getContext(), mBlogList, R.layout.item_main_recommend_fragment) {
            @Override
            protected void render(ViewHolder holder, Blog item, int position) {
                holder.setText(R.id.ilaeb_tv_title, item.getTitle());
                holder.setText(R.id.ilaeb_tv_url, item.getDetailsUrl());
                GlideImageUtils.setConrnerImage(getContext(), (ImageView) holder.getSubView(R.id.ilaeb_iv_headImg), item.getHeadImg());
                holder.setText(R.id.ilaeb_tv_author, item.getAuthor());
                holder.setText(R.id.ilaeb_tv_publish_date, TimeUtils.getFriendlyTimeSpanByNow(item.getPublishDateTime(),"yyyy-MM-dd HH:mm"));
                holder.setText(R.id.ilaeb_tv_view, "读：" + item.getViewNums());
                holder.setText(R.id.ilaeb_tv_comment, "评：" + item.getCommentNums());
            }
        };
        mFmrLvListview.setAdapter(mBlogBasicAdapter);
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

        mFmrLvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Keys.INTENT_WEB_KEY, mBlogList.get(position).getDetailsUrl());
                bundle.putSerializable(Constants.Keys.BLOG, mBlogList.get(position));
                go(X54BlogDetailsActivity.class, bundle);
            }
        });

        //回到顶部
        ((MainActivity) getActivity()).mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doubleKilltoTop();
            }
        });
    }

    private void loadData() {
        RecommendReqBean recommendReqBean = new RecommendReqBean();
        if (EmptyUtils.isNotEmpty(App.getPUser()))
            if (EmptyUtils.isNotEmpty(App.getPUser().getDislikeType())) {
                recommendReqBean.setDislikeType(App.getPUser().getDislikeType());
            }
        recommendReqBean.setNowPager(nowPager);
        call(ApiClient.getApis().recommendAction_getRecommendBlogsByTypeIdByLogined(recommendReqBean), new MySubscriber<BaseResp<List<Blog>>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                requestServiceErr();
            }

            @Override
            public void onNext(BaseResp<List<Blog>> resp) {
                Log.e(TAG, "onNext: "+resp.toString() );
                if (resp.code == Constants.Http.SUCCESS) {
                    //数据加载过来，隐藏empty
                    mFmrXsfFreshload.enableEmptyView(false);
                    //数据集不为空
                    if (resp.size != 0) {
                        //第一页。
                        if (nowPager == 0) {
                            mBlogList.clear();//主要考虑到刷新情况。
                        }
                        mBlogList.addAll(resp.data);
                        Log.e(TAG, "onNext: " + resp.data.get(0));
                        nowPager++;
                    } else {
                        if (nowPager == 0 && resp.size == 0) {
                            mFmrXsfFreshload.setNoDataView(true);
                        }
                    }
                } else if (resp.code == Constants.Http.REQUEST_ERROR) {
                    Log.e(TAG, "onNext: 请求参数错误。" + resp.toString());
                    requestServiceErr();

                } else if (resp.code == Constants.Http.SERVICE_ERROR) {
                    requestServiceErr();

                    Log.e(TAG, "onNext: 服务器异常。" + resp.toString());
                } else {
                    requestServiceErr();

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

    private void requestServiceErr() {
        showToast(getString(R.string.error_conn_service));
    }

    @Subscribe
    public void onEventRefresh(RefreshHome refreshHome) {
        mFmrXsfFreshload.startRefresh();
    }

    private long topTime = 0;

    private void doubleKilltoTop() {
        if (topTime <= 0) {
            topTime = System.currentTimeMillis();
        } else {
            long nowtime = System.currentTimeMillis();
            if (nowtime - topTime < 3000) {
                mFmrLvListview.setSelection(0);
            } else {
                topTime = System.currentTimeMillis();
            }
        }
    }

    public MyXRefreshView getFmrXsfFreshload() {
        return mFmrXsfFreshload;
    }

    public void setFmrXsfFreshload(MyXRefreshView fmrXsfFreshload) {
        mFmrXsfFreshload = fmrXsfFreshload;
    }
}
