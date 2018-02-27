package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUploadImageEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUploadImageApi {
    @Multipart
    @POST("api/v1/common/avatar/upload")
    Observable<SMResEntity<String>> smUploadImage(@Part MultipartBody.Part body, @QueryMap Map<String,Object>options);
}
