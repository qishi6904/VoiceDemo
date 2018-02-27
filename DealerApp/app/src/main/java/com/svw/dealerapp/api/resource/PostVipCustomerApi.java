package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface PostVipCustomerApi {
    @PUT("api/opportunities")
    Observable<ResEntity<Object>> postVipCustomer(@Body Map<String, String> options);
}
