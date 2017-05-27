package com.gaoyuan.csdnandroid.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseFragment;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.Expert;
import com.gaoyuan.csdnandroid.myview.MyXRefreshView;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.ExpertsReqBean;
import com.gaoyuan.csdnandroid.ui.activity.expert.ExpertBlogsActivity;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：wgyscsf on 2017/4/29 22:21
 * 邮箱：wgyscsf@163.com
 * 博客：http://Expert.csdn.net/wgyscsf
 */
@SuppressLint("ValidFragment")
public class ExpertListFragment extends BaseFragment {

    @Bind(R.id.fel_lv_listview)
    ListView mFmrLvListview;
    @Bind(R.id.fel_xsf_freshload)
    MyXRefreshView mFmrXsfFreshload;

    private int nowPager;
    private BasicAdapter<Expert> mExpertBasicAdapter;
    private List<Expert> mExpertList;
    //分类
    private int typeId;
    private int orderId;


    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_expert_list;
    }

    public ExpertListFragment() {
    }

    public ExpertListFragment(int typeId) {
        this.typeId = typeId;
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
        mExpertList = new ArrayList<>();
    }

    private void initAdapter() {
        mExpertBasicAdapter = new BasicAdapter<Expert>(getContext(), mExpertList, R.layout.item_fragment_expert_list) {
            @Override
            protected void render(ViewHolder holder, Expert item, int position) {
                GlideImageUtils.setConrnerImage(getContext(), (ImageView) holder.getSubView(R.id.ifel_iv_headImg), item.getHeadImg());
                holder.setText(R.id.ifel_tv_name, item.getName());
                holder.setText(R.id.ifel_tv_type, item.getTypeName());
                holder.setText(R.id.ifel_tv_readNums, "阅读量：" + item.getReadNums());
                holder.setText(R.id.ifel_tv_articalNums, "文章量：" + item.getArticleNums());
                holder.setText(R.id.ifel_tv_url, "http://blog.csdn.net/" + item.getId_expert());
            }
        };
        mFmrLvListview.setAdapter(mExpertBasicAdapter);
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
                bundle.putSerializable(Constants.Keys.INTENT_EXPERTDETAILS_KEY, mExpertList.get(position));
                go(ExpertBlogsActivity.class, bundle);
            }
        });
    }

    private void loadData() {
        ExpertsReqBean expertsReqBean = new ExpertsReqBean();
        expertsReqBean.setNowPager(nowPager);
        expertsReqBean.setOrderId(orderId);
        expertsReqBean.setTypeId(typeId);
        call(ApiClient.getApis().coreAction_getExpertsByTypeId(expertsReqBean), new MySubscriber<BaseResp<List<Expert>>>() {
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                requestServiceErr();
            }

            @Override
            public void onNext(BaseResp<List<Expert>> resp) {
                if (resp.code == Constants.Http.SUCCESS) {
                    mFmrXsfFreshload.enableEmptyView(false);
                    //数据集不为空
                    if (resp.size != 0) {
                        //第一页。
                        if (nowPager == 0) {
                            mExpertList.clear();//主要考虑到刷新情况。
                        }
                        mExpertList.addAll(resp.data);
                        Log.e(TAG, "onNext: " + resp.data.get(0));
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
                mExpertBasicAdapter.notifyDataSetChanged();
            }
        });
    }

    private void requestServiceErr() {
        showToast(getString(R.string.error_conn_service));
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
