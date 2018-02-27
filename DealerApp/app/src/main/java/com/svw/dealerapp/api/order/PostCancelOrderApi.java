package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface PostCancelOrderApi {
    @POST("customer/order/cancel")
    Observable<ResEntity<Object>> postCancelOrder(@Body Map<String, Object> options);
}
