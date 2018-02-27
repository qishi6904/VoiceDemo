package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/12/2.
 */

public interface ActivateYellowCardApi {
    @POST("api/opportunities/activate")
    Observable<ResEntity<Object>> activateYellowCard(@Body FollowupCreateReqEntity options);
}
