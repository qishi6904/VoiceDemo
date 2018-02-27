package com.svw.dealerapp.api.task;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 6/1/2017.
 */

public interface TaskFollowUpApi {
    @GET("api/task/screen/oppor")
    Observable<ResEntity<TaskFollowUpEntity>> getTaskFollowList(@QueryMap Map<String, String> options);
}
