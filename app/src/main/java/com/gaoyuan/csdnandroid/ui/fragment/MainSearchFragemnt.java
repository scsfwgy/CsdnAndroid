package com.gaoyuan.csdnandroid.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.common.lib.adapter.BasicAdapter;
import com.common.lib.adapter.ViewHolder;
import com.common.lib.utils.KeyboardUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.BaseFragment;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.ui.activity.MainActivity;
import com.gaoyuan.csdnandroid.ui.activity.search.SearchDetailsActivity;
import com.gaoyuan.csdnandroid.utils.MySnackbarUtils;
import com.gaoyuan.csdnandroid.utils.MySpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * 作者：wgyscsf on 2016/12/24 16:47
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MainSearchFragemnt extends BaseFragment {
    @Bind(R.id.fms_tv_go)
    TextView mFmsTvGo;
    @Bind(R.id.fms_et_words)
    EditText mFmsEtWords;
    @Bind(R.id.fms_lv_listview)
    ListView mFmsLvListview;
    @Bind(R.id.fms_ll_history)
    LinearLayout mFmsLlHistory;
    @Bind(R.id.fms_ll_container)
    LinearLayout mFmsLlContainer;
    @Bind(R.id.fms_tv_type1)
    TextView type1;
    @Bind(R.id.fms_tv_type2)
    TextView type2;
    List<String> mStringList;
    BasicAdapter<String> mStringBasicAdapter;
    private int searchType;//搜索类型，0：搜索博客标题和csdnid;1:搜索博客专家名字和csdnid

    //导航栏图标
    ImageView icon;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main_search;
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
        icon = ((MainActivity) getActivity()).topReightImg;
        icon.setImageDrawable(getResources().getDrawable(R.drawable.help));
        icon.setVisibility(View.VISIBLE);

    }

    private void initData() {
        mStringList = new ArrayList<>();
    }

    private void initAdapter() {
        mStringBasicAdapter = new BasicAdapter<String>(getContext(), mStringList, R.layout.item_main_search_fragment_history) {
            @Override
            protected void render(ViewHolder holder, String item, final int position) {
                holder.setText(R.id.imsfh_tv_title, item);
                holder.getSubView(R.id.imsfh_iv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mStringList.remove(position);
                        MySpUtils.addHistory(getContext(), mStringList);
                        mStringBasicAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        mFmsLvListview.setAdapter(mStringBasicAdapter);
    }

    private void initListener() {
        mFmsLvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goSearch(mStringList.get(position));
            }
        });

        mFmsTvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddSp();
            }
        });
        mFmsEtWords.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    processAddSp();
                }
                return false;
            }
        });
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_blue));
                type2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_gray));
                type1.setText("√博客");
                type2.setText("博主");
                searchType = 0;
            }
        });
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_blue));
                type1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_gray));
                type1.setText("博客");
                type2.setText("√博主");
                searchType = 1;

            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Add the buttons
        builder.setMessage("鉴于技术的限制，搜索请尽量使用语义明确且范围确定的词汇，搜索仅取前十个字符。如果想要查看某位博主的文章，请尽量键入博主完整的名字或者id；当前搜索博主，仅支持博客专家。")
                .setTitle("帮助")
                .setIcon(R.drawable.me_me_toushu)
                .setCancelable(false)
                .setPositiveButton("我已了解", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        //show
        dialog.show();
    }

    private void processAddSp() {
        String trim = mFmsEtWords.getText().toString().trim();
        if (trim.length() == 0) {
            MySnackbarUtils.showYellowSnack(mFmsLlContainer, "请输入内容后进行搜索", getContext());
            return;
        }
        //开始处理逻辑
        goSearch(trim);
        //sp
        if (!mStringList.contains(trim)) {
            mStringList.add(trim);
            mStringBasicAdapter.notifyDataSetChanged();
            MySpUtils.addHistory(getContext(), mStringList);
        }
        //reset
        mFmsEtWords.setText("");
    }

    private void goSearch(String trim) {
        KeyboardUtils.hideSoftInput(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Keys.KEYWORDS, trim);
        bundle.putInt(Constants.Keys.SEARCHTYPE, searchType);
        go(SearchDetailsActivity.class, bundle);
    }

    private void loadData() {
        List<String> historyList = MySpUtils.getHistoryList(getContext());
        if (historyList != null && !historyList.isEmpty()) {
            mStringList.addAll(historyList);
            mStringBasicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            icon.setVisibility(View.INVISIBLE);
        } else {
            icon.setVisibility(View.VISIBLE);
        }
    }
}
