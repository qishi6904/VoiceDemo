package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.order.AppraiserEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 11/22/2017.
 */

public interface AppraiserApi {

    @GET("api/appraisers")
    Observable<ResEntity<AppraiserEntity>> getAppraiserList(@QueryMap Map<String, String> options);

}
