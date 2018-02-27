package com.svw.dealerapp.api.resource;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface PostTransferYellowCardApi {
    @POST("api/opportunities/transfer")
    Observable<ResEntity<Object>> postTransferYellowCard(@Body Map<String, String> options);
}
