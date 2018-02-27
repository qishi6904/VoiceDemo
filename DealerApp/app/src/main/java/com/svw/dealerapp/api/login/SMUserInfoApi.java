package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMUserInfoEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMUserInfoApi {
    @GET("api/v1/authentication/currentUser")
    Observable<SMResEntity<SMUserInfoEntity>> smUserInfo(@QueryMap Map<String, Object> options);
}
