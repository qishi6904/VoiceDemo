package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface SubmitRemarkApi {
    @POST("api/opportunities/comment")
    Observable<ResEntity<Object>> submitRemark(@Body Map<String, Object> options);
}
