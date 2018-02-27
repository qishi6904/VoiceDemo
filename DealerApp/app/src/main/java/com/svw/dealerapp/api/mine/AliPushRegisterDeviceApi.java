package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface AliPushRegisterDeviceApi {
    @POST("api/noticeCenter/register/device")
    Observable<ResEntity<Object>> aliPushRegisterDevice(@Body Map<String, Object> options);
}
