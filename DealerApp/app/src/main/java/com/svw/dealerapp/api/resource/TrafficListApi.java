package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */
@Deprecated
public interface TrafficListApi {
    @GET("dealer/leads")
    Observable<ResEntity<TrafficEntity>> getTrafficList(@QueryMap Map<String, String> options);
}
