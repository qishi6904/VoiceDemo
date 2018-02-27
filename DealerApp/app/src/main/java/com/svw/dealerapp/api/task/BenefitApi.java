package com.svw.dealerapp.api.task;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.task.BenefitEntity;
import com.svw.dealerapp.entity.task.TaskECommerceEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qinshi on 6/6/2017.
 */

public interface BenefitApi {
    @GET("dealer/leads/custRights")
    Observable<ResEntity<BenefitEntity>> getBenefitData(@QueryMap Map<String, Object> options);
}
