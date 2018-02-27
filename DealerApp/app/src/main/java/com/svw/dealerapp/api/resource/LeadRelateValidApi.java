package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2017/11/22.
 */

public interface LeadRelateValidApi {

    @POST("api/opportunities/bind/leads")
    Observable<ResEntity<Object>> leadRelateValid(@Body Map<String, Object> options);

}
