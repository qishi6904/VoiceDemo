package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.DELETE;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface AliPushUnregisterDeviceApi {
    @DELETE("api/noticeCenter/register/device")
    Observable<ResEntity<Object>> aliPushUnregisterDevice(@QueryMap Map<String, Object> options);
}
