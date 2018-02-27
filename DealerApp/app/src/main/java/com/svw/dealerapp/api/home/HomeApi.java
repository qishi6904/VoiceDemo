package com.svw.dealerapp.api.home;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.HomeEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/6/6.
 */

public interface HomeApi {
    @GET("api/homePage")
    Observable<ResEntity<HomeEntity>> getHomeData(@QueryMap Map<String, Object> options);
}
