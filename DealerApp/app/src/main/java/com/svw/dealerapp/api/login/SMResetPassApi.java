package com.svw.dealerapp.api.login;

import com.svw.dealerapp.entity.SMResEntity;
import com.svw.dealerapp.entity.login.SMResetPassEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/25.
 */

public interface SMResetPassApi {
    @POST("api/v1/common/user/changePassword")
    Observable<SMResEntity<Object>> smResetPass(@Body Map<String, Object> options);
}
