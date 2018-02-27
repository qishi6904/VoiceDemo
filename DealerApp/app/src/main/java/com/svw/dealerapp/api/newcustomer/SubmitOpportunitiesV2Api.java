package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface SubmitOpportunitiesV2Api {
    @POST("api/v2/opportunities")
    Observable<ResEntity<OpportunityEntity>> submitOpportunitiesV2(@Body OpportunitySubmitReqEntityV2 options);

}
