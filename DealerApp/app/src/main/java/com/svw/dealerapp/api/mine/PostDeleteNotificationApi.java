package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface PostDeleteNotificationApi {
    @POST("api/noticeCenter/delete")
    Observable<ResEntity<Object>> postDeleteNotification(@Body Map<String, Object> options);
}
