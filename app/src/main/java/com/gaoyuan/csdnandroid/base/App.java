package com.gaoyuan.csdnandroid.base;

import android.app.Application;
import android.util.Log;

import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.utils.MySpUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import c.b.BP;


public class App extends Application {
    private static final String TAG = "App";
    private static App inst;
    private static PUser sPUser;

    public static PUser getPUser() {
        return sPUser;
    }

    public static void setPUser(PUser PUser) {
        sPUser = PUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inst = this;
        initTbs();
        sPUser = MySpUtils.getPUser(inst);
        //支付
        BP.init("e68a008b476ddbea2ef4a2eb8639ae80");
        //bugly
        //CrashReport.initCrashReport(getApplicationContext(), "133d2b9d85", false);
        Bugly.init(getApplicationContext(), "133d2b9d85", false);
    }

    public static App getInst() {
        return inst;
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.e(TAG, "onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

}
