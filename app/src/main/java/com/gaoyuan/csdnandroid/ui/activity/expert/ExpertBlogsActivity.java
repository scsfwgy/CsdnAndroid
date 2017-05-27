package com.gaoyuan.csdnandroid.ui.activity.expert;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.common.lib.utils.TimeUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.bean.Expert;
import com.gaoyuan.csdnandroid.myview.MyXRefreshView;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.ExpertBolgsReqBean;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;
import com.gaoyuan.csdnandroid.webview.X54BlogDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ExpertBlogsActivity extends BaseActivity {


    @Bind(R.id.aeb_lv_listview)
    ListView mAebLvListview;
    @Bind(R.id.aeb_xsf_freshload)
    MyXRefreshView mAebXsfFreshload;
    @Bind(R.id.aeb_ll_container)
    LinearLayout mAebLlContainer;
    @Bind(R.id.cab_rl_back)
    RelativeLayout mCabRlBack;
    @Bind(R.id.cab_tv_top_title)
    TextView mCabTvTopTitle;

    private int nowPager;
    private BasicAdapter<Blog> mBlogBasicAdapter;
    private List<Blog> mBlogList;
    private String authorId;
    private Expert mExpert;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_expert_blogs;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mExpert = (Expert) extras.getSerializable(Constants.Keys.INTENT_EXPERTDETAILS_KEY);
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
        authorId = mExpert.getId_expert();
        mCabTvTopTitle.setText(mExpert.getName() != null ? mExpert.getName() + "的博客列表" : mExpert.getId_expert() + "的博客列表");
    }

    private void initAdapter() {
        mBlogBasicAdapter = new BasicAdapter<Blog>(ExpertBlogsActivity.this, mBlogList, R.layout.item_main_recommend_fragment) {
            @Override
            protected void render(ViewHolder holder, Blog item, int position) {
                holder.setText(R.id.ilaeb_tv_title, item.getTitle());
                holder.setText(R.id.ilaeb_tv_url, item.getDetailsUrl());
                GlideImageUtils.setConrnerImage(ExpertBlogsActivity.this, (ImageView) holder.getSubView(R.id.ilaeb_iv_headImg), item.getHeadImg());
                holder.setText(R.id.ilaeb_tv_author, item.getAuthor());
                holder.setText(R.id.ilaeb_tv_publish_date, TimeUtils.getFriendlyTimeSpanByNow(item.getPublishDateTime(),"yyyy-MM-dd HH:mm"));
                holder.setText(R.id.ilaeb_tv_view, "读：" + item.getViewNums());
                holder.setText(R.id.ilaeb_tv_comment, "评：" + item.getCommentNums());
            }
        };
        mAebLvListview.setAdapter(mBlogBasicAdapter);
    }

    private void initListener() {
        mAebXsfFreshload.setXRefreshViewListener(new MyXRefreshView.MyXRefreshViewListener() {
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

        mAebLvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Keys.INTENT_WEB_KEY, mBlogList.get(position).getDetailsUrl());
                bundle.putSerializable(Constants.Keys.BLOG, mBlogList.get(position));
                go(X54BlogDetailsActivity.class, bundle);
            }
        });
        mCabRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        ExpertBolgsReqBean recommendReqBean = new ExpertBolgsReqBean();
        recommendReqBean.setId_author(authorId);
        recommendReqBean.setOrderId(3);
        recommendReqBean.setNowPager(nowPager);
        call(ApiClient.getApis().coreAction_getAuthorBlogsByAuthorId(recommendReqBean), new MySubscriber<BaseResp<List<Blog>>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                requestServiceErr();
            }

            @Override
            public void onNext(BaseResp<List<Blog>> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    mAebXsfFreshload.enableEmptyView(false);
                    //数据集不为空
                    if (resp.size != 0) {
                        //第一页。
                        if (nowPager == 0) {
                            mBlogList.clear();//主要考虑到刷新情况。
                        }
                        mBlogList.addAll(resp.data);
                        Log.e(TAG, "onNext: " + resp.data.get(0));
                        //最后一页
                        if (resp.size < pagerSize) {
                            mAebXsfFreshload.setLoadComplete(true);
                        } else
                            nowPager++;
                    } else {
                        if (nowPager == 0 && resp.size == 0) {
                            mAebXsfFreshload.setNoDataView(true);
                        }
                        //返回数据集为空
                        mAebXsfFreshload.setLoadComplete(true);
                    }
                } else if (resp.code == Constants.Http.REQUEST_ERROR) {
                    requestServiceErr();

                    Log.e(TAG, "onNext: 请求参数错误。" + resp.toString());
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
                if (mAebXsfFreshload.mPullRefreshing)
                    mAebXsfFreshload.stopRefresh();
                mAebXsfFreshload.stopLoadMore();
                mBlogBasicAdapter.notifyDataSetChanged();
            }
        });
    }

    private void requestServiceErr() {
        showToast(getString(R.string.error_conn_service));
    }

    @Override
    protected boolean isSwipeBackHere() {
        return true;
    }
}
