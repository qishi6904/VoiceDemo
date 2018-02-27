package com.svw.dealerapp.api.newcustomer;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.newcustomer.FollowupCreateReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityDetailEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunityEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntity;
import com.svw.dealerapp.entity.newcustomer.OpportunitySubmitReqEntityV2;
import com.svw.dealerapp.entity.newcustomer.OpportunityUpdateReqEntity;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lijinkui on 2017/5/15.
 */
@Deprecated
public interface NewCustomerApi_NotUsed {
    @POST("api/opportunities")
    Observable<ResEntity<OpportunityEntity>> submitOpportunities(@Body Map<String, String> options);

    @POST("api/opportunities")
    Observable<ResEntity<OpportunityEntity>> submitOpportunities(@Body OpportunitySubmitReqEntity options);

    @PUT("api/opportunities")
    Observable<ResEntity<OpportunityEntity>> updateOpportunities(@Body Map<String, String> options);

    @POST("api/opportunities/followupInfo")
    Observable<ResEntity<Object>> submitFollowupInfo(@Body Map<String, String> options);
}
