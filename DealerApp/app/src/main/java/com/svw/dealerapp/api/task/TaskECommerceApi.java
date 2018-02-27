package com.svw.dealerapp.api.task;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;
import com.svw.dealerapp.entity.task.TaskFollowUpEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public interface TaskECommerceApi {
    @GET("api/task/screen/oppor")
    Observable<ResEntity<TaskECommerceEntity>> getTaskECommerceList(@QueryMap Map<String, String> options);
}
