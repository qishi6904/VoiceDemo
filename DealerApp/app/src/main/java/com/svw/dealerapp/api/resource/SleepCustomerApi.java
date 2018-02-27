package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.resource.FailedCustomerEntity;
import com.svw.dealerapp.entity.resource.SleepCustomerEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 8/14/2017.
 */

public interface SleepCustomerApi {

    @GET("api/opportunities/getOpportunityListByCondition")
    Observable<ResEntity<SleepCustomerEntity>> getSleepCustomerList(@QueryMap Map<String, String> options);
}
