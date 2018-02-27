package com.svw.dealerapp.api.task;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskLeadsEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 8/27/2017.
 */

public interface TaskLeadApi {

    @GET("screen/leads")
    Observable<ResEntity<TaskLeadsEntity>> getTaskLeadsList(@QueryMap Map<String, String> options);
}
