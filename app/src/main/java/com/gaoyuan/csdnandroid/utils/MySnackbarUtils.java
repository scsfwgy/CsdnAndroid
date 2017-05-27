package com.gaoyuan.csdnandroid.utils;

import android.content.Context;
import android.view.View;

import com.common.lib.utils.SnackbarUtils;
import com.gaoyuan.csdnandroid.R;

/**
 * 作者：wgyscsf on 2017/5/2 15:59
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MySnackbarUtils {
    public static void showBlueSnack(View view, String text, Context context) {
        SnackbarUtils.showLongSnackbar(view, text, context.getResources().getColor(R.color.white), context.getResources().getColor(R.color.colorBlue));
    }

    public static void showYellowSnack(View view, String text, Context context) {
        SnackbarUtils.showLongSnackbar(view, text, context.getResources().getColor(R.color.white), context.getResources().getColor(R.color.colorYellow));
    }
}
