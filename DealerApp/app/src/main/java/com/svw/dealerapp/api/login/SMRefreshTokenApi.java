package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshTokenEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMRefreshTokenApi {
    @POST("api/v1/uaa/oauth2/refresh_token")
    Observable<SMResEntity<SMRefreshTokenEntity>> smRefreshToken(@Body Map<String, Object> options);
}
