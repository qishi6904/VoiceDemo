package com.svw.dealerapp.api.home;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/5.
 */

public interface CreateTrafficApi {
    @POST("dealer/leads")
    Observable<ResEntity<Object>> createTraffic(@Body Map<String, Object> options);
}
