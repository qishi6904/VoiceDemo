package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.LogoutEntity;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/13.
 */

@Deprecated
public interface LogoutApi {
    @POST("dealer/logout")
    Observable<ResEntity<LogoutEntity>> logout(@QueryMap Map<String, Object> options);
}
