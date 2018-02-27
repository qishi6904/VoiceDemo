package com.svw.dealerapp.api.home;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.home.ActivityEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface ActivityListApi {
    @GET("dealer/activity/select")
    Observable<ResEntity<ActivityEntity>> getActivityList(@QueryMap Map<String, Object> options);
}
