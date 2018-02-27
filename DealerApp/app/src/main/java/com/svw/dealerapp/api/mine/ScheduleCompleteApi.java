package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface ScheduleCompleteApi {
    @GET("api/opportunities/appointments/screen")
    Observable<ResEntity<ScheduleCompleteEntity>> getScheduleComplete(@QueryMap Map<String, Object> options);
}