package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.DealCustomerEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 8/14/2017.
 */

public interface DealCustomerApi {

    @GET("customer/order/my/handOver")
    Observable<ResEntity<DealCustomerEntity>> getDealCustomerList(@QueryMap Map<String, String> options);
}
