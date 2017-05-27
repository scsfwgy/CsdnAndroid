package com.gaoyuan.csdnandroid.ui.activity.me;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseActivity;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.MyModule;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.event.RefreshHome;
import com.gaoyuan.csdnandroid.net.ApiClient;
import com.gaoyuan.csdnandroid.net.BaseResp;
import com.gaoyuan.csdnandroid.net.MySubscriber;
import com.gaoyuan.csdnandroid.net.req.LoginReqBean;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;
import com.gaoyuan.csdnandroid.utils.MySpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class FavoriteModulesActivity extends BaseActivity {


    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.cab_tv_right_txt)
    TextView save;
    @Bind(R.id.afm_gv_gridview)
    GridView mAfmGvGridview;
    BasicAdapter<MyModule> mMyModuleBasicAdapter;
    List<MyModule> mMyModuleList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_favorite_modules;
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
        save.setText("保存");
        save.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mMyModuleList = new ArrayList<>();

        MyModule myModule2 = new MyModule();
        myModule2.setName("移动开发");
        myModule2.setSelected(true);
        myModule2.setId(1);

        MyModule myModule3 = new MyModule();
        myModule3.setName("web前端");
        myModule3.setSelected(true);
        myModule3.setId(2);


        MyModule myModule4 = new MyModule();
        myModule4.setName("架构设计");
        myModule4.setId(3);
        myModule4.setSelected(true);

        MyModule myModule5 = new MyModule();
        myModule5.setName("编程语言");
        myModule5.setId(4);

        myModule5.setSelected(true);

        MyModule myModule6 = new MyModule();
        myModule6.setName("互联网");
        myModule6.setId(5);

        myModule6.setSelected(true);

        MyModule myModule7 = new MyModule();
        myModule7.setName("数据库");
        myModule7.setId(6);

        myModule7.setSelected(true);

        MyModule myModule8 = new MyModule();
        myModule8.setName("系统运维");
        myModule8.setId(7);

        myModule8.setSelected(true);

        MyModule myModule9 = new MyModule();
        myModule9.setName("云计算");
        myModule9.setId(8);

        myModule9.setSelected(true);

        MyModule myModule10 = new MyModule();
        myModule10.setName("研发管理");
        myModule10.setId(9);

        myModule10.setSelected(true);

        mMyModuleList.add(myModule2);
        mMyModuleList.add(myModule3);
        mMyModuleList.add(myModule4);
        mMyModuleList.add(myModule5);
        mMyModuleList.add(myModule6);
        mMyModuleList.add(myModule7);
        mMyModuleList.add(myModule8);
        mMyModuleList.add(myModule9);
        mMyModuleList.add(myModule10);
    }

    private void initAdapter() {
        mMyModuleBasicAdapter = new BasicAdapter<MyModule>(this, mMyModuleList, R.layout.item_favorite_modules) {
            @Override
            protected void render(ViewHolder holder, MyModule item, int position) {
                holder.setText(R.id.ipcbd_tv_text, item.getName());
                if (item.isSelected()) {
                    holder.getSubView(R.id.ipcbd_tv_text).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_blue));
                } else {
                    holder.getSubView(R.id.ipcbd_tv_text).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_gray));
                }
            }
        };
        mAfmGvGridview.setAdapter(mMyModuleBasicAdapter);

    }

    private void initListener() {
        mAfmGvGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyModule myModule = mMyModuleList.get(position);
                if (myModule.isSelected()) {
                    myModule.setSelected(false);
                } else {
                    myModule.setSelected(true);
                }
                mAfmGvGridview.setAdapter(mMyModuleBasicAdapter);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unlikeType = "";
                for (int i = 0; i < mMyModuleList.size(); i++) {
                    if (!mMyModuleList.get(i).isSelected() && i != mMyModuleList.size() - 1) {
                        unlikeType += mMyModuleList.get(i).getId() + "_";
                    } else if (!mMyModuleList.get(i).isSelected()) {
                        unlikeType += mMyModuleList.get(i).getId();
                    }
                }

                if (unlikeType.split("_").length == 9) {
                    MySnackbarUtils.showYellowSnack(container, "至少选择一个模块", FavoriteModulesActivity.this);
                    return;
                }
                Log.e(TAG, "onClick: " + unlikeType);
                App.getPUser().setDislikeType(unlikeType);
                updatePUser();
                MySpUtils.addPUser(FavoriteModulesActivity.this, App.getPUser());
                //刷新首页
                EventBus.getDefault().post(new RefreshHome());
                finish();
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

    private void loadData() {
        String dislikeType = App.getPUser().getDislikeType();
        if (dislikeType == null || dislikeType.equals("")) {
            return;
        }
        String[] dislikeId = dislikeType.split("_");
        List<Integer> integerList = new ArrayList<>();
        for (String s : dislikeId) {
            integerList.add(Integer.parseInt(s));
        }
        if (integerList.contains(1)) {
            mMyModuleList.get(0).setSelected(false);
        }
        if (integerList.contains(2)) {
            mMyModuleList.get(1).setSelected(false);
        }
        if (integerList.contains(3)) {
            mMyModuleList.get(2).setSelected(false);
        }
        if (integerList.contains(4)) {
            mMyModuleList.get(3).setSelected(false);
        }
        if (integerList.contains(5)) {
            mMyModuleList.get(4).setSelected(false);
        }
        if (integerList.contains(6)) {
            mMyModuleList.get(5).setSelected(false);
        }
        if (integerList.contains(7)) {
            mMyModuleList.get(6).setSelected(false);
        }
        if (integerList.contains(8)) {
            mMyModuleList.get(7).setSelected(false);
        }
        if (integerList.contains(9)) {
            mMyModuleList.get(8).setSelected(false);
        }
        mMyModuleBasicAdapter.notifyDataSetChanged();
    }

}
