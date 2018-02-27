package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface UpdateOpportunitiesApi {
    @PUT("api/opportunities")
    Observable<ResEntity<OpportunityEntity>> updateOpportunities(@Body OpportunityUpdateReqEntity options);
}
