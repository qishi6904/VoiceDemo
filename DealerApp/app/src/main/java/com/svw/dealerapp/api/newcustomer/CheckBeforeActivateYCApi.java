package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface CheckBeforeActivateYCApi {
    @GET("api/opportunities/isActivate")
    Observable<ResEntity<Object>> checkBeforeActivateYC(@QueryMap Map<String, Object> options);
}
