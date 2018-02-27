package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginoutEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMLoginoutApi {
    @GET("api/v1/uaa/logout/tokenLogout")
    Observable<SMResEntity<Object>> smLoginout(@QueryMap Map<String, Object> options);
}
