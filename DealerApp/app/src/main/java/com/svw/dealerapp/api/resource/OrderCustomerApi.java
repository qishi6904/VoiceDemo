package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.OrderCustomerEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 7/28/2017.
 */

public interface OrderCustomerApi {

    @GET("customer/order/my/order")
    Observable<ResEntity<OrderCustomerEntity>> getOrderCustomerList(@QueryMap Map<String, String> options);
}
