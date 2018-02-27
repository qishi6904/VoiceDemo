package com.svw.dealerapp.api.order;

import com.svw.dealerapp.entity.ReqEntity;
import com.svw.dealerapp.entity.ResEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lijinkui on 2018/1/18.
 */

public interface ReportOtdOrderApi {
    @POST("push/order/{orderId}")
    Observable<ResEntity<Object>> reportOtdOrder(@Path("orderId") String orderId, @Body ReqEntity entity);
}
