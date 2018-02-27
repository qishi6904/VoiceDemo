package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface SubmitFollowupInfoApi {
    @POST("api/opportunities/followupInfo")
    Observable<ResEntity<Object>> submitFollowupInfo(@Body FollowupCreateReqEntity options);
}
