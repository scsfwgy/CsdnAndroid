package com.gaoyuan.csdnandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 作者：wgyscsf on 2017/5/1 10:13
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class ToastUtils {
    public static void ToastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
