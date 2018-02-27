package com.svw.dealerapp.api.mine;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.mine.NotificationEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface NotificationDataApi {
    @POST("api/noticeCenter/noticelist")
    Observable<ResEntity<NotificationEntity>> getNotificationData(@Body Map<String, Object> options);
}