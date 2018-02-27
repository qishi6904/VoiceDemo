package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMLoginEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMLoginApi {
    @POST("api/v1/uaa/mobile/obtainToken")
    Observable<SMResEntity<SMLoginEntity>> smLogin(@Body Map<String, Object> options);
}
