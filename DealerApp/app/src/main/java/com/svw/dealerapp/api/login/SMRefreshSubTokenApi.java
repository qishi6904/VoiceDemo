package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMRefreshSubTokenEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMRefreshSubTokenApi {
    @POST("api/v1/uaa/mobile/obtainSonToken")
    Observable<SMResEntity<SMRefreshSubTokenEntity>> smRefreshSubToken(@Body Map<String, Object> options);
}
