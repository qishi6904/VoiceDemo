package com.svw.dealerapp.api;


import com.svw.dealerapp.BuildConfig;
import com.svw.dealerapp.api.update.AppUpdateApi;
import com.svw.dealerapp.api.update.AppUpdateDownloadApi;
import com.svw.dealerapp.util.JLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qinshi on 7/3/2017.
 */

public class UpdateNetwork {

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;

    private static AppUpdateApi mAppUpdateApi;
    private static AppUpdateDownloadApi mAppUpdateDownloadApi;

    private static UpdateNetwork mUpdateNetwork;

    public static UpdateNetwork getInstance() {
        if (null == mUpdateNetwork) {
            synchronized (UpdateNetwork.class) {
                if(null == mUpdateNetwork) {
                    mUpdateNetwork = new UpdateNetwork();
                }
            }
        }
        return mUpdateNetwork;
    }

    public AppUpdateApi getAppUpdateApi(){
        return mAppUpdateApi == null ? configRetrofit(AppUpdateApi.class, "http://api.fir.im/apps/", 0, 0): mAppUpdateApi;
    }

    public AppUpdateDownloadApi getAppUpdateDownloadApi(String baseUrl, int rangeStart, int rangeEnd){
        return mAppUpdateDownloadApi == null ? configRetrofit(AppUpdateDownloadApi.class, baseUrl, rangeStart, rangeEnd): mAppUpdateDownloadApi;
    }

    private <T> T configRetrofit(Class<T> service, String baseUrl, int rangeStart, int rangeEnd) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(configClient(rangeStart, rangeEnd))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);

    }

    private OkHttpClient configClient(final int rangeStart, final int rangeEnd) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                if(rangeStart >= 0 && rangeEnd > 0) {
                    builder.addHeader("Range", "bytes=" + rangeStart + "-" + rangeEnd);
                }
                Request request = builder.build();
                return chain.proceed(request);
            }
        };

        // Log信息拦截器
        if (BuildConfig.LOG_DEBUG) {
            Interceptor loggingIntercept = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    source.request(1024 * 10); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    Charset UTF8 = Charset.forName("UTF-8");

                    String readBuffer = buffer.clone().readString(UTF8);
                    JLog.logJ("RESPONSE_JSON", readBuffer);
                    JLog.logi("REQUEST_URL", request.toString());
                    return response;
                }
            };
            okHttpClient.addInterceptor(loggingIntercept);
        }

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(headerIntercept);

        return okHttpClient.build();
    }
}
