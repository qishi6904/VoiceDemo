package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.CancelOrderReqEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface CancelOrderFollowupApi {
    @POST("customer/order/cancel")
    Observable<ResEntity<Object>> cancelOrderFollowup(@Body CancelOrderReqEntity entity);
}
