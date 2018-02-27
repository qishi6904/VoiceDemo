package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.QueryOrderDetailResponseEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface QueryOrderDetailApi {
    @GET("customer/order")
    Observable<ResEntity<QueryOrderDetailResponseEntity>> queryOrderDetail(@QueryMap Map<String, Object> options);

}
