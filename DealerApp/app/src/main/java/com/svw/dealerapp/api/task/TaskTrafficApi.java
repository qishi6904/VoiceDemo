package com.svw.dealerapp.api.task;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.TrafficEntity;
import com.svw.dealerapp.entity.task.TaskTrafficEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 6/1/2017.
 */

public interface TaskTrafficApi {
    @GET("api/task/screen/leads")
    Observable<ResEntity<TaskTrafficEntity>> getTaskTrafficList(@QueryMap Map<String, String> options);
}
