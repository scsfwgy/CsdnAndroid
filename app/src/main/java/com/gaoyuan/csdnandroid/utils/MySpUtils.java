package com.gaoyuan.csdnandroid.utils;

import android.content.Context;

import com.common.lib.utils.EmptyUtils;
import com.common.lib.utils.SPUtils;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.ui.activity.me.SettingActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 作者：wgyscsf on 2017/5/1 16:58
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MySpUtils {
    public static boolean addPUser(Context context, PUser pUser) {
        if (pUser == null) {
            throw new NullPointerException("请确认传递的pUser不为空");
        }
        SPUtils spUtils = new SPUtils(context, Constants.SP.SP_ROOT);
        spUtils.putString(Constants.SP.PUSER, new Gson().toJson(pUser));
        return true;
    }

    public static PUser getPUser(Context context) {
        SPUtils spUtils = new SPUtils(context, Constants.SP.SP_ROOT);
        String pUSerStr = spUtils.getString(Constants.SP.PUSER, null);
        if (EmptyUtils.isEmpty(pUSerStr)) return null;
        Gson gson = new Gson();
        PUser pUser = gson.fromJson(pUSerStr, PUser.class);
        return pUser;
    }

    public static boolean clearPuser(Context context) {
        SPUtils spUtils = new SPUtils(context, Constants.SP.SP_ROOT);
        spUtils.remove(Constants.SP.PUSER);
        App.setPUser(null);
        return true;
    }

    //history操作相关
    public static boolean addHistory(Context context, List<String> stringList) {
        if (stringList == null) {
            throw new NullPointerException("请确认传递的str不为空");
        }
        SPUtils spUtils = new SPUtils(context, Constants.SP.SP_ROOT);
        spUtils.putString(Constants.SP.SEARCH_HISTORY, new Gson().toJson(stringList));
        return true;
    }

    public static List<String> getHistoryList(Context context) {
        SPUtils spUtils = new SPUtils(context, Constants.SP.SP_ROOT);
        String historyList = spUtils.getString(Constants.SP.SEARCH_HISTORY, null);
        if (EmptyUtils.isEmpty(historyList)) return null;
        Gson gson = new Gson();
        return gson.fromJson(historyList, new TypeToken<List<String>>() {
        }.getType());
    }

    public boolean firstComeSuppert(SettingActivity settingActivity) {
        SPUtils spUtils = new SPUtils(settingActivity, Constants.SP.SP_ROOT);
        spUtils.putInt(Constants.SP.FIRST_SUPPERT, 1);
        return true;
    }

    public boolean firstComeAbout(SettingActivity settingActivity) {
        SPUtils spUtils = new SPUtils(settingActivity, Constants.SP.SP_ROOT);
        spUtils.putInt(Constants.SP.FIRST_ABOUT, 1);
        return true;
    }

    public boolean isfirstComeSuppert(SettingActivity settingActivity) {
        SPUtils spUtils = new SPUtils(settingActivity, Constants.SP.SP_ROOT);
        return spUtils.getInt(Constants.SP.FIRST_SUPPERT) == 1 ? true : false;
    }

    public boolean isfirstComeAbout(SettingActivity settingActivity) {
        SPUtils spUtils = new SPUtils(settingActivity, Constants.SP.SP_ROOT);
        return spUtils.getInt(Constants.SP.FIRST_ABOUT) == 1 ? true : false;
    }
}
