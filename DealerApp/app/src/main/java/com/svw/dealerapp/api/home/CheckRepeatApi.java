package com.svw.dealerapp.api.home;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.CheckRepeatEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface CheckRepeatApi {
    @GET("dealer/opearation/display")
    Observable<ResEntity<List<CheckRepeatEntity>>> checkRepeat(@QueryMap Map<String, Object> options);
}
