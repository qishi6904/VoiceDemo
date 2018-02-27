package com.svw.dealerapp.api;


import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.svw.dealerapp.DealerApp;

import com.svw.dealerapp.R;
import com.svw.dealerapp.global.Constants;
import com.svw.dealerapp.util.GsonUtils;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.PackageUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
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
 * Created by lijinkui on 2017/5/9.
 */

public class NetworkManager {

//    private static LoginApi mLoginApi;

//    public LoginApi getLoginApi() {
//        return mLoginApi == null ? configRetrofit(LoginApi.class, false, Constants.API_BASE_URL_USER_SERVICE): mLoginApi;
//    }
    
    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;

    private static Map<String, Object> apiInstanceMap = new HashMap<>();

    private static String mToken = "";

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    private static NetworkManager mNetworkManager;

    public static NetworkManager getInstance() {
        if (mNetworkManager == null) {
            mNetworkManager = new NetworkManager();
            ApiManager.init();
            apiInstanceMap.clear();
        }
        return mNetworkManager;
    }

    /**
     * 获取API实例
     * @param service
     * @param isGetToken
     * @param <T>
     * @return
     */
    public <T> T getApiInstance(Class<T> service, boolean isGetToken) {
        String baseUrl = null;
        String className = service.getSimpleName();
        T apiInstance = (T)apiInstanceMap.get(className);

        // 根据接口类型获取不同的服务path
        if(null == apiInstance) {
            if(ApiManager.isCarConfigApi(service)){
                baseUrl = Constants.API_BASE_URL_CAR_CONFIG_SERVICE;
            }else if(ApiManager.isDealerApi(service)){
                baseUrl = Constants.API_BASE_URL_DEALER_APP_SERVICE;
            }else if(ApiManager.isDictionaryApi(service)){
                baseUrl = Constants.API_BASE_URL_DIC_SERVICE;
            }else if(ApiManager.isKIPApi(service)){
                baseUrl = Constants.API_BASE_URL_KPI_SERVICE;
            }else if(ApiManager.isLeadsApi(service)){
                baseUrl = Constants.API_BASE_URL_LEADS_SERVICE;
            }else if(ApiManager.isMemberShipApi(service)){
                baseUrl = Constants.API_BASE_URL_MEMBERSHIP_SERVICE;
            }else if(ApiManager.isNoticeApi(service)){
                baseUrl = Constants.API_BASE_URL_NOTICE_SERVICE;
            }else if(ApiManager.isOrderApi(service)){
                baseUrl = Constants.API_BASE_URL_ORDER_SERVICE;
            }else if(ApiManager.isOpportunityApi(service)){
                baseUrl = Constants.API_BASE_URL_OPPORTUNITY_SERVICE;
            }else if(ApiManager.isUploadApi(service)){
                baseUrl = Constants.API_BASE_URL_UPLOAD_SERVICE;
            }else if(ApiManager.isUserApi(service)){
                baseUrl = Constants.API_BASE_URL_USER_SERVICE;
            }else if(ApiManager.isSuaaApi(service)){
                baseUrl = Constants.API_BASE_URL_SM_UAA_SERVICE;
            } else if(ApiManager.isSuserApi(service)){
                baseUrl = Constants.API_BASE_URL_SM_USER_SERVICE;
            }

            apiInstance = configRetrofit(service, isGetToken, baseUrl);
            apiInstanceMap.put(className, apiInstance);
        }

        return apiInstance;
    }
	
    private <T> T configRetrofit(Class<T> service, boolean isGetToken, String baseUrl) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(configClient(isGetToken))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);

    }

    private OkHttpClient configClient(final boolean isGetToken) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Content-Type", "application/json;charset=UTF-8");
                if(PackageUtils.isZhEvn()) {
                    builder.addHeader("Accept-Language", "zh");
                }
                if(isGetToken){
//                    mToken = "SWTeocT_SxZFg2psKqwD6KM6sJpyT9YD";//for unit test
                    builder.addHeader("xtoken", mToken);
                    builder.addHeader("Authorization", "Bearer "+mToken);
                }

                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        // Log信息拦截器
        Interceptor loggingIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset UTF8 = Charset.forName("UTF-8");

                //if retCode==401,tokenInvalid
                String readBuffer = buffer.clone().readString(UTF8);
                JLog.logi("REQUEST_URL", request.toString());
                JLog.logJ("RESPONSE_JSON", readBuffer);
                if(GsonUtils.isGoodJson(readBuffer)){
                    Gson gson = new Gson();
                    String retCode = "";
                    Map<String,Object> option = gson.fromJson(readBuffer,Map.class);
                    if(null != option.get("retCode")){
                        retCode = option.get("retCode").toString();
                    }else if(null != option.get("status")){
                        retCode = option.get("status").toString();
                    }
//                    boolean filter = request.url().toString().contains("api/dictionaries");
                    boolean filter = false;
//                    if(("401".equals(retCode) || "404".equals(retCode) || ("401.0".equals(retCode)) && !filter)){
                    if(("401".equals(retCode) || ("401.0".equals(retCode)) && !filter)){
                        Intent intent = new Intent("com.svw.dealerapp.tokenInvalid");
                        DealerApp.getContext().sendBroadcast(intent);
                    }
                }
//                    else {
//                        //由于后台token处理异常返回xml数据
//                        Intent intent = new Intent("com.svw.dealerapp.tokenInvalid");
//                        DealerApp.getContext().sendBroadcast(intent);
//                    }

                return response;
            }
        };
        okHttpClient.addInterceptor(loggingIntercept);

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(headerIntercept);

        return okHttpClient.build();
    }

    private static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    private static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }

        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
    }

    /**
     * 清空apiInstanceMap
     */
    public static void clearApiInstanceMap() {
        apiInstanceMap.clear();
    }

}
