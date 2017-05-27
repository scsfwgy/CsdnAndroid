package com.gaoyuan.csdnandroid.net;

import android.util.Log;


import com.common.lib.utils.NetworkUtils;
import com.common.lib.utils.StorageUtils;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiClient {
    private static final String TAG = "ApiClient";

    private static final int CONNECT_TIMEOUT = 40;
    private static final int READ_TIMEOUT = 40;
    private static final int WRITE_TIMEOUT = 40;
    private static final long MAX_CACHE = 30 * 1024 * 1024;

    private static Apis apis;

    private ApiClient() {

    }

    public static Apis getApis() {
        if (apis == null) {
            synchronized (ApiClient.class) {
                if (apis == null) {
                    //设置缓存路径
                    File cacheDir = StorageUtils.getCacheDirectory(App.getInst());
                    Cache myCache = new Cache(cacheDir, MAX_CACHE);
                    OkHttpClient httpClient = new OkHttpClient.Builder()
//                            .cache(myCache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                            .addNetworkInterceptor(getNetWorkInterceptor())
//                            .addInterceptor(getInterceptor())
                            .build();

                    Retrofit restAdapter = new Retrofit.Builder()
                            .client(httpClient)
                            .baseUrl(Constants.Http.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    apis = restAdapter.create(Apis.class);
                }
            }
        }
        return apis;
    }

    /**
     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
     */
    public static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected(App.getInst())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * 设置连接器  设置缓存
     */
    public static Interceptor getNetWorkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (!NetworkUtils.isConnected(App.getInst())) {
                    int maxAge = 0 * 60;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
    }

    public static Subscription call(Observable observable, MySubscriber subscriber) {
        try {
            return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        } catch (Exception e) {
            Log.e(TAG, "call: " + e.getStackTrace());
        }
        return null;
    }

    public static void excute(Observable observable) {
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
